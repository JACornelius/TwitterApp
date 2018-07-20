package twitterapp.src;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAppConfiguration extends Configuration {


    @JsonProperty("twitter")
    TwitterConfiguration twitterConfig = new TwitterConfiguration();
    Twitter twitter;

    @JsonProperty("twitter")
    public Twitter getTwitter(){

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(twitterConfig.getConsumerKey())
                    .setOAuthConsumerSecret(twitterConfig.getConsumerSecret())
                    .setOAuthAccessToken(twitterConfig.getAccessToken())
                    .setOAuthAccessTokenSecret(twitterConfig.getAccessTokenSecret());
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
        return twitter;
    }


}
