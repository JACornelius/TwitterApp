package twitterapp.src;

import twitter4j.*;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/tweeting")

public class TwitterAppResource {
    static final int MAX_LENGTH = 280;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTimeline()
    {
        String timeline = "Printing home timeline: \n";
        String newStatusUpdate = "This is your new status: ";
        Twitter t = TwitterFactory.getSingleton();
        try{
            List<Status> statuses = t.getHomeTimeline();

            for(Status s: statuses)
            {
                timeline = timeline + s.getUser().getName() + ": " + s.getText() + "\n";
            }

            String tweet = "trying to tweet with dropwizard round 2";
            t.updateStatus(tweet);
            newStatusUpdate = newStatusUpdate + tweet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return timeline + "\n\n" + newStatusUpdate;
    }




}
