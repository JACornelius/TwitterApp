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
    public String getConsumerKey() { return consumerKey; }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public void setConsumerSecret(String consumerSecret){
        this.consumerSecret = consumerSecret;
    }

    public void setConsumerKey(String consumerKey){
        this.consumerKey = consumerKey;
    }

    public void setAccessTokenSecret(String accessTokenSecret){
        this.accessTokenSecret = accessTokenSecret;
    }
}
