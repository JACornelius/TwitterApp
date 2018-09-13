package twitterapp.src;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitterapp.src.injections.DaggerTwitterComponent;
import twitterapp.src.injections.TwitterComponent;
import twitterapp.src.injections.TwitterModule;

public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{
    public static void  main (String[] args) throws Exception {

        new TwitterAppApplication().run(args);
    }

    @Override
    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        TwitterModule twitterModule = new TwitterModule(configuration.twitterConfig);
        TwitterComponent component = DaggerTwitterComponent.builder()
                .twitterModule(twitterModule)
                .build();
        environment.jersey().register(component.buildTwitterAppResource());
    }
}