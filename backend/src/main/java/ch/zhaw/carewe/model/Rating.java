package ch.zhaw.carewe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("rating")
public class Rating {
@Id
private String id;
@NonNull
private String titel;
@NonNull
private String name;
@NonNull 
private int zahl;  
@NonNull
private String rezession;
@NonNull
private String helperId;

}
