package twitterapp.src.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;


import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.TwitterPost;

import java.util.ArrayList;
import java.util.List;

import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;


public class TwitterAppService {
    private static Logger log = (Logger) LoggerFactory.getLogger("myLogger");
    static TwitterAppService service = null;
    public Twitter twitter;


    public static TwitterAppService getService() {
        if (service == null) {
            service = new TwitterAppService();
        }
        return service;
    }

    public void setTwitter(Twitter t) {
        twitter = t;
    }

    public TwitterPost postTweet(TwitterPost inputTwitterPost) throws Exception {
        TwitterPost twitterPost;

        System.out.println(inputTwitterPost.getMessage());
        if (inputTwitterPost.getMessage().length() > MAX_LENGTH) {
            log.warn("Tweet is too long, keep it within 280 characters");
            throw new LongTweetException("Tweet is too long, keep it within 280 characters");

        } else if (inputTwitterPost.getMessage().length() == 0) {
            log.warn("An empty tweet was entered");
            throw new EmptyTweetException("An empty tweet was entered");
        } else {
            try {
                twitter.updateStatus(inputTwitterPost.getMessage());
                twitterPost = inputTwitterPost;
                log.info("Tweet(" + inputTwitterPost.getMessage() + ") has been posted.");
            } catch (Exception e) {

                log.error("There was a problem on the server side, please try again later.");
                throw new TwitterAppException("There was a problem on the server side, please try again later");
            }
            return twitterPost;

        }
    }

    public List<TwitterPost> getTimeline() {
        List<Status> statuses;
        List<TwitterPost> listTwitterPost = new ArrayList<>();

        try {

            statuses = twitter.getHomeTimeline();
            for(Status s: statuses){
                TwitterPost twitterPost = new TwitterPost(null, null, null, null, null);
                twitterPost.setMessage(s.getText());
                listTwitterPost.add(twitterPost);
            }

        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);

        }

        return listTwitterPost;
    }
}

