package com.example.util;

import com.example.entity.Client;
import com.example.entity.Planet;
import com.example.entity.Ticket;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static final String DB_URL = "jdbc:h2:./spacetravel";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static final SessionFactory SESSION_FACTORY = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        runFlywayMigrations();

        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", DB_URL);
        configuration.setProperty("hibernate.connection.username", DB_USER);
        configuration.setProperty("hibernate.connection.password", DB_PASSWORD);

        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Planet.class);
        configuration.addAnnotatedClass(Ticket.class);

        return configuration.buildSessionFactory();
    }

    private static void runFlywayMigrations() {
        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void close() {
        SESSION_FACTORY.close();
    }
}
