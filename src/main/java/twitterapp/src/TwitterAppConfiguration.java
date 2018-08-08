package twitterapp.src;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import twitter4j.Twitter;


import twitterapp.src.injections.DaggerTwitterComponent;
import twitterapp.src.injections.TwitterComponent;
import twitterapp.src.injections.TwitterInjection;
import twitterapp.src.injections.TwitterModule;


public class TwitterAppConfiguration extends Configuration {


    @JsonProperty("twitter")
    TwitterConfiguration twitterConfig = new TwitterConfiguration();
    Twitter twitter;

    public Twitter getTwitter(){
        TwitterComponent component = DaggerTwitterComponent.builder()
                .twitterModule(new TwitterModule(twitterConfig))
                .build();
        TwitterInjection injection = component.buildTwitterInjection();
        return injection.getTwitter();

    }



}
