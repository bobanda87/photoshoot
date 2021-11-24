package com.boom.photoshoot_app.service;

import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.data.payloads.request.PhotographerRequest;
import com.boom.photoshoot_app.data.payloads.response.PhotographerResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PhotographerServiceInterface {
    PhotographerResponse createPhotographer(PhotographerRequest photographerRequest);
    Photographer getPhotographer(Long photographerId);
    List<Photographer> getAllPhotographers();
    void deletePhotographer(Long photographerId);
}
