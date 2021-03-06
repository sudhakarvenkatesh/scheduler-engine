// $Id: spooler_module_java.cxx 13870 2010-06-03 15:29:33Z jz $
/*
    Hier sind implementiert

    Java_vm
    Module::java_method_id
    Java_module_instance
*/

#include "spooler.h"
#include "../file/stdfile.h"    // make_path

#include "../kram/sos_java.h"
#include "../zschimmer/java.h"
#include "../zschimmer/system_command.h"

//#ifdef _DEBUG
//#   include "Debug/sos/spooler/Idispatch.h"
//#else
//#   include "Release/sos/spooler/Idispatch.h"
//#endif

#include <sys/stat.h>

#ifdef Z_WINDOWS
#   include <sys/utime.h>
#else
#   include <utime.h>
#endif

#ifndef JNI_VERSION_1_2
#   error "Der Scheduler braucht jni.h Version 1.2"
#endif

#ifdef SCHEDULER_WITH_HOSTJAVA
#   include "../hostjava/hostjava.h"
#endif

using namespace std;
using namespace zschimmer::javabridge;

extern "C"
//JNIEXPORT
jobject JNICALL Java_sos_spooler_Idispatch_com_1call( JNIEnv*, jclass, jlong jidispatch, jstring, jobjectArray );


