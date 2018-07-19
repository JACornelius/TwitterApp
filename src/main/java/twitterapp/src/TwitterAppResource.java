package twitterapp.src;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.serverError;

@Path("/api/1.0/twitter")
public class TwitterAppResource {
    static final int MAX_LENGTH = 280;
    Twitter t;
    List<Status> statuses;

    Logger LOGGER = LoggerFactory.getLogger(TwitterAppResource.class);

    public TwitterAppResource(Twitter twitter){

        t = twitter;
    }


    @GET
    @Path("/timeline")
    public Response getTimeline() {

        try {
            statuses = t.getHomeTimeline();
            if (statuses != null) {
                LOGGER.info("Timeline has been printed.");
                return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).build();
            } else {
                    LOGGER.error("List of Statuses are null.");
                    return serverError().entity("There was a problem on the server side, please try again later.").build();
            }
        } catch (TwitterException e) {
            LOGGER.error("There was a problem on the server side.");
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }


    @POST
    @Path("/tweet")
    public Response postTweet(String tweet){
    if (tweet.length() > MAX_LENGTH) {
        LOGGER.error("Tweet is too long, keep it within 280 characters");
        return Response.serverError().entity("Tweet is too long, keep it within 280 characters").build();
    }
    else if(tweet.length() == 0){
        LOGGER.error("An empty tweet was entered.");
        return Response.serverError().entity("An empty tweet entered").build();
    }
    else {
        try {
            t.updateStatus(tweet);
        }
        catch (Exception e) {
            LOGGER.error("There was a problem on the server side, please try again later.");
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
        LOGGER.info("Tweet("+tweet+") has been posted.");
        return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).entity("Tweet("+tweet+") has been posted.").build();
        }
    }
}



