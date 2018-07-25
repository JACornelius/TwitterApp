package twitterapp.src.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.List;

public class TwitterAppService {
        private static Logger log = (Logger) LoggerFactory.getLogger("myLogger");
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

        public boolean testBadTweet(String tweet){int max = 280;
            System.out.println(tweet.length());
            System.out.print(max);
            if(tweet.length() > 280){
                log.warn("Tweet is too long, keep it within 280 characters");
                return true;
            }
            else if(tweet.length() == 0){
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

