package com.boom.photoshoot_app.data.payloads.response;

public class OrderResponse {
    private String message;

    public OrderResponse(String message){
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
