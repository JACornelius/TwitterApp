package twitterapp.src.injections;

import dagger.Component;
import twitter4j.Twitter;

import javax.inject.Singleton;

@Singleton
@Component(modules = TwitterModule.class)
public interface TwitterComponent {
     Twitter buildTwitter();
}
