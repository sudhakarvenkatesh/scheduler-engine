// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

#include "_precompiled.h"

#include "com__sos__scheduler__engine__kernel__cppproxy__ScheduleC.h"
#include "java__lang__Object.h"
#include "java__lang__String.h"

namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { 

struct ScheduleC__class : ::zschimmer::javabridge::Class
{
    ScheduleC__class(const string& class_name);
   ~ScheduleC__class();


    static const ::zschimmer::javabridge::class_factory< ScheduleC__class > class_factory;
};

const ::zschimmer::javabridge::class_factory< ScheduleC__class > ScheduleC__class::class_factory ("com.sos.scheduler.engine.kernel.cppproxy.ScheduleC");

ScheduleC__class::ScheduleC__class(const string& class_name) :
    ::zschimmer::javabridge::Class(class_name)
{}

ScheduleC__class::~ScheduleC__class() {}




ScheduleC::ScheduleC(jobject jo) { if (jo) assign_(jo); }

ScheduleC::ScheduleC(const ScheduleC& o) { assign_(o.get_jobject()); }

#ifdef Z_HAS_MOVE_CONSTRUCTOR
    ScheduleC::ScheduleC(ScheduleC&& o) { set_jobject(o.get_jobject());  o.set_jobject(NULL); }
#endif

ScheduleC::~ScheduleC() { assign_(NULL); }





::zschimmer::javabridge::Class* ScheduleC::java_object_class_() const { return _class.get(); }

::zschimmer::javabridge::Class* ScheduleC::java_class_() { return ScheduleC__class::class_factory.clas(); }


void ScheduleC::Lazy_class::initialize() const {
    _value = ScheduleC__class::class_factory.clas();
}


}}}}}}}