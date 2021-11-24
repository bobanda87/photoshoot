package com.boom.photoshoot_app.service;

import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.data.payloads.request.PhotographerRequest;
import com.boom.photoshoot_app.data.payloads.response.PhotographerResponse;
import com.boom.photoshoot_app.data.repository.PhotographerRepository;
import com.boom.photoshoot_app.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotographerService implements PhotographerServiceInterface {
    @Autowired
    PhotographerRepository photographerRepository;

    @Override
    public PhotographerResponse createPhotographer(PhotographerRequest photographerRequest) {
        Photographer photographer = new Photographer();
        photographer.setFirstName(photographerRequest.getFirstName());
        photographer.setLastName(photographerRequest.getLastName());
        photographerRepository.save(photographer);

        return new PhotographerResponse("New photographer created successfully");
    }

    @Override
    public Photographer getPhotographer(Long photographerId) {
        return photographerRepository.findById(photographerId).orElseThrow(() -> new ResourceNotFoundException("Photographer", "id", photographerId));
    }

    @Override
    public List<Photographer> getAllPhotographers() {
        return photographerRepository.findAll();
    }

    @Override
    public void deletePhotographer(Long photographerId) {
        if (photographerRepository.getById(photographerId).getId().equals(photographerId)) {
            photographerRepository.deleteById(photographerId);
        } else {
            throw new ResourceNotFoundException("Photographer", "id", photographerId);
        }
    }
}
