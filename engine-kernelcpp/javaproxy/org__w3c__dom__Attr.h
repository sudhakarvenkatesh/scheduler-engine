// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

#ifndef _JAVAPROXY_ORG_W3C_DOM_ATTR_H_
#define _JAVAPROXY_ORG_W3C_DOM_ATTR_H_

#include "../zschimmer/zschimmer.h"
#include "../zschimmer/java.h"
#include "../zschimmer/Has_proxy.h"
#include "../zschimmer/javaproxy.h"
#include "../zschimmer/lazy.h"
#include "java__lang__Object.h"

namespace javaproxy { namespace java { namespace lang { struct Object; }}}
namespace javaproxy { namespace java { namespace lang { struct String; }}}
namespace javaproxy { namespace org { namespace w3c { namespace dom { struct Element; }}}}
namespace javaproxy { namespace org { namespace w3c { namespace dom { struct Node; }}}}


namespace javaproxy { namespace org { namespace w3c { namespace dom { 


struct Attr__class;

struct Attr : ::zschimmer::javabridge::proxy_jobject< Attr >, ::javaproxy::java::lang::Object {
  private:
    static Attr new_instance();  // Not implemented
  public:

    Attr(jobject = NULL);

    Attr(const Attr&);

    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        Attr(Attr&&);
    #endif

    ~Attr();

    Attr& operator=(jobject jo) { assign_(jo); return *this; }
    Attr& operator=(const Attr& o) { assign_(o.get_jobject()); return *this; }
    #ifdef Z_HAS_MOVE_CONSTRUCTOR
        Attr& operator=(Attr&& o) { set_jobject(o.get_jobject()); o.set_jobject(NULL); return *this; }
    #endif

    jobject get_jobject() const { return ::zschimmer::javabridge::proxy_jobject< Attr >::get_jobject(); }

  protected:
    void set_jobject(jobject jo) {
        ::zschimmer::javabridge::proxy_jobject< Attr >::set_jobject(jo);
        ::javaproxy::java::lang::Object::set_jobject(jo);
    }
  public:

    ::javaproxy::java::lang::String getName() const;
    ::javaproxy::org::w3c::dom::Element getOwnerElement() const;
    bool getSpecified() const;
    ::javaproxy::java::lang::String getValue() const;
    bool isId() const;
    void setValue(const ::zschimmer::javabridge::proxy_jobject< ::javaproxy::java::lang::String >& p0) const;

    ::zschimmer::javabridge::Class* java_object_class_() const;

    static ::zschimmer::javabridge::Class* java_class_();


  private:
    struct Lazy_class : ::zschimmer::abstract_lazy<Attr__class*> {
        void initialize() const;
    };

    Lazy_class _class;
};


}}}}

#endif
