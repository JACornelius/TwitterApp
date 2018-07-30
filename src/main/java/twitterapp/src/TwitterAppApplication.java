package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitterapp.src.resources.TwitterAppResource;

import java.util.List;

import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{

    public static void  main (String[] args) throws Exception{

        new TwitterAppApplication().run(args);
    }

    @Override
    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {
        environment.jersey().register(new TwitterAppResource(configuration.getTwitter()));
    }


}