package ch.zhaw.carewe.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyState;
import ch.zhaw.carewe.repository.HelperRepository;
import ch.zhaw.carewe.repository.NeedyRepository;

@Service
public class NeedyService {
    @Autowired
    NeedyRepository needyRepository;

    @Autowired
    HelperRepository helperRepository;

    public List<Needy> getAssignedNeedyForCurrentUser(String userEmail) {
        return needyRepository.findByHelperEmail(userEmail);
    }
   

    public Optional<Needy> assignNeedy(String needyId, String helperEmail) {
        Optional<Needy> needyToAssign = needyRepository.findById(needyId);
        if (needyToAssign.isPresent()) {
            Needy needy = needyToAssign.get();
            if (needy.getNeedyState() == NeedyState.UNALLOCATED) {
                Helper helper = helperRepository.findFirstByEmail(helperEmail);
                if (helper != null) {
                    needy.setNeedyState(NeedyState.ASSIGNED);
                    needy.setHelperId(helper.getId());
                    needyRepository.save(needy);
                    return Optional.of(needy);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Needy> completeNeedy(String needyId, String helperEmail) {
        Optional<Needy> needyToAssign = needyRepository.findById(needyId);
        if (needyToAssign.isPresent()) {
            Needy needy = needyToAssign.get();
            if (needy.getNeedyState() == NeedyState.ASSIGNED) {
                Helper helper = helperRepository.findFirstByEmail(helperEmail);
                if (helper != null) {
                    if (needy.getHelperId().equals(helper.getId())) {
                        needy.setNeedyState(NeedyState.DONE);
                        needyRepository.save(needy);
                        return Optional.of(needy);
                    }
                }
            }
        }
        return Optional.empty();
    }

   
}
