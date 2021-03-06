// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

#ifndef _JAVAPROXY_COM_SOS_SCHEDULER_ENGINE_KERNEL_CPPPROXY_TASKC_H_
#define _JAVAPROXY_COM_SOS_SCHEDULER_ENGINE_KERNEL_CPPPROXY_TASKC_H_

#include "../zschimmer/zschimmer.h"
#include "../zschimmer/java.h"
#include "../zschimmer/Has_proxy.h"
#include "../zschimmer/javaproxy.h"
#include "../zschimmer/lazy.h"
#include "java__lang__Object.h"

namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { struct JobC; }}}}}}}
namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { struct OrderC; }}}}}}}
namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { struct Prefix_logC; }}}}}}}
namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { struct Process_classC; }}}}}}}
namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { struct Variable_setC; }}}}}}}
namespace javaproxy { namespace java { namespace lang { struct Object; }}}
namespace javaproxy { namespace java { namespace lang { struct String; }}}


namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace cppproxy { 


struct TaskC__class;

struct TaskC : ::zschimmer::javabridge::proxy_jobject< TaskC >, ::javaproxy::java::lang::Object {
  private:
    static TaskC new_instance();  // Not implemented
  public:

    TaskC(jobject = NULL);

    TaskC(const TaskC&);

    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        TaskC(TaskC&&);
    #endif

    ~TaskC();

    TaskC& operator=(jobject jo) { assign_(jo); return *this; }
    TaskC& operator=(const TaskC& o) { assign_(o.get_jobject()); return *this; }
    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        TaskC& operator=(TaskC&& o) { set_jobject(o.get_jobject()); o.set_jobject(NULL); return *this; }
    #endif

    jobject get_jobject() const { return ::zschimmer::javabridge::proxy_jobject< TaskC >::get_jobject(); }

  protected:
    void set_jobject(jobject jo) {
        ::zschimmer::javabridge::proxy_jobject< TaskC >::set_jobject(jo);
        ::javaproxy::java::lang::Object::set_jobject(jo);
    }
  public:


    ::zschimmer::javabridge::Class* java_object_class_() const;

    static ::zschimmer::javabridge::Class* java_class_();


  private:
    struct Lazy_class : ::zschimmer::abstract_lazy<TaskC__class*> {
        void initialize() const;
    };

    Lazy_class _class;
};


}}}}}}}

#endif
