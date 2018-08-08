package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitterapp.src.injections.DaggerTwitterComponent;
import twitterapp.src.injections.ServiceModule;
import twitterapp.src.injections.TwitterComponent;
import twitterapp.src.injections.TwitterModule;
import twitterapp.src.resources.TwitterAppResource;


public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{

    public static void  main (String[] args) throws Exception{

        new TwitterAppApplication().run(args);
    }

    @Override
    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {
        TwitterModule twitterModule = new TwitterModule(configuration.twitterConfig);
        Twitter twitter = twitterModule.provideTwitter();
        TwitterComponent component = DaggerTwitterComponent.builder()
                .twitterModule(twitterModule)
                .serviceModule(new ServiceModule(twitter))
                .build();
        environment.jersey().register(component.buildTwitterAppResource(new TwitterAppResource()));
    }


}