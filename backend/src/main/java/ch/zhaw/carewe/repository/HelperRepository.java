package ch.zhaw.carewe.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.carewe.model.Helper;


public interface HelperRepository extends MongoRepository<Helper, String> {

    Helper findFirstByEmail(String helperEmail);
    List<Helper> findBySkillsIn(List<String> skills);
    List<Helper> findByAddress(String address);
    List<Helper> findBySkillsInAndAddress(List<String> skills, String address);
}

