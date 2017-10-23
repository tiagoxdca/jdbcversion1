package persistence;

import domain.Pet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public interface IPetDAO {

    List<Pet> findAll() throws SQLException;

    Pet findByName(String name);

    int countByGender(Pet.SEX gender) throws SQLException;

    List<Pet> bornBetween(LocalDate begin, LocalDate end);

    List<Pet> deadBetween(LocalDate begin, LocalDate end);
}
