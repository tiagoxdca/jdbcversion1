package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import domain.Pet;
import persistence.PetDAO;

public class PetService implements IPetService {

    private PetDAO petDAO;

    public PetService() {
        this.petDAO = new PetDAO();
    }

    @Override
    public List<Pet> findAll() throws SQLException {
        return petDAO.findAll();
    }

    @Override
    public Pet findByName(String name) {
    	
    	return petDAO.findByName(name);
    }

    @Override
    public int countByGender(Pet.SEX gender) {

        return petDAO.countByGender(gender);

    }

    @Override
    public List<Pet> deadBetween(LocalDate begin, LocalDate end) {
    	List<Pet> pets = petDAO.deadBetween(begin, end);
        return pets;
    }

	@Override
	public List<Pet> bornBetween(LocalDate begin, LocalDate end) {
		
    	List<Pet> pets = petDAO.bornBetween(begin, end);
        return pets;
	
	}
}
