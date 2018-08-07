package twitterapp.src;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import twitter4j.Twitter;


import twitterapp.src.injections.DaggerTwitterComponent;
import twitterapp.src.injections.TwitterComponent;


import javax.inject.Inject;

public class TwitterAppConfiguration extends Configuration {

    @JsonProperty("twitter")
    TwitterConfiguration twitterConfig = new TwitterConfiguration();
    Twitter twitter;

    @Inject
    @JsonProperty("twitter")
    public Twitter getTwitter(){
        TwitterComponent component = DaggerTwitterComponent.create();
        Twitter twitter = component.buildTwitter();
        return twitter;

    }



}
