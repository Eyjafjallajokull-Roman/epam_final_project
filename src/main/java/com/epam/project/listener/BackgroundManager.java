package com.epam.project.listener;

import com.epam.project.util.Scheduler;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class BackgroundManager implements ServletContextListener {
    private static final Logger log = Logger.getLogger(BackgroundManager.class);
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Scheduler(), 60, 60, TimeUnit.SECONDS);
        log.info("I`m doing something");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log.info("I`m dead");
        scheduler.shutdownNow();
    }
}
