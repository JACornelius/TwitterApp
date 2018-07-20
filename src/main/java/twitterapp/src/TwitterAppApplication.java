
package twitterapp.src;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.LoggerFactory;


public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{

    public static void  main (String[] args) throws Exception{

        new TwitterAppApplication().run(args);
    }


    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {

        environment.jersey().register(new TwitterAppResource(configuration.getTwitter()));
    }
}