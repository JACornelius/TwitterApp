package twitterapp.src;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAppConfiguration extends Configuration {

    Logger LOGGER = LoggerFactory.getLogger(TwitterAppConfiguration.class);

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
