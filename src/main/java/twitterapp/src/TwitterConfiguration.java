package twitterapp.src;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TwitterConfiguration {

    @JsonProperty("accessToken")
    String accessToken;


    @JsonProperty("accessTokenSecret")
    String accessTokenSecret;


    @JsonProperty("consumerSecret")
    String consumerSecret;


    @JsonProperty("consumerKey")
    String consumerKey;


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
    public String getConsumerKey(){ return consumerKey; }
}
