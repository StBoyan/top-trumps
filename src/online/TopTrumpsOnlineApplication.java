package online;


import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import online.configuration.TopTrumpsJSONConfiguration;
import online.dwResources.GameWebPagesResource;
import online.dwResources.TopTrumpsRESTAPI;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Top Trumps Web Application. This class is complete, you do not need to edit it, you
 * instead need to complete TopTrumpsRESTAPI and the HTML/Javascript views.
 */
public class TopTrumpsOnlineApplication extends Application<TopTrumpsJSONConfiguration> {

    /**
     * This is the main class for the Top Trumps Web application. It is called by TopTrumps.java
     * when the user specifies that they want to run in online mode.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new TopTrumpsOnlineApplication().run(args); // Create a new online application and run it
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    /**
     * This is the Dropwizard run method after argument parsing has happened
     */
    public void run(TopTrumpsJSONConfiguration conf, Environment environment)
            throws Exception {

        // Enable CORS headers (see https://en.wikipedia.org/wiki/Cross-origin_resource_sharing)
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        // Dropwizard expresses things that the user can ask for as resources. We have two of
        // these, the REST api and the HTML/Javascript Webpages

        // REST API
        TopTrumpsRESTAPI restAPI = new TopTrumpsRESTAPI(conf);

        // HTML/Javascript Webpages
        GameWebPagesResource gameScreen = new GameWebPagesResource();

        // Registration tells Dropwizard to host a resource
        environment.jersey().register(restAPI);
        environment.jersey().register(gameScreen);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));



    }


    /**
     * Get the name of the application
     */
    @Override
    public String getName() {
        return "TopTrumps";
    }


    /**
     * An initalization method that attaches the Configuration to the views
     */
    @Override
    public void initialize(Bootstrap<TopTrumpsJSONConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<TopTrumpsJSONConfiguration>());
        bootstrap.addBundle(new AssetsBundle("/content/images"));
    }
}
