package de.rwthaachen.openlap.visualizer.service;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Development configuration class which makes it possible to access h2 database web console
 */
//@Component
public class DBConfiguration {
   /* @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console*//*");
        return registrationBean;
    }*/
}
