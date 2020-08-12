package usi.tagger.listeners;

import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import usi.tagger.utilities.Utilities;

@WebListener
public class SystemStartStop implements ServletContextListener {

    private Context environmentContext;

    private Optional<String> getPropertyFromContextAsString(String name) {
        try {
            return Optional.of((String) environmentContext.lookup(name));
        } catch (Exception e) {
            System.out.println("SystemStartStop.getPropertyFromContextAsString() Cannot find " + name);
            return Optional.empty();
        }
    }

    private Optional<Integer> getPropertyFromContextAsInteger(String name) {
        try {
            return Optional.of(Integer.parseInt((String) environmentContext.lookup(name)));
        } catch (Exception e) {
            System.out.println("SystemStartStop.getPropertyFromContextAsInteger() Cannot find " + name);
            return Optional.empty();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize Utilities
//            Context initialContext = new InitialContext();
//            Context environmentContext = (Context) initialContext.lookup("java:comp/env");
//            String dataResourceName = "jdbc/tagger";
//            DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
//            return dataSource.getConnection();

        System.out.println("\n\n\n. Setting Utilities properties from context");
        try {
            Context initialContext = new InitialContext();
            this.environmentContext = (Context) initialContext.lookup("java:comp/env");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utilities.dbDriver = getPropertyFromContextAsString("dbDriver").orElse(Utilities.dbDriver);
        Utilities.dbAddress = getPropertyFromContextAsString("dbAddress").orElse(Utilities.dbAddress);
        Utilities.dbUsername = getPropertyFromContextAsString("dbUsername").orElse(Utilities.dbUsername);
        Utilities.dbPassword = getPropertyFromContextAsString("dbPassword").orElse(Utilities.dbPassword);
        Utilities.maxNumberOfEvaluators = getPropertyFromContextAsInteger("maxNumberOfEvaluators")
                .orElse(Utilities.maxNumberOfEvaluators);
        Utilities.maxNumberOfEvaluationsPerUser = getPropertyFromContextAsInteger("maxNumberOfEvaluationsPerUser")
                .orElse(Utilities.maxNumberOfEvaluationsPerUser);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}
