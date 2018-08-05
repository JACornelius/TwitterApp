package twitterapp.src.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;


import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;


public class TwitterAppService {
    private static Logger log = (Logger) LoggerFactory.getLogger(TwitterAppService.class);
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

    public Optional<TwitterPost> postTweet(RequestBody input) throws Exception {

        if (input.getMessage().length() > MAX_LENGTH) {
            log.warn("Tweet is too long, keep it within 280 characters");
            throw new LongTweetException("Tweet is too long, keep it within 280 characters");

        } else if (input.getMessage().length() == 0) {
            log.warn("An empty tweet was entered");
            throw new EmptyTweetException("An empty tweet was entered");
        } else {
            try {

                return Optional.ofNullable((Stream.of(twitter.updateStatus(input.getMessage()))
                        .map(s -> new TwitterPost(s.getText(),
                                s.getUser().getName(),
                                s.getUser().getScreenName(),
                                s.getUser().getProfileImageURL(),
                                s.getCreatedAt()))
                        .collect(toList())).get(0));


            } catch (Exception e) {

                log.error("There was a problem on the server side, please try again later.", e);
                throw new TwitterAppException("Unable to post tweet. There was a problem on the server side, please try again later");

            }
        }
    }

    public Optional<List<TwitterPost>> filterTweets(String filter) throws TwitterAppException{
        try {
            return Optional.ofNullable(twitter.getHomeTimeline().stream()
                    .filter(s -> s.getText().contains(filter))
                    .map(s -> new TwitterPost(s.getText(),
                                              s.getUser().getName(),
                                              s.getUser().getScreenName(),
                                              s.getUser().getProfileImageURL(),
                                              s.getCreatedAt()))
                    .collect(toList()));
        }
        catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to filter tweets. There was a problem on the server side.");
        }

    }

    public Optional<List<TwitterPost>> getTimeline() throws TwitterAppException{


        try {
          return Optional.ofNullable(twitter.getHomeTimeline().stream()
                    .map(s -> new TwitterPost(s.getText(),
                                              s.getUser().getName(),
                                              s.getUser().getScreenName(),
                                              s.getUser().getProfileImageURL(),
                                              s.getCreatedAt()))
                    .collect(toList()));



        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to get timeline. There was a problem on the server side");
        }


    }
}

