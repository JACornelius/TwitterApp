package twitterapp.src;
import twitter4j.*;
import java.util.*;

/*Posts a tweet to User's Timeline*/

public class Project1 {
    public static void main(String args[])
    {
        Twitter twitter = TwitterFactory.getSingleton();
        System.out.println("Enter your tweet: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String message = sc.nextLine();

        if(message.length() > 280){
            System.out.println("Tweet too long.");
        }
        try {
            Status status = twitter.updateStatus(message);
        }
        catch(TwitterException te)
        {
            te.printStackTrace();
        }

    }
}
