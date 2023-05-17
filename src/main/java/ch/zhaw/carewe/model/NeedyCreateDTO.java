package ch.zhaw.carewe.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NeedyCreateDTO {
    private String name;
    private String email;
    private String address;
    private String number;
    private List<String> needs;
    private NeedyState needyState;
 
}