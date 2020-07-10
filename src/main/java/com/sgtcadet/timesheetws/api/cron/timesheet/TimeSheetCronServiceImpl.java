package com.sgtcadet.timesheetws.api.cron.timesheet;


import com.sgtcadet.timesheetws.api.timesheet.TimeSheet;
import com.sgtcadet.timesheetws.api.timesheet.TimeSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Configuration
//@EnableScheduling
public class TimeSheetCronServiceImpl implements TimeSheetCronService, SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(TimeSheetCronServiceImpl.class);
    TaskScheduler taskScheduler;
    private ScheduledFuture<?> approveTimesheets;

    @Autowired private TimeSheetRepository repository;

    @Override
    public void approveJob() {
        ExecutorService timesheetThreadService = Executors.newFixedThreadPool(10);

        timesheetThreadService.execute(() -> System.out.println("Task Running"));

        timesheetThreadService.shutdown();
    }

//    @Scheduled(fixedRate = 1000)
    public void executeTask2() {
        System.out.println(Thread.currentThread().getName() + " The Task2 executed at " + new Date());
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("timesheet-thread");
        threadPoolTaskScheduler.initialize();
        approveTimesheets(threadPoolTaskScheduler);
        this.taskScheduler=threadPoolTaskScheduler;
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    public void approveTimesheets(TaskScheduler scheduler) {
        approveTimesheets = scheduler.schedule(() -> {
            logger.info("Approval Job starts");
            // find all timesheet with status of pending
            List<TimeSheet> sheets = repository.findAllPending();
            logger.info(String.format("Sheets in pending state: %d", sheets.size()));

            // update each timesheet status to approve
            if(!sheets.isEmpty()){
                for (TimeSheet sheet:sheets) {
                    sheet.setStatus("A");
                    logger.info(String.format("Updating sheet `%d` to approve",sheet.getId()));
                    repository.save(sheet);
                }
            }
            logger.info("Approval Job End");
        }, triggerContext -> {
            String cronExp = "0/5 * * * * ?"; //can be pulled from the database
            return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
        });


    }
}