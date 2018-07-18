
package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{
    public static void  main (String[] args) throws Exception{
        new TwitterAppApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TwitterAppConfiguration> bootstrap){
    }

 /*   @Override
    public void run(final TwitterAppConfiguration config, Environment env) {
        env.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(config).to(TwitterAppConfiguration.class);
            }
        });
    }*/

    public void run(final TwitterAppConfiguration configuration, final Environment environment)
    {
        environment.jersey().register(new TwitterAppResource(configuration.getTwitter()));
    }
}