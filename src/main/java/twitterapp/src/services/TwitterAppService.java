package twitterapp.src.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitterapp.src.TwitterAppException;

import java.util.List;


public class TwitterAppService {
    private static Logger log = (Logger) LoggerFactory.getLogger("myLogger");
    static TwitterAppService service = null;
    Twitter twitter;
    int MAX_LENGTH = 280;

    public static TwitterAppService getService() {
        if (service == null) {
            service = new TwitterAppService();
        }
        return service;
    }

    public void setTwitter(Twitter t) {
        twitter = t;
    }


    public boolean testBadTweet(String tweet) throws TwitterAppException {

        if (tweet.length() > MAX_LENGTH) {
            log.warn("Tweet is too long, keep it within 280 characters");
            throw new TwitterAppException("Tweet is too long, keep it within 280 characters");

        } else if (tweet.length() == 0) {
            log.warn("An empty tweet was entered");
            throw new TwitterAppException("An empty tweet was entered");
        } else {
            return false;
        }

    }


    public Status postTweet(String tweet) throws Exception {
        Status s;
         try {

            s = twitter.updateStatus(tweet);
            log.info("Tweet(" + tweet + ") has been posted.");


        } catch (Exception e) {
            log.error("There was a problem on the server side, please try again later.", e);
            throw new TwitterException("There was a problem on the server side, please try again later");

        }
        return s;

    }

    public List<Status> getTimeline() {
        List<Status> statuses;
        try {
            statuses = twitter.getHomeTimeline();
            return statuses;

        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            statuses = null;

        }
        return statuses;
    }
}

