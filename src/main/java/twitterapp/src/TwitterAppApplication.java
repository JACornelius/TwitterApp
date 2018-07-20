package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

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