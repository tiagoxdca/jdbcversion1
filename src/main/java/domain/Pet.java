package domain;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Pet {
	
	public enum SEX{

		FEMALE('f'), MALE('m'), UNDIFINED('u');

        String letter;

        SEX(Character letter){
		    this.letter = letter.toString();
        }

        public String getLetter() {
            return letter;
        }
    }

    private String name;

    private String owner;

    private String species;

    private SEX gender;

    private Date birth;

    private Date death;
    
}
