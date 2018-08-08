package twitterapp.src.injections;

import twitter4j.Twitter;
import twitterapp.src.TwitterConfiguration;

import javax.inject.Inject;


public class TwitterInjection {

    private Twitter twitter;

    @Inject
    public TwitterInjection(Twitter twitter){
        this.twitter = twitter;

    }

    public void setTwitter(Twitter twitter){
        this.twitter = twitter;
    }

    public Twitter getTwitter(){
        return this.twitter;
    }

}
