package ch.zhaw.carewe.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyStateChangeDTO;
import ch.zhaw.carewe.service.NeedyService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    NeedyService needyService;

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/assignneedy")
    public ResponseEntity<Needy> assignNeedy(@RequestBody NeedyStateChangeDTO changeS) {
        String needyId = changeS.getNeedyId();
        String helperEmail = changeS.getHelperEmail();
        Optional<Needy> needy = needyService.assignNeedy(needyId, helperEmail);
        if (needy.isPresent()) {
            return new ResponseEntity<>(needy.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}