package ch.zhaw.carewe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyCreateDTO;

import ch.zhaw.carewe.model.NeedyStateAggregation;
import ch.zhaw.carewe.repository.NeedyRepository;

@RestController
@RequestMapping("/api")
public class NeedyController {

    @Autowired
    private NeedyRepository needyRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/needy")
    public ResponseEntity<Needy> createNeedy(@RequestBody NeedyCreateDTO nDTO) {
        Needy needy = new Needy(nDTO.getName(), nDTO.getEmail(), nDTO.getAddress(), nDTO.getNumber(), nDTO.getNeeds(),
             nDTO.getNotes(),nDTO.getNeedyState());
        Needy savedNeedy = needyRepository.save(needy);
        return new ResponseEntity<>(savedNeedy, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/needy")
    public ResponseEntity<List<Needy>> getNeedyByNeeds(@RequestParam(required = false) List<String> needs) {
        List<Needy> needyList;
        if (needs == null) {
            needyList = needyRepository.findAll();
        } else {
            needyList = needyRepository.findByNeedsIn(needs);
            needs.forEach(n -> System.out.println(n));
        }
        return new ResponseEntity<>(needyList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/needy/aggregation/state")
    public List<NeedyStateAggregation> getNeedyStateAggregation() {
        return needyRepository.getNeedyStateAggregation();
    }

}
