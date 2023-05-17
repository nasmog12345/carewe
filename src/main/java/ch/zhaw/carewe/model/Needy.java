package ch.zhaw.carewe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("needy")
public class Needy {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String address;
    @NonNull
    private String number;
    @NonNull
    private List<String> needs;
    @NonNull
    private NeedyState needyState;

}
