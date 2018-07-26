package twitterapp.src.exceptions;

public class EmptyTweetException extends Exception {
    public EmptyTweetException(String message){
        super(message);
    }
}
