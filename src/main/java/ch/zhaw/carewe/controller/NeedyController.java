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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyCreateDTO;
import ch.zhaw.carewe.repository.HelperRepository;
import ch.zhaw.carewe.repository.NeedyRepository;

@RestController
@RequestMapping("/api")
public class NeedyController {

    @Autowired
    private NeedyRepository needyRepository;

    @Autowired
    private HelperRepository helperRepository;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/me/assignedneedy")
    public ResponseEntity<List<Needy>> getAssignedNeedyForCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getClaimAsString("email");
        Helper helper = helperRepository.findFirstByEmail(userEmail);
        if (helper != null) {
            List<Needy> assignedNeedy = needyRepository.findByHelperId(helper.getId());
            if (!assignedNeedy.isEmpty()) {
                return new ResponseEntity<>(assignedNeedy, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/needy/me/assignedneedy/{helperId}")
    public ResponseEntity<List<Needy>> getAssignedNeedyForUser(@PathVariable String helperId) {
        List<Needy> assignedNeedies = needyRepository.findByHelperId(helperId);
        if (!assignedNeedies.isEmpty()) {
            return new ResponseEntity<>(assignedNeedies, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/needy")
    public ResponseEntity<Needy> createNeedy(@RequestBody NeedyCreateDTO nDTO) {
        Needy needy = new Needy(nDTO.getName(), nDTO.getEmail(), nDTO.getAddress(), nDTO.getNumber(), nDTO.getNeeds(),
                nDTO.getNotes(), nDTO.getNeedyState());
        Needy savedNeedy = needyRepository.save(needy);
        return new ResponseEntity<>(savedNeedy, HttpStatus.CREATED);
    }

    /*
     * @CrossOrigin(origins = "http://localhost:8081")
     * 
     * @GetMapping("/searching")
     * public ResponseEntity<List<Needy>> getByAddressen(@RequestParam(required =
     * false) String address) {
     * List<Needy> adressen = needyRepository.findByAddress(address);
     * return new ResponseEntity<>(adressen, HttpStatus.OK);
     * }
     * 
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/needy")
    public ResponseEntity<Page<Needy>> getNeedyByAddressOrNeeds(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) List<String> needs,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        Page<Needy> needyList;

        if (address != null && !address.isEmpty()) {
            needyList = needyRepository.findByAddress(address, PageRequest.of(pageNumber - 1, pageSize));
        } else if (needs != null && !needs.isEmpty()) {
            needyList = needyRepository.findByNeedsIn(needs, PageRequest.of(pageNumber - 1, pageSize));
        } else {
            needyList = needyRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        }

        return new ResponseEntity<>(needyList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/needy/{id}")
    public ResponseEntity<Needy> getNeedyById(@PathVariable String id) {
        Optional<Needy> optNeedy = needyRepository.findById(id);
        if (optNeedy.isPresent()) {
            return new ResponseEntity<>(optNeedy.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * @CrossOrigin(origins = "http://localhost:8081")
     * 
     * @GetMapping("/needy/aggregation/state")
     * public List<NeedyStateAggregation> getNeedyStateAggregation() {
     * return needyRepository.getNeedyStateAggregation();
     * }
     */
}
