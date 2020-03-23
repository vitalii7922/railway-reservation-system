package com.tsystems.project.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
 class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
           Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        }
        catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Bean
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
