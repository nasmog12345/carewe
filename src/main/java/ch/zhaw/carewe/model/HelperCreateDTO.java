package ch.zhaw.carewe.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HelperCreateDTO {
    private String name;
    private String address;
    private String email;
    private List<String> skills;
    private String bio;
    private HelperState helperState;
}
