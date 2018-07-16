package twitterapp.src;
import java.util.Scanner;


/*Combining both Project1 (Posting a Tweet) and Project2 (Posting timeline to console)*/

public class MainFile {
    public static void main(String args[]){
        System.out.println("Would you like to... \n 1: Post a tweet \n 2: Show timeline on console \n 3: Both");
        Scanner sc = new Scanner(System.in);
        char c = sc.next().charAt(0);
        switch (c){
            case '1':
                Tweeting tweet = new Tweeting();
                tweet.tweet();
                break;
            case '2':
                Timeline timeline = new Timeline();
                timeline.printTimeline();
                break;
            case '3':
                Tweeting tweet1 = new Tweeting();
                tweet1.tweet();
                Timeline timeline1 = new Timeline();
                timeline1.printTimeline();
                break;
            default:
                System.out.println("Invalid answer.");
        }

    }
}