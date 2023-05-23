package ch.zhaw.carewe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RatingDTO {
   private String id;
   private String titel;
   private String name;
   private int zahl;
   private String rezession;
   private String helperId;

}
