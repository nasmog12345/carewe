package ch.zhaw.carewe.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyState;
import ch.zhaw.carewe.model.NeedyStateAggregation;

public interface NeedyRepository extends MongoRepository<Needy, String> {
    List<Needy> findByHelperId(String helperId);
    Page<Needy> findByNeedsIn(List<String> needs, Pageable pageable);
    List<Needy> findByHelperEmail(String helperEmail);
    Page<Needy> findByAddress(String address, Pageable pageable);
    List<Needy> findByNeedyState(NeedyState needyState);
    @Aggregation(
        "{$group:{_id:'$needyState',needyIds:{$push:'$_id',},count:{$count:{}}}}")
        List<NeedyStateAggregation> getNeedyStateAggregation();
}


  
 