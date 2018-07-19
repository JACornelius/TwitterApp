package twitterapp.src;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TwitterConfiguration {
    /*@NotEmpty
    @NotNull*/
    @JsonProperty("accessToken")
    String accessToken;

    /*@NotEmpty
    @NotNull
    @JsonProperty*/
    String accessTokenSecret;

    /*@NotEmpty
    @NotNull
    @JsonProperty*/
    String consumerSecret;

    /*@NotEmpty
    @NotNull
    @JsonProperty*/
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
    public String getConsumerKey(){

        return consumerKey;
    }
}
