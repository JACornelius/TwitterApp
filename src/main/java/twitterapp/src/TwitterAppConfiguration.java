package twitterapp.src;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAppConfiguration extends Configuration {

    @JsonProperty("twitter")
    TwitterConfiguration twitter;

    @JsonProperty("twitter")
    public TwitterConfiguration getTwitter(){
        return twitter;
    }

}
