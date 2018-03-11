package ru.otus_jee_matveev_anton.contextlisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus_jee_matveev_anton.repository.JPAUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class InitDdOnStartupListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(InitDdOnStartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JPAUtil.getEntityManagerFactory();
        log.info("EntityManagerFactory was created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.shutdown();
        log.info("EntityManagerFactory was shutdown");
    }
}
