import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.TwitterResponse;

import java.util.ArrayList;
import java.util.List;


public class TwitterResponseList<T> extends ArrayList<T> implements ResponseList<T> {

    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    public int getAccessLevel(){
        return 1;
    }

}
