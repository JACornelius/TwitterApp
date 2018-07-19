
package twitterapp.src;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAppApplication extends Application<TwitterAppConfiguration>
{
    public static void  main (String[] args) throws Exception{
        new TwitterAppApplication().run(args);
    }


    public void run(final TwitterAppConfiguration configuration, final Environment environment) throws Exception
    {
        TwitterConfiguration twitterProperties = configuration.getTwitter();
        Twitter t;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterProperties.getConsumerKey())
                .setOAuthConsumerSecret(twitterProperties.getConsumerSecret())
                .setOAuthAccessToken(twitterProperties.getAccessToken())
                .setOAuthAccessTokenSecret(twitterProperties.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        t = tf.getInstance();
        TwitterAppResource resource = new TwitterAppResource(t);
        environment.jersey().register(resource);
    }
}