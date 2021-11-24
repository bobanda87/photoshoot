package com.boom.photoshoot_app.web;

import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.data.payloads.request.PhotographerRequest;
import com.boom.photoshoot_app.data.payloads.response.PhotographerResponse;
import com.boom.photoshoot_app.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photographer")
public class PhotographerController {
    @Autowired
    PhotographerService photographerService;

    @GetMapping("/all")
    public ResponseEntity<List<Photographer>> getAllPhotographers () {
        List<Photographer> photographers = photographerService.getAllPhotographers();
        return new ResponseEntity<>(photographers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Photographer> getPhotographerById (@PathVariable("id") Long id) {
        Photographer photographer = photographerService.getPhotographer(id);
        return new ResponseEntity<>(photographer, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PhotographerResponse> addPhotographer(@RequestBody PhotographerRequest photographerRequest) {
        PhotographerResponse newPhotographer = photographerService.createPhotographer(photographerRequest);
        return new ResponseEntity<>(newPhotographer, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePhotographer(@PathVariable("id") Long id) {
        photographerService.deletePhotographer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
