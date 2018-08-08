package twitterapp.src.injections;

import twitterapp.src.services.TwitterAppService;

import javax.inject.Inject;

public class ServiceInjection {

    private TwitterAppService service;

    @Inject
    public ServiceInjection(TwitterAppService service){
        this.service = service;
    }

    public void setService(TwitterAppService service){
        this.service = service;
    }

    public TwitterAppService getService(){
        return this.service;
    }
}
