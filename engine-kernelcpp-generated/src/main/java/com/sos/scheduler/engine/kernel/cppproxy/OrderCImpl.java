// *** Generated by com.sos.scheduler.engine.cplusplus.generator ***

package com.sos.scheduler.engine.kernel.cppproxy;

@javax.annotation.Generated("C++/Java-Generator - SOS GmbH Berlin")
@SuppressWarnings({"unchecked", "rawtypes"})
final class OrderCImpl
extends com.sos.scheduler.engine.cplusplus.runtime.CppProxyImpl<com.sos.scheduler.engine.kernel.order.Order>
implements com.sos.scheduler.engine.kernel.cppproxy.OrderC {

    // <editor-fold defaultstate="collapsed" desc="Generated code - DO NOT EDIT">

    private OrderCImpl(com.sos.scheduler.engine.cplusplus.runtime.Sister context) { // Nur für JNI zugänglich
        setSister(com.sos.scheduler.engine.kernel.cppproxy.OrderC$.MODULE$.sisterType().sister(this, context));
    }

    @Override public java.lang.String calculate_db_distributed_next_time() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = calculate_db_distributed_next_time__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String calculate_db_distributed_next_time__native(long cppReference);


    @Override public java.lang.String database_runtime_xml() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = database_runtime_xml__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String database_runtime_xml__native(long cppReference);


    @Override public java.lang.String database_xml() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = database_xml__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String database_xml__native(long cppReference);


    @Override public long endTimeMillis() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return endTimeMillis__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long endTimeMillis__native(long cppReference);


    @Override public java.lang.String end_state_string() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = end_state_string__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String end_state_string__native(long cppReference);


    @Override public java.lang.String file() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = file__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String file__native(long cppReference);


    @Override public java.lang.String file_based_error_string() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = file_based_error_string__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String file_based_error_string__native(long cppReference);


    @Override public int file_based_state() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return file_based_state__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native int file_based_state__native(long cppReference);


    @Override public long file_modification_time_t() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return file_modification_time_t__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long file_modification_time_t__native(long cppReference);


    @Override public java.lang.String file_path() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = file_path__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String file_path__native(long cppReference);


    @Override public boolean has_base_file() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return has_base_file__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean has_base_file__native(long cppReference);


    @Override public int history_id() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return history_id__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native int history_id__native(long cppReference);


    @Override public boolean id_locked() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return id_locked__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean id_locked__native(long cppReference);


    @Override public java.lang.String initial_state_string() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = initial_state_string__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String initial_state_string__native(long cppReference);


    @Override public boolean is_file_based() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_file_based__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_file_based__native(long cppReference);


    @Override public boolean is_file_based_reread() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_file_based_reread__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_file_based_reread__native(long cppReference);


    @Override public boolean is_file_order() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_file_order__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_file_order__native(long cppReference);


    @Override public boolean is_on_blacklist() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_on_blacklist__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_on_blacklist__native(long cppReference);


    @Override public boolean is_to_be_removed() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_to_be_removed__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_to_be_removed__native(long cppReference);


    @Override public boolean is_visible() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return is_visible__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean is_visible__native(long cppReference);


    @Override public long java_fast_flags() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return java_fast_flags__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long java_fast_flags__native(long cppReference);


    @Override public com.sos.scheduler.engine.kernel.order.jobchain.Node java_job_chain_node() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            com.sos.scheduler.engine.kernel.order.jobchain.Node result = java_job_chain_node__native(cppReference());
            checkIsNotReleased(com.sos.scheduler.engine.kernel.order.jobchain.Node.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native com.sos.scheduler.engine.kernel.order.jobchain.Node java_job_chain_node__native(long cppReference);


    @Override public java.lang.String java_occupying_cluster_member_id() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = java_occupying_cluster_member_id__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String java_occupying_cluster_member_id__native(long cppReference);


    @Override public void java_remove() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java_remove__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void java_remove__native(long cppReference);


    @Override public com.sos.scheduler.engine.kernel.cppproxy.Job_chainC job_chain() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            com.sos.scheduler.engine.kernel.cppproxy.Job_chainC result = job_chain__native(cppReference());
            checkIsNotReleased(com.sos.scheduler.engine.kernel.cppproxy.Job_chainC.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native com.sos.scheduler.engine.kernel.cppproxy.Job_chainC job_chain__native(long cppReference);


    @Override public java.lang.String job_chain_path_string() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = job_chain_path_string__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String job_chain_path_string__native(long cppReference);


    @Override public com.sos.scheduler.engine.kernel.cppproxy.Prefix_logC log() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            com.sos.scheduler.engine.kernel.cppproxy.Prefix_logC result = log__native(cppReference());
            checkIsNotReleased(com.sos.scheduler.engine.kernel.cppproxy.Prefix_logC.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native com.sos.scheduler.engine.kernel.cppproxy.Prefix_logC log__native(long cppReference);


    @Override public String[] missing_requisites_java() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            String[] result = missing_requisites_java__native(cppReference());
            checkIsNotReleased(String[].class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native String[] missing_requisites_java__native(long cppReference);


    @Override public java.lang.String name() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = name__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String name__native(long cppReference);


    @Override public boolean name_is_fixed() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return name_is_fixed__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean name_is_fixed__native(long cppReference);


    @Override public long next_step_at_millis() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return next_step_at_millis__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long next_step_at_millis__native(long cppReference);


    @Override public long next_time_millis() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return next_time_millis__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long next_time_millis__native(long cppReference);


    @Override public java.lang.String outer_job_chain_path() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = outer_job_chain_path__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String outer_job_chain_path__native(long cppReference);


    @Override public com.sos.scheduler.engine.kernel.cppproxy.Variable_setC params() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            com.sos.scheduler.engine.kernel.cppproxy.Variable_setC result = params__native(cppReference());
            checkIsNotReleased(com.sos.scheduler.engine.kernel.cppproxy.Variable_setC.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native com.sos.scheduler.engine.kernel.cppproxy.Variable_setC params__native(long cppReference);


    @Override public java.lang.String path() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = path__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String path__native(long cppReference);


    @Override public int priority() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return priority__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native int priority__native(long cppReference);


    @Override public com.sos.scheduler.engine.kernel.filebased.FileBased replacement_java() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            com.sos.scheduler.engine.kernel.filebased.FileBased result = replacement_java__native(cppReference());
            checkIsNotReleased(com.sos.scheduler.engine.kernel.filebased.FileBased.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native com.sos.scheduler.engine.kernel.filebased.FileBased replacement_java__native(long cppReference);


    @Override public void set_end_state(java.lang.String p0) {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_end_state__native(cppReference(), p0);
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_end_state__native(long cppReference, java.lang.String p0);


    @Override public void set_end_state_reached() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_end_state_reached__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_end_state_reached__native(long cppReference);


    @Override public void set_force_file_reread() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_force_file_reread__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_force_file_reread__native(long cppReference);


    @Override public void set_id(java.lang.String p0) {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_id__native(cppReference(), p0);
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_id__native(long cppReference, java.lang.String p0);


    @Override public void set_on_blacklist() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_on_blacklist__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_on_blacklist__native(long cppReference);


    @Override public void set_priority(int p0) {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_priority__native(cppReference(), p0);
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_priority__native(long cppReference, int p0);


    @Override public void set_suspended(boolean p0) {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_suspended__native(cppReference(), p0);
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_suspended__native(long cppReference, boolean p0);


    @Override public void set_title(java.lang.String p0) {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            set_title__native(cppReference(), p0);
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native void set_title__native(long cppReference, java.lang.String p0);


    @Override public long setback_millis() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return setback_millis__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long setback_millis__native(long cppReference);


    @Override public byte[] source_xml_bytes() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            byte[] result = source_xml_bytes__native(cppReference());
            checkIsNotReleased(byte[].class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native byte[] source_xml_bytes__native(long cppReference);


    @Override public long startTimeMillis() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return startTimeMillis__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native long startTimeMillis__native(long cppReference);


    @Override public java.lang.String state_text() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = state_text__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String state_text__native(long cppReference);


    @Override public java.lang.String string_id() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = string_id__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String string_id__native(long cppReference);


    @Override public java.lang.String string_payload() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = string_payload__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String string_payload__native(long cppReference);


    @Override public java.lang.String string_state() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = string_state__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String string_state__native(long cppReference);


    @Override public boolean suspended() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return suspended__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native boolean suspended__native(long cppReference);


    @Override public int task_id() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            return task_id__native(cppReference());
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native int task_id__native(long cppReference);


    @Override public java.lang.String title() {
        com.sos.scheduler.engine.cplusplus.runtime.CppProxy$.MODULE$.requireCppThread();
        try {
            java.lang.String result = title__native(cppReference());
            checkIsNotReleased(java.lang.String.class, result);
            return result;
        }
        catch (Exception x) { throw com.sos.scheduler.engine.cplusplus.runtime.CppProxies.propagateCppException(x, this); }
    }

    private static native java.lang.String title__native(long cppReference);


    // </editor-fold>
}
