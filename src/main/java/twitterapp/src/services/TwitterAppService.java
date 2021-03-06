package twitterapp.src.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Paging;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitterapp.src.exceptions.EmptyReplyTweetId;
import twitterapp.src.exceptions.EmptyTweetMsgException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.ReplyTweetRequest;
import twitterapp.src.models.PostTweetRequest;
import twitterapp.src.models.TwitterPost;
import javax.inject.Inject;
import java.util.*;
import static java.util.stream.Collectors.toList;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppService {
    private static Logger log = (Logger) LoggerFactory.getLogger(TwitterAppService.class);
    private static int TIMELINE_KEY = 1;

    LoadingCache<Integer, Optional<List<TwitterPost>>> cacheHomeTimeline = CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<Integer, Optional<List<TwitterPost>>>() {
                        @Override
                        public Optional<List<TwitterPost>> load(Integer integer) throws Exception {
                            return Optional.empty();
                        }
                    }
            );
    LoadingCache<Integer, Optional<List<TwitterPost>>> cacheUserTimeline = CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<Integer, Optional<List<TwitterPost>>>() {
                        @Override
                        public Optional<List<TwitterPost>> load(Integer integer) throws Exception {
                            return Optional.empty();
                        }
                    }
            );
    LoadingCache<String, Optional<List<TwitterPost>>> cacheFilter= CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<String, Optional<List<TwitterPost>>>() {
                        @Override
                        public Optional<List<TwitterPost>> load(String filter) throws Exception {
                            return Optional.empty();
                        }
                    }
            );

    @Inject
    public Twitter twitter;

    @Inject
    public TwitterAppService(Twitter twitter){
        this.twitter = twitter;
    }

    public Optional<TwitterPost> postTweet(PostTweetRequest input) throws Exception {
        if (input.getMessage().length() > MAX_LENGTH) {
            log.warn("Tweet is too long, keep it within 280 characters");
            throw new LongTweetException("Tweet is too long, keep it within 280 characters");
        } else if (input.getMessage().length() == 0) {
            log.warn("No tweet was provided.");
            throw new EmptyTweetMsgException("No tweet was provided.");
        } else {
            try {
                return Optional.ofNullable(twitter.updateStatus(input.getMessage()))
                        .map(s -> {
                                TwitterPost twitterPost = new TwitterPost(s);
                                cacheHomeTimeline.invalidateAll();
                                cacheUserTimeline.invalidateAll();
                                cacheFilter.invalidateAll();
                                return twitterPost;
                        });
            } catch (Exception e) {
                log.error("There was a problem on the server side, please try again later.", e);
                throw new TwitterAppException("Unable to post tweet. There was a problem on the server side, please try again later");
            }
        }
    }
    public Optional<TwitterPost> replyTweet(ReplyTweetRequest input) throws Exception {

        if (input.getMessage().length() > MAX_LENGTH) {
            log.warn("Tweet is too long, keep it within 280 characters");
            throw new LongTweetException("Tweet is too long, keep it within 280 characters");
        } else if (input.getMessage().length() == 0) {
            log.warn("No tweet was provided.");
            throw new EmptyTweetMsgException("No tweet was provided.");
        } else if (input.getReplyTweetID() == 0) {
            System.out.println(input.getReplyTweetID());
            log.warn("No reply tweetID was provided");
            throw new EmptyReplyTweetId("No reply TweetID was provided");
        } else {
            try {
                return Optional.ofNullable(twitter.updateStatus(new StatusUpdate(input.getMessage())
                                                                        .inReplyToStatusId(input.getReplyTweetID())))
                        .map(s -> {
                            TwitterPost twitterPost = new TwitterPost(s);
                            cacheHomeTimeline.invalidateAll();
                            cacheUserTimeline.invalidateAll();
                            cacheFilter.invalidateAll();
                            return twitterPost;
                        });
            } catch (Exception e) {
                log.error("There was a problem on the server side, please try again later.", e);
                throw new TwitterAppException("Unable to post tweet. There was a problem on the server side, please try again later");
            }
        }
    }

    public Optional<List<TwitterPost>> filterTweets(String filter) throws TwitterAppException {
        try {
            if(cacheFilter.get(filter).isPresent() == false) {
                Optional<List<TwitterPost>> resultFilteredTweets = Optional.ofNullable(twitter.getHomeTimeline().stream()
                        .filter(s -> s.getText().contains(filter))
                        .map(s -> new TwitterPost(s))
                        .collect(toList()));
                cacheFilter.put(filter, resultFilteredTweets);
            }
            return cacheFilter.get(filter);
        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to filter tweets. There was a problem on the server side.");
        }
    }

    public Optional<List<TwitterPost>> getHomeTimeline() throws TwitterAppException {
        try {
            if(cacheHomeTimeline.get(TIMELINE_KEY).isPresent() == false){
                Paging page = new Paging(1,25);
            Optional<List<TwitterPost>> resultListTwitterPost = Optional.ofNullable(twitter.getHomeTimeline(page).stream()
                    .map(s -> new TwitterPost(s))
                    .collect(toList()));

                cacheHomeTimeline.put(TIMELINE_KEY, resultListTwitterPost);
                }
               return cacheHomeTimeline.get(1);
        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to get timeline. There was a problem on the server side");
        }
    }

    public Optional<List<TwitterPost>> getUserTimeline() throws TwitterAppException {
        try {
            if(cacheUserTimeline.get(TIMELINE_KEY).isPresent() == false){
                Paging page = new Paging(1,25);
                Optional<List<TwitterPost>> resultListTwitterPost = Optional.ofNullable(twitter.getUserTimeline(page).stream()
                        .map(s -> new TwitterPost(s))
                        .collect(toList()));
                cacheUserTimeline.put(TIMELINE_KEY, resultListTwitterPost);
            }
            return cacheUserTimeline.get(1);
        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to get timeline. There was a problem on the server side");
        }
    }
}