namespace sos {
namespace scheduler {

//--------------------------------------------------------------------------------------------const

const string needed_api_version = "2.0.160.4605 (2006-11-23)";

const static JNINativeMethod native_methods[] = 
{
    { (char*)"com_call", (char*)"(JLjava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", (void*)Java_sos_spooler_Idispatch_com_1call }
};

//-------------------------------------------------------------------------------------------static

static zschimmer::Thread_data<Java_thread_data> thread_data;

//-----------------------------------------------------------------------------jobject_from_variant

static jobject scheduler_jobject_from_variant( JNIEnv* jenv, const VARIANT& v, Java_idispatch_container* java_idispatch_container )
{
    Com_env env = jenv;

    if( v.vt == VT_EMPTY )
    {
        return jenv->NewString( NULL, 0 );       // Für job_chain::Node.next_state, .error_state ("" wird zu VT_EMPTY)
    }
    else
    if( v.vt == VT_ERROR  && v.scode == DISP_E_PARAMNOTFOUND ) 
    {
        return jenv->NewString( NULL, 0 );       // Für job_chain::Node.next_state, .error_state ("" wird zu VT_EMPTY)
    }
    else
    {
        return env.jobject_from_variant( v, java_idispatch_container );
    }
}

//--------------------------------------------------------------Java sos.spooler.Idispatch.com_call

extern "C"
//JNIEXPORT
jobject JNICALL Java_sos_spooler_Idispatch_com_1call( JNIEnv* jenv, jclass cls, jlong jidispatch, jstring jname, jobjectArray jparams )
{
    Com_env env = jenv;

    try
    {
        return scheduler_jobject_from_variant( jenv, env.variant_java_com_call( cls, jidispatch, jname, jparams ), &thread_data->_idispatch_container );
    }
    catch( const exception&  x ) { env.set_java_exception( x ); }
    catch( const _com_error& x ) { env.set_java_exception( x ); }

    return NULL;
}

//------------------------------------------Java_idispatch_stack_frame::~Java_idispatch_stack_frame

Java_idispatch_stack_frame::~Java_idispatch_stack_frame()
{ 
    thread_data->_idispatch_container.release_objects(); 
}

//-------------------------------------------------------------------------------------init_java_vm

void init_java_vm( javabridge::Vm* java_vm )
{
    string work_dir = java_vm->work_dir();
    if( !work_dir.empty() )
    {
        try
        {
            //fprintf( stderr, "make_path %s\n", work_dir.c_str() );
            make_path( work_dir );  // Verzeichnis muss beim Start von Java vorhanden sein, damit Java es in classpath berücksichtigt.
        }
        catch( const exception& x ) { java_vm->_log.warn( "mkdir " + work_dir + " => " + x.what() ); }
    }

    java_vm->start();
    java_vm->load_standard_classes();           // Wirkt natürlich nur für dieses Vm. Es kann in einer DLL aber noch eines geben, deshalb standard_classes()

    {
        Env env = java_vm->jni_env();
        Local_frame local_frame ( 10 );

        //_idispatch_jclass = env->FindClass( JAVA_IDISPATCH_CLASS );
        Class idispatch_class ( JAVA_IDISPATCH_CLASS );
        if( env->ExceptionCheck() )  env.throw_java( "FindClass " JAVA_IDISPATCH_CLASS );

        int ret = env->RegisterNatives( idispatch_class, native_methods, NO_OF( native_methods ) );
        if( ret < 0 )  env.throw_java( "RegisterNatives" );
      //if( ret < 0 )  throw_java_ret( ret, "RegisterNatives" );
    }

#   ifdef SCHEDULER_WITH_HOSTJAVA
        Z_LOG2( "scheduler", "init_hostjava()\n" );
        hostjava::init_hostjava( java_vm );     // Weil gcc 3.2 in libhostjava.sl die statischen Variablen nicht initialisiert, bin wir das Module ein.
#   endif
}

//-------------------------------------------------------Java_module_instance::Java_module_instance

Java_module_instance::Java_module_instance( Module* module )
: 
    Module_instance(module),
    _zero_(_end_)
{
}

//-----------------------------------------------------------------Java_module_instance::close__end

void Java_module_instance::close__end()  // Synchron
{
    Module_instance::close__end();   // Synchron

    _added_jobjects.clear();
    _jobject = NULL;
}

//-----------------------------------------------------------------------Java_module_instance::init

void Java_module_instance::init()
{
    Env         env;
    Local_frame local_frame ( 10 );
    Java_idispatch_stack_frame stack_frame;

    Module_instance::init();

    assert( _jobject == NULL );
    if( !_java_class )
    {
        string class_name = replace_regex( _module->_java_class_name, "\\.", "/" );
        _java_class = env.find_class( class_name.c_str() );
    }

    jmethodID method_id = java_method_id( "<init>()V" );   // Konstruktor
    if (method_id) {
        _jobject = env->NewObject(_java_class, method_id);
    } else 
    if (_module->_injectorJ) {
        env->ExceptionClear();
        _jobject = _module->_injectorJ.getInstance(_java_class.j());
    } else 
        env.throw_java("GetMethodID");
    
    if (!_jobject || env->ExceptionCheck())  env.throw_java(_module->_java_class_name + " constructor");
}

//--------------------------------------------------------------------Java_module_instance::add_obj

void Java_module_instance::add_obj( IDispatch* object, const string& name )
{
    Env env;
    Local_frame local_frame ( 10 );


    string java_class_name = "sos/spooler/" + replace_regex_ext( name, "^(spooler_)?(.*)$", "\\u\\2" );    // "spooler_task" -> "sos.spooler.Task"

    //Z_LOGI2( "scheduler", "Java_module_instance::add_obj " << java_class_name << "\n" );

    jclass cls = _jobject.get_jclass();

    string signature = "L" + java_class_name + ";";

    jfieldID field_id = env->GetFieldID( cls, name.c_str(), signature.c_str() );

    if( !field_id )  env.throw_java( "GetFieldID", name );

    ptr<Java_idispatch> java_idispatch = Z_NEW( Java_idispatch( object, true, java_class_name.c_str() ) );

    _added_jobjects.push_back( java_idispatch );
                         
    env->SetObjectField( _jobject, field_id, java_idispatch->get_jobject() );
    if( env->ExceptionCheck() )  env.throw_java( "SetObjectField", name );


    Module_instance::add_obj( object, name );
}

//-----------------------------------------------------------------------Java_module_instance::load

bool Java_module_instance::load()
{
    return Module_instance::load();
}

//-----------------------------------------------------------------------Java_module_instance::call

Variant Java_module_instance::call( const string& name_par )
{
    Env    env;
    bool   is_optional = false;
    string name        = name_par;

    Local_frame local_frame ( 10 );
    Java_idispatch_stack_frame stack_frame;


    if( name[0] == '?' )  is_optional = true,  name.erase( 0, 1 );

    jmethodID method_id = java_method_id( name );
    if( !method_id )  
    {
        if( is_optional )  return Variant();
        env.throw_java( name, message_string( "SCHEDULER-174", name, _module->_java_class_name ) );
        //z::throw_xc( "SCHEDULER-174", name, _module->_java_class_name.c_str() );
    }

    Variant result;

    if( *name.rbegin() == 'Z' )
    {
        In_call in_call ( this, name );
        result = env->CallBooleanMethod( _jobject, method_id ) != 0;
    }
    //else
    //if( *name.rbegin() == ';' )     // Für spooler_api_version()
    //{
    //    In_call in_call ( this, name ); 
    //    Local_jstring jstr ( (jstring)env->CallObjectMethod( _jobject, method_id ) );
    //    if( env->ExceptionCheck() )  env.throw_java( name );
    //    result = env.string_from_jstring( jstr );
    //}
    else
    {
        In_call in_call ( this, name );
        env->CallVoidMethod( _jobject, method_id );
    }

    if( env->ExceptionCheck() )  env.throw_java( name );

    return result;
}

//-----------------------------------------------------------------------Java_module_instance::call

Variant Java_module_instance::call( const string& name, const Variant& param, const Variant& param2 )
{
    Com_env env;
    Local_frame local_frame ( 10 );
    Java_idispatch_stack_frame stack_frame;

    jmethodID method_id = java_method_id( name );
    if( !method_id )  z::throw_xc( "SCHEDULER-174", name, _module->_java_class_name.c_str() );

    bool result;
    
    {
        In_call in_call ( this, name );

        if( string_ends_with( name, "(Z)Z" )  &&  param2.is_missing() )
        {
            result = env->CallBooleanMethod( _jobject, method_id, param.as_bool() ) != 0;
        }
        else
        if( string_ends_with( name, "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;" ) )  // Für <run_time start_time_function="">, s. Run_time::call_function
        {
            Bstr result;

            jstring jstr1 = env.jstring_from_variant( param );
            jstring jstr2 = env.jstring_from_variant( param2 );
            jstring jstr = static_cast<jstring>( env->CallObjectMethod( _jobject, method_id, jstr1, jstr2 ) );
            jboolean exception_check = env->ExceptionCheck();

            if( !exception_check )
            {
                env.jstring_to_bstr( jstr, &result._bstr );
            }

            env->DeleteLocalRef( jstr );
            env->DeleteLocalRef( jstr1 );
            env->DeleteLocalRef( jstr2 );

            if( exception_check )  env.throw_java( name );
            return result;
        }
        else
        //if( string_ends_with( name, "(Lsos/spooler/Spooler_object;)Z" ) )
        //{
        //    ...
        //}
        //else
            z::throw_xc( Z_FUNCTION, " Call is not implemented in scheduler java interface", name );
    }

    if( env->ExceptionCheck() )  env.throw_java( name );

    return result;
}

//----------------------------------------------------------------Java_module_instance::name_exists

bool Java_module_instance::name_exists( const string& name )
{
    return java_method_id( name ) != NULL;
}

//-------------------------------------------------------------Java_module_instance::java_method_id

jmethodID Java_module_instance::java_method_id( const string& name )
{
    Env         env;
    jmethodID   method_id;

    Method_map::iterator it = _method_map.find( name );
    if( it == _method_map.end() )  
    {
        size_t pos = name.find( '(' );
        if( pos == string::npos )  pos = name.length();
        
        if( !_java_class )  z::throw_xc( "SCHEDULER-197", name );
        method_id = env->GetMethodID( _java_class, name.substr(0,pos).c_str(), name.c_str()+pos );
        //if( env->ExceptionCheck() )  env->ExceptionDescribe(), env->ExceptionClear();

        _method_map[name] = method_id;
    }
    else
    {
        method_id = it->second;
    }

    return method_id;
}

//-------------------------------------------------------------------------------------------------

} //namespace scheduler
} //namespace sos
