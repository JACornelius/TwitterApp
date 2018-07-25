package twitterapp.src.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitterapp.src.TwitterAppConfiguration;

import java.util.List;

import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppService{
        Logger log = (Logger) LoggerFactory.getLogger("myLogger");
        static TwitterAppService service = null;
        Twitter twitter;

        public TwitterAppService TwitterAppService(){
            return service;
        }

        public static TwitterAppService getService(){
            if(service == null){
                service = new TwitterAppService();
            }
            return service;
        }

        public void setTwitter(Twitter t){
            twitter = t;
        }

        public TwitterAppService setService(TwitterAppService s){
            service = s;
            return service;
        }

        public boolean testBadTweet(String tweet){
            if(tweet.length() > MAX_LENGTH){
                log.warn("Tweet is too long, keep it within 280 characters");
                return true;
            }
            else if(tweet.isEmpty() == true){
                log.warn("An empty tweet was entered");
                return true;
            }
            else{
                return false;
            }
        }

        public Status postTweet(String tweet){
            Status s;
            if(testBadTweet(tweet) == true){
                s = null;
                return s;
            }
                try{

                        s = twitter.updateStatus(tweet);
                        log.info("Tweet("+tweet+") has been posted.");
                        return s;


                }
                catch(Exception e){
                    log.error("There was a problem on the server side, please try again later.", e);
                    s = null;
                    return s;
                }




        }

        public List<Status> getTimeline(){
            List<Status> statuses;
            try{
                statuses = twitter.getHomeTimeline();
                if (statuses != null) {
                    log.info("Timeline has been printed.");
                    return statuses;
                }
            }
            catch(Exception e){
                log.error("There was a problem on the server side.", e);
                statuses = null;

            }
            return statuses;
        }
    }

