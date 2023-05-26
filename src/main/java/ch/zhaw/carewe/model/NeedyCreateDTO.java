package ch.zhaw.carewe.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NeedyCreateDTO {
    private String name;
    private String email;
    private String address;
    private String number;
    private List<String> needs;
    private String notes;
    private NeedyState needyState;
 private String helperId;
 
}