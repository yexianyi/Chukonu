package net.yxy.chukonu.spring.security.servlet.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionTrackingMode;
import javax.servlet.annotation.WebListener;

/**
 * 
 * https://www.baeldung.com/spring-security-session
 * 6. Prevent using URL Parameters for Session Tracking
 * @author yexianyi
 */
@WebListener
public class SetSessionTrackingModeListener implements ServletContextListener {

  public void contextInitialized(ServletContextEvent sce) {
    Set<SessionTrackingMode> modes = new HashSet<SessionTrackingMode>();
//    modes.add(SessionTrackingMode.URL); // thats the default behaviour!
    modes.add(SessionTrackingMode.COOKIE);
    sce.getServletContext().setSessionTrackingModes(modes);
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

}
