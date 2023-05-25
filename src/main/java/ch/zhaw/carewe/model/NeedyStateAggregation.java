package ch.zhaw.carewe.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NeedyStateAggregation {
    private String id;
    private List<String> needyIds;
    private String count;
}
