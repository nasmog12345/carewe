package ch.zhaw.carewe.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.carewe.model.Needy;
import ch.zhaw.carewe.model.NeedyStateAggregation;

public interface NeedyRepository extends MongoRepository<Needy, String> {
    List<Needy> findByNeedsIn(List<String> needs);

    @Aggregation("{$group:{_id:'$needyState',needyIds:{$push:'$_id',},count:{$count:{}}}}")
    List<NeedyStateAggregation> getNeedyStateAggregation();
}
