package com.boom.photoshoot_app.data.payloads.response;

public class PhotographerResponse {
    private String message;

    public PhotographerResponse(String message){
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
