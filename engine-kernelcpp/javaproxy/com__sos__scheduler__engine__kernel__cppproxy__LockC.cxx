// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

#include "_precompiled.h"

#include "com__sos__scheduler__engine__kernel__cppproxy__LockC.h"
#include "java__lang__Object.h"
#include "java__lang__String.h"

namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { 

struct LockC__class : ::zschimmer::javabridge::Class
{
    LockC__class(const string& class_name);
   ~LockC__class();


    static const ::zschimmer::javabridge::class_factory< LockC__class > class_factory;
};

const ::zschimmer::javabridge::class_factory< LockC__class > LockC__class::class_factory ("com.sos.scheduler.engine.kernel.cppproxy.LockC");

LockC__class::LockC__class(const string& class_name) :
    ::zschimmer::javabridge::Class(class_name)
{}

LockC__class::~LockC__class() {}




LockC::LockC(jobject jo) { if (jo) assign_(jo); }

LockC::LockC(const LockC& o) { assign_(o.get_jobject()); }

#ifdef Z_HAS_MOVE_CONSTRUCTOR
    LockC::LockC(LockC&& o) { set_jobject(o.get_jobject());  o.set_jobject(NULL); }
#endif

LockC::~LockC() { assign_(NULL); }





::zschimmer::javabridge::Class* LockC::java_object_class_() const { return _class.get(); }

::zschimmer::javabridge::Class* LockC::java_class_() { return LockC__class::class_factory.clas(); }


void LockC::Lazy_class::initialize() const {
    _value = LockC__class::class_factory.clas();
}


}}}}}}}
