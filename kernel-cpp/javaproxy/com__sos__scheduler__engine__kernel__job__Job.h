// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

#ifndef _JAVAPROXY_COM_SOS_SCHEDULER_ENGINE_KERNEL_JOB_JOB_H_
#define _JAVAPROXY_COM_SOS_SCHEDULER_ENGINE_KERNEL_JOB_JOB_H_

#include "../zschimmer/zschimmer.h"
#include "../zschimmer/java.h"
#include "../zschimmer/Has_proxy.h"
#include "../zschimmer/javaproxy.h"
#include "../zschimmer/lazy.h"
#include "java__lang__Object.h"

namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace data { namespace job { struct JobPersistentState; }}}}}}}
namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace filebased { struct FileBased; }}}}}}}
namespace javaproxy { namespace java { namespace lang { struct Object; }}}
namespace javaproxy { namespace java { namespace lang { struct String; }}}
namespace javaproxy { namespace scala { struct Option; }}


namespace javaproxy { namespace com { namespace sos { namespace scheduler { namespace engine { namespace kernel { namespace job { 


struct Job__class;

struct Job : ::zschimmer::javabridge::proxy_jobject< Job >, ::javaproxy::java::lang::Object {
  private:
    static Job new_instance();  // Not implemented
  public:

    Job(jobject = NULL);

    Job(const Job&);

    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        Job(Job&&);
    #endif

    ~Job();

    Job& operator=(jobject jo) { assign_(jo); return *this; }
    Job& operator=(const Job& o) { assign_(o.get_jobject()); return *this; }
    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        Job& operator=(Job&& o) { set_jobject(o.get_jobject()); o.set_jobject(NULL); return *this; }
    #endif

    jobject get_jobject() const { return ::zschimmer::javabridge::proxy_jobject< Job >::get_jobject(); }

  protected:
    void set_jobject(jobject jo) {
        ::zschimmer::javabridge::proxy_jobject< Job >::set_jobject(jo);
        ::javaproxy::java::lang::Object::set_jobject(jo);
    }
  public:

    void deletePersistedTask(jint p0) const;
    void deletePersistentState() const;
    void loadPersistentTasks() const;
    void persistEnqueuedTask(jint p0, jlong p1, jlong p2, const ::zschimmer::javabridge::proxy_jobject< ::javaproxy::java::lang::String >& p3, const ::zschimmer::javabridge::proxy_jobject< ::javaproxy::java::lang::String >& p4) const;
    void persistState() const;
    ::javaproxy::scala::Option tryFetchAverageStepDuration() const;
    ::javaproxy::com::sos::scheduler::engine::data::job::JobPersistentState tryFetchPersistentState() const;

    ::zschimmer::javabridge::Class* java_object_class_() const;

    static ::zschimmer::javabridge::Class* java_class_();


  private:
    struct Lazy_class : ::zschimmer::abstract_lazy<Job__class*> {
        void initialize() const;
    };

    Lazy_class _class;
};


}}}}}}}

#endif