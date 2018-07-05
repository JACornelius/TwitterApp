package twitterapp.src;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import java.util.List;

/*Print User's Home Timeline*/

public class Timeline {
    public void printTimeline()
    {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Printing home timeline: ");
            for(Status s: statuses){
                System.out.println(s.getUser().getName() + ": " + s.getText());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}


