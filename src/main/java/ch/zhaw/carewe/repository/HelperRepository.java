package ch.zhaw.carewe.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.carewe.model.Helper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface HelperRepository extends MongoRepository<Helper, String> {

    Helper findFirstByEmail(String helperEmail);

    Page<Helper> findBySkillsIn(List<String> skills, Pageable pageable);

    Page<Helper> findByAddress(String address, Pageable pageable);

    List<Helper> findBySkillsInAndAddress(List<String> skills, String address);
}
