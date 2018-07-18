package twitterapp.src;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterAppConfiguration extends Configuration {
    @NotEmpty
    String accessToken;
    @NotEmpty
    String accessTokenSecret;
    @NotEmpty
    String consumerSecret;
    @NotEmpty
    String consumerKey;

    @JsonProperty
    public String getAccessToken(){
        return accessToken;
    }

    @JsonProperty
    public String getConsumerSecret(){
        return consumerSecret;
    }

    @JsonProperty
    public String getAccessTokenSecret(){
        return accessTokenSecret;
    }

    @JsonProperty
    public String getConsumerKey(){
        return consumerKey;
    }

    public Twitter getTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthAccessTokenSecret(getAccessTokenSecret())
                .setOAuthAccessToken(getAccessToken())
                .setOAuthConsumerKey(getConsumerKey())
                .setOAuthConsumerSecret(getConsumerSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }


}
