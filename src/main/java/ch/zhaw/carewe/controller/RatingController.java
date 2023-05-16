package ch.zhaw.carewe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.carewe.model.Rating;
import ch.zhaw.carewe.model.RatingDTO;
import ch.zhaw.carewe.repository.RatingRepository;

@RestController
@RequestMapping("/api")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/rating")
    public ResponseEntity<Rating> createHelper(@RequestBody RatingDTO rDTO) {
        Rating rating = new Rating(rDTO.getTitel(), rDTO.getName(),
                rDTO.getZahl(), rDTO.getRezession(), rDTO.getHelperId());
        Rating savedRating = ratingRepository.save(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

}
