package com.boom.photoshoot_app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderUploadPhotosRequest {
    @NotBlank
    @NotNull
    private byte[] photosZip;

    public byte[] getPhotosZip() {
        return photosZip;
    }

    public void setPhotosZip(byte[] photosZip) {
        this.photosZip = photosZip;
    }
}
