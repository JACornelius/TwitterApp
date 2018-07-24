package twitterapp.src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import java.util.List;
import static twitterapp.src.TwitterAppResource.MAX_LENGTH;

public class TwitterAppService{
    Logger log = (Logger) LoggerFactory.getLogger("myLogger");
    static TwitterAppService service = null;
    Twitter twitter;

    public static TwitterAppService getService(){
        if(service == null){
            service = new TwitterAppService();
        }
        return service;
    }

    public void setTwitter(Twitter t){
        twitter = t;
    }


    public String servicePostTweet(String tweet){
        if(tweet.length() > MAX_LENGTH){
            log.warn("Tweet is too long, keep it within 280 characters");
            return "Tweet is too long, keep it within 280 characters";
        }
        else if(tweet.isEmpty() == true){
            log.warn("An empty tweet was entered");
            return "No tweet entered";
        }
        else{
            try{
                twitter.updateStatus(tweet);
                log.info("Tweet("+tweet+") has been posted.");
                return "Tweet("+tweet+") has been posted.";
            }
            catch(Exception e){
                log.error("There was a problem on the server side, please try again later.", e);
                return "There was a problem on the server side, please try again later.";
            }

        }

    }

    public List<Status> serviceGetTimeline(){
        try{
            List<Status> statuses = twitter.getHomeTimeline();
            if (statuses != null) {
                log.info("Timeline has been printed.");
                return statuses;

            } else {
                log.warn("List of Statuses are null.");
                return statuses;
            }
        }
        catch(Exception e){
            log.error("There was a problem on the server side.", e);
             List<Status> statusException = null;
             return statusException;
        }
    }
}
