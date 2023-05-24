package ch.zhaw.carewe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")

public class HelperController {
    @Autowired
    HelperRepository helperRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/helper")
    public ResponseEntity<Helper> createHelper(@RequestBody HelperCreateDTO helperCreateDTO) {
        Helper helper = new Helper(helperCreateDTO.getName(), helperCreateDTO.getAddress(), helperCreateDTO.getEmail(),
                helperCreateDTO.getSkills(), helperCreateDTO.getBio(), helperCreateDTO.getHelperState());
        Helper savedHelper = helperRepository.save(helper);
        return new ResponseEntity<>(savedHelper, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/helper")
    public ResponseEntity<Page<Helper>> getHelper(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) String address,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<Helper> helpers;

        if (address != null && !address.isEmpty()) {
            helpers = helperRepository.findByAddress(address , PageRequest.of(pageNumber - 1, pageSize));
        } else if (skills != null && !skills.isEmpty()) {
            helpers = helperRepository.findBySkillsIn(skills , PageRequest.of(pageNumber - 1, pageSize));
        } else {
            helpers = helperRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        }

        return new ResponseEntity<>(helpers, HttpStatus.OK);
    }

    /*
     * @CrossOrigin(origins = "http://localhost:8081")
     * 
     * @GetMapping("/helper")
     * public ResponseEntity<List<Helper>> getAllHelpers() {
     * List<Helper> helpers = helperRepository.findAll();
     * return new ResponseEntity<>(helpers, HttpStatus.OK);
     * }
     */

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

    @GetMapping("/me/helper")
    public ResponseEntity<Helper> assignToMe(@AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getClaimAsString("email");
        Helper helper = helperRepository.findFirstByEmail(userEmail);
        if (helper != null) {
            return new ResponseEntity<>(helper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
