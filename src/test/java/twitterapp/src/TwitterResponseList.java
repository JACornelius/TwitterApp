package twitterapp.src;

import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import java.util.ArrayList;


public class TwitterResponseList<T> extends ArrayList<T> implements ResponseList<T> {

    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    public int getAccessLevel(){
        return 1;
    }



}
