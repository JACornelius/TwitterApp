package twitterapp.src;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TweetJson {
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Tweet")
    private String tweet;
    @JsonProperty("Time")
    private String time;

    @JsonCreator
    public TweetJson(@JsonProperty("Username") String un, @JsonProperty("Tweet") String tw, @JsonProperty("Time")String tm){
        this.time = tm;
        this.username = un;
        this.tweet = tw;
    }
}
