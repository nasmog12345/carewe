package ch.zhaw.carewe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.model.HelperCreateDTO;
import ch.zhaw.carewe.repository.HelperRepository;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")

public class HelperController {
    @Autowired
    HelperRepository helperRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/helper")
    public ResponseEntity<Helper> createHelper(@RequestBody HelperCreateDTO helperCreateDTO) {
        Helper helper = new Helper(helperCreateDTO.getName(), helperCreateDTO.getEmail(),
                helperCreateDTO.getSkills(), helperCreateDTO.getBio(), helperCreateDTO.getHelperState());
        Helper savedHelper = helperRepository.save(helper);
        return new ResponseEntity<>(savedHelper, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/helper")
    public ResponseEntity<List<Helper>> getAllHelpers() {
        List<Helper> helpers = helperRepository.findAll();
        return new ResponseEntity<>(helpers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/helper/{id}")
    public ResponseEntity<Helper> getHelperById(@PathVariable String id) {
        Optional<Helper> optHelper = helperRepository.findById(id);
        if (optHelper.isPresent()) {
            return new ResponseEntity<>(optHelper.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
