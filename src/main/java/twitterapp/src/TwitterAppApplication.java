package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import twitterapp.src.injections.DaggerTwitterComponent;
import twitterapp.src.injections.TwitterComponent;
import twitterapp.src.injections.TwitterModule;


public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{

    public static void  main (String[] args) throws Exception{

        new TwitterAppApplication().run(args);
    }

    @Override
    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {
        TwitterModule twitterModule = new TwitterModule(configuration.twitterConfig);
        TwitterComponent component = DaggerTwitterComponent.builder()
                .twitterModule(twitterModule)
                .build();
        environment.jersey().register(component.buildTwitterAppResource());
    }



}