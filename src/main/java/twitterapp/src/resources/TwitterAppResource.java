package twitterapp.src.resources;

import twitter4j.Status;
import twitter4j.Twitter;

import twitterapp.src.services.TwitterAppService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/1.0/twitter")
public class TwitterAppResource {

    public static int MAX_LENGTH = 280;
    public TwitterAppService service;

    public void setService(TwitterAppService s) {
        service = s;
    }

    public TwitterAppResource(Twitter twitter) {
        service = TwitterAppService.getService();
        service.setTwitter(twitter);
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {
        List<Status> statuses = service.getTimeline();
        if (statuses.isEmpty() == false) {
            return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }

    @POST
    @Path("/tweet")
    public Response postTweet(String tweet) throws Exception{
        if (service.postTweet(tweet) != null) {
            return Response.ok().entity("Tweet(" + tweet + ") has been posted.").build();
        } else {
            try {
                service.testBadTweet(tweet);
                return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
            } catch (Exception e) {
                return Response.serverError().entity("The tweet is longer than 280 characters or empty.").build();
            }
        }


    }

}



