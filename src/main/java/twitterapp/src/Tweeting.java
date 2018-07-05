package twitterapp.src;
import java.util.Scanner;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Tweeting {
    static final int MAX_LENGTH = 280;

    public void tweet() {
        Twitter twitter = TwitterFactory.getSingleton();
        System.out.println("Enter your tweet: ");
        Scanner sc = (new Scanner(System.in)).useDelimiter("\\n");
        String message = sc.nextLine();

        if (message.length() > MAX_LENGTH) {
            System.out.println("Tweet too long. ");
        } else {
            try {
                twitter.updateStatus(message);
            } catch (TwitterException te) {
                te.printStackTrace();
            }
        }
    }
}
