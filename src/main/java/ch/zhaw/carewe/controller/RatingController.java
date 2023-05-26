package ch.zhaw.carewe.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    // @Secured("ROLE_admin")
    public ResponseEntity<Rating> createHelper(@RequestBody RatingDTO rDTO) {
        Rating rating = new Rating(rDTO.getTitel(), rDTO.getName(),
                rDTO.getZahl(), rDTO.getRezession(), rDTO.getHelperId());
        Rating savedRating = ratingRepository.save(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/rating/helper/{helperId}")
    public ResponseEntity<List<Rating>> getRatingsByHelperId(@PathVariable String helperId) {
        List<Rating> ratings = ratingRepository.findByHelperId(helperId);
        if (!ratings.isEmpty()) {
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/rating")
    public ResponseEntity<List<Rating>> getRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

}
