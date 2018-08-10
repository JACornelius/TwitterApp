package twitterapp.src.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;


import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;

import javax.inject.Inject;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


import static java.util.stream.Collectors.toList;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;


public class TwitterAppService {
    private static Logger log = (Logger) LoggerFactory.getLogger(TwitterAppService.class);
    LoadingCache<Integer, Optional<List<TwitterPost>>> cacheTimeline = CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<Integer, Optional<List<TwitterPost>>>() {
                        @Override
                        public Optional<List<TwitterPost>> load(Integer integer) throws Exception {
                            return Optional.empty();
                        }
                    }
            );
    LoadingCache<Integer, Optional<List<TwitterPost>>> cacheFilter= CacheBuilder.newBuilder()
            .build(
                    new CacheLoader<Integer, Optional<List<TwitterPost>>>() {
                        @Override
                        public Optional<List<TwitterPost>> load(Integer integer) throws Exception {
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

    public Optional<List<TwitterPost>> getTimeline() throws TwitterAppException{
        try {

            Optional<List<TwitterPost>> resultListTwitterPost = Optional.ofNullable(twitter.getHomeTimeline().stream()
                    .map(s -> new TwitterPost(s.getText(),
                            s.getUser().getName(),
                            s.getUser().getScreenName(),
                            s.getUser().getProfileImageURL(),
                            s.getCreatedAt()))
                    .collect(toList()));
            Integer eTag = resultListTwitterPost.hashCode();
            if(cacheTimeline.get(eTag).isPresent() == false){
                cacheTimeline.invalidateAll();
                cacheTimeline.put(eTag, resultListTwitterPost);
                return resultListTwitterPost;
            }
            else{
               return cacheTimeline.get(eTag);
            }

        } catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to get timeline. There was a problem on the server side");
        }


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
                    return Optional.ofNullable(twitter.updateStatus(input.getMessage()))
                            .map(s -> new TwitterPost(s.getText(),
                                s.getUser().getName(),
                                s.getUser().getScreenName(),
                                s.getUser().getProfileImageURL(),
                                s.getCreatedAt()));

            } catch (Exception e) {

                log.error("There was a problem on the server side, please try again later.", e);
                throw new TwitterAppException("Unable to post tweet. There was a problem on the server side, please try again later");

            }
        }
    }

    public Optional<List<TwitterPost>> filterTweets(String filter) throws TwitterAppException{
        try {
                Optional<List<TwitterPost>> resultFilterdTweets = Optional.ofNullable(twitter.getHomeTimeline().stream()
                    .filter(s -> s.getText().contains(filter))
                    .map(s -> new TwitterPost(s.getText(),
                                              s.getUser().getName(),
                                              s.getUser().getScreenName(),
                                              s.getUser().getProfileImageURL(),
                                              s.getCreatedAt()))
                    .collect(toList()));
                Integer eTag = resultFilterdTweets.hashCode();
                if(cacheFilter.get(eTag).isPresent() == false){
                    cacheFilter.put(eTag, resultFilterdTweets);
                    return resultFilterdTweets;
                }
                else{
                    return cacheFilter.get(eTag);
                }
        }
        catch (Exception e) {
            log.error("There was a problem on the server side.", e);
            throw new TwitterAppException("Unable to filter tweets. There was a problem on the server side.");
        }

    }


}

