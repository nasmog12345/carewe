package ch.zhaw.carewe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.carewe.model.Helper;

public interface HelperRepository extends MongoRepository<Helper, String> {

    Helper findFirstByEmail(String helperEmail);
   
}

