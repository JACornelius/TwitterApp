package twitterapp.src;

import twitter4j.Status;
import twitter4j.Twitter;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/1.0/twitter")
public class TwitterAppResource {

    static final int MAX_LENGTH = 280;
    TwitterAppService service;

    public TwitterAppResource(Twitter twitter){
        service = TwitterAppService.getService();
        service.setTwitter(twitter);
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {
       List<Status> statuses = service.serviceGetTimeline();
       if(statuses != null && statuses.isEmpty() == false){
           return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).build();
       }
       else{
           return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
       }
    }

    @POST
    @Path("/tweet")
    public Response postTweet(String tweet) {

        String r = service.servicePostTweet(tweet);
        if(r.equals("Tweet("+tweet+") has been posted."))
        {
            return Response.ok().entity(r).build();
        }
        else{
            return Response.serverError().entity(r).build();
        }
    }

}



