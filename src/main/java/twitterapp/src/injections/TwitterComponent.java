package twitterapp.src.injections;

import dagger.Component;
import twitter4j.Twitter;
import twitterapp.src.TwitterConfiguration;

import javax.inject.Singleton;

@Singleton
@Component(modules = TwitterModule.class)
public interface TwitterComponent {
     TwitterInjection buildTwitterInjection();
}
