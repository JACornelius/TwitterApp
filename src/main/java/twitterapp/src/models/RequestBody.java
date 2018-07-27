package twitterapp.src.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody{
    @JsonProperty("name")
    public String name;
    @JsonProperty("message")
    public String message;
}