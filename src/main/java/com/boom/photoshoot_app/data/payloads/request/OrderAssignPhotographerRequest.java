package com.boom.photoshoot_app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderAssignPhotographerRequest {
    @NotBlank
    @NotNull
    private Long photographerId;

    public Long getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(Long photographerId) {
        this.photographerId = photographerId;
    }
}
