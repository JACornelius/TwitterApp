package twitterapp.src;
import java.util.Scanner;


/*Combining both Project1 (Posting a Tweet) and Project2 (Posting timeline to console)*/

public class main {
    public static void main(String args[]){
        System.out.println("Would you like to... \n 1: Post a tweet \n 2: Show timeline on console \n 3: Both");
        Scanner sc = new Scanner(System.in);
        char c = sc.next().charAt(0);
        switch (c){
            case '1':
                Project1.main(args);
                break;
            case '2':
                Project2.main(args);
                break;
            case '3':
                Project1.main(args);
                Project2.main(args);
                break;
            default:
                System.out.println("Invalid answer.");
        }

    }
}
