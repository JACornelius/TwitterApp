package twitterapp.src.injections;


import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitterapp.src.TwitterConfiguration;


import javax.inject.Singleton;

@Module
public class TwitterModule {
    TwitterConfiguration twitterConfig;


    public TwitterModule(TwitterConfiguration twitterConfig){
        this.twitterConfig = twitterConfig;
    }

    @Provides
    @Singleton
    public Twitter provideTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterConfig.getConsumerKey())
                .setOAuthConsumerSecret(twitterConfig.getConsumerSecret())
                .setOAuthAccessToken(twitterConfig.getAccessToken())
                .setOAuthAccessTokenSecret(twitterConfig.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
       return tf.getInstance();

    }
}


