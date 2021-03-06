package com.sos.scheduler.engine.tests.scheduler.comapi.java;

import com.google.common.base.Strings;
import sos.spooler.Job;
import sos.spooler.Job_impl;

/**
 * @author Andreas Liebert
 */
public class JobObjectJob extends Job_impl {

    enum UnwantedMessage {
        CONFIG_DIR("spooler_job.configuration_directory is empty"),
        FOLDER_PATH("spooler_job.folder_path is empty"),
        MAX_ORDER_SETBACKS("unexpected value for spooler_job.max_order_setbacks"),
        JOB_PATH("spooler_job.name() returns incorrect value"),
        REMOVE(SchedulerAPIIT.RemoveMeJobPath().string()+" still exists");

        private final String message;

        UnwantedMessage(String message){
            this.message = "##"+message+"##";
        }

        @Override
        public String toString() {
            return message;
        }
    }

    @Override
    public boolean spooler_process() throws Exception {
        spooler_job.set_delay_after_error(1, 60);
        spooler_job.set_delay_after_error(2, "00:02");
        spooler_job.set_delay_after_error(3, "00:02:22");
        spooler_job.set_delay_after_error(4, "STOP");
        spooler_job.clear_delay_after_error();

        spooler_job.set_delay_order_after_setback(1, 70.5);
        spooler_job.set_delay_order_after_setback(2, "00:03");
        spooler_job.set_delay_order_after_setback(3, "00:03:33");

        int maxOrderSetbacks = 5;
        spooler_job.set_max_order_setbacks(maxOrderSetbacks);
        if (spooler_job.max_order_setbacks() != maxOrderSetbacks) {
            spooler_log.warn(UnwantedMessage.MAX_ORDER_SETBACKS.toString());
        }

        checkNotEmpty(spooler_job.configuration_directory(), UnwantedMessage.CONFIG_DIR);
        checkNotEmpty(spooler_job.folder_path(), UnwantedMessage.FOLDER_PATH);
        spooler_log.info("include_path=" + spooler_job.include_path());

        if(!SchedulerAPIIT.JobObjectJobPath().string().equals("/"+spooler_job.name())){
            spooler_log.warn(UnwantedMessage.JOB_PATH.toString());
        }

        spooler_log.info("process_class name="+spooler_job.process_class().name());
        spooler_log.info("process_class max_processes="+spooler_job.process_class().max_processes());
        spooler_log.info("process_class remote_scheduler="+spooler_job.process_class().remote_scheduler());
        spooler_log.info("title="+spooler_job.title());

        if (!spooler_job.script_code().isEmpty()){
            throw new Exception("Expected emtpy script_code for java job");
        }

        Job removeableJob = spooler.job(SchedulerAPIIT.RemoveMeJobPath().string());
        removeableJob.remove();
        try {
            spooler.job(SchedulerAPIIT.RemoveMeJobPath().string());
            spooler_log.warn(UnwantedMessage.REMOVE.toString());
        }
        catch (RuntimeException e){
            spooler_log.info("job "+SchedulerAPIIT.RemoveMeJobPath().string()+" has been removed ("+ e +")");
        }

        spooler_job.set_state_text(SchedulerAPIIT.TestStateText());
        return spooler_task.order() != null;
    }

    private void checkNotEmpty(String text, UnwantedMessage message) {
        if (Strings.isNullOrEmpty(text)) {
            spooler_log.warn(message.toString());
        }
    }
}
