package framework.web;

import framework.cache.MemCachedManager;
import framework.util.LoggerHandler;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * spring application init and listening web context
 * Created by Willow on 3/12/17.
 */
public class WebContextListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        try {
            Class.forName(MemCachedManager.class.getName());
        } catch (ClassNotFoundException e) {
            LoggerHandler.error(e,"init memcached error");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }
}
