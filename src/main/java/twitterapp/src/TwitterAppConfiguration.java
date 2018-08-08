package twitterapp.src;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import twitter4j.Twitter;




public class TwitterAppConfiguration extends Configuration {


    @JsonProperty("twitter")
    TwitterConfiguration twitterConfig = new TwitterConfiguration();
    Twitter twitter;

    public Twitter getTwitter(){
        return twitter;

    }



}
