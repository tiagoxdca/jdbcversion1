import domain.Pet;
import domain.Pet.SEX;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.PetService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PetServiceUT {

    private PetService petService;

    @Before
    public void setUp() {
        petService = new PetService();
    }

    @Test
    public void findAll_returnsSuccess() throws SQLException {
    	
        Assert.assertEquals(8, petService.findAll().size());
    }
    
    @Test
    public void findByName(){
    	String name = "Browser";
    	Pet pet = petService.findByName(name);
    	Assert.assertEquals(name, pet.getName());
    }

    @Test
    public void findAll_genderAllM_returnsSuccess() throws SQLException {

        List<Pet> pets = petService.findAll();

        Assert.assertTrue(pets.get(0).getGender() == SEX.FEMALE );
        Assert.assertTrue(pets.get(1).getGender() == SEX.MALE);
        Assert.assertTrue(pets.get(2).getGender() == SEX.MALE);
    }
    
    @Test
    public void findAll_BornBetweenDates(){
    	LocalDate begin = LocalDate.of(1993, 3, 04);
    	LocalDate end = LocalDate.of(2010, 6, 04);
    	List<Pet> petList = petService.bornBetween(begin, end);
    	petList.forEach(System.out::println);
    	Assert.assertNotNull(petList);
    }

    @Test
    public void findPetsDeathAfterDate(){
        LocalDate begin = LocalDate.of(2012, 6, 04);
        LocalDate end = LocalDate.of(2017, 1, 04);

        List<Pet> pets = petService.deadBetween(begin, end);
        Assert.assertEquals("Tiago", pets.get(0).getName());
    }

    @Test
    public void countPetsByGender(){

        int count = petService.countByGender(SEX.FEMALE);
        Assert.assertEquals(1, count);

    }
}
