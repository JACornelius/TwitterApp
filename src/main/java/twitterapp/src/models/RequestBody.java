package twitterapp.src.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody{
    @JsonProperty("name")
    public String name;
    @JsonProperty("message")
    public String message;

    public void setName(String name){
        this.name = name;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getName(){
        return name;
    }

    public String getMessage()
    {
        return message;
    }}

