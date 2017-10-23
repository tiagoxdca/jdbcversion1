package persistence;

import com.mysql.jdbc.JDBC4ResultSet;
import database.DB;
import domain.Pet;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetDAO implements IPetDAO {

    // JDBC driver name and database URL
    final String DB_URL = "jdbc:mysql://localhost/webdb";

    // Database credentials
    final String USER = "root";
    final String PASS = "q1w2e3r4";

    @Override
    public List<Pet> findAll() throws SQLException {

        ResultSet rs = DB.getConnection().query("SELECT * FROM pet");

        List<Pet> pets = new ArrayList<>();// new

        while (rs.next()) {
            Pet pet = buildPetFromData(rs);
            pets.add(pet);
        }
        return pets;
    }

    @Override
    public Pet findByName(String name) {
        String sql = "select * from pet where name = ?";
        Pet pet = null;
        try {
            PreparedStatement preparedStatement = DB.getConnection().getPreparedStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pet = buildPetFromData(resultSet);
                return pet;
            }
        } catch (SQLException e) {

            System.out.println("Erro sql: " + e.getMessage());
        }
        return pet;
    }

    private Pet convertDataToPet(ResultSet rs, String name, String owner, String species, String birth, String death)
            throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dbirth = null;
        Date ddeath = null;
        try {
            if (birth != null)
                dbirth = (Date) formatter.parse(birth);
            if (death != null)
                ddeath = (Date) formatter.parse(death);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        char petSex = (rs.getString("sex") == null) ? 'u' : rs.getString("sex").charAt(0);
        Pet.SEX gender = null;
        switch (petSex) {
            case 'm':
                gender = Pet.SEX.MALE;
                break;
            case 'f':
                gender = Pet.SEX.FEMALE;
                break;
            default:
                gender = Pet.SEX.UNDIFINED;
                break;
        }

        Pet pet = Pet.builder().name(name).owner(owner).species(species).gender(gender).birth(dbirth).death(ddeath)
                .build();
        return pet;
    }

    @Override
    public int countByGender(Pet.SEX gender) {

        String sql = "select count(*) as count from pet where sex = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            preparedStatement = DB.getConnection().getConn().prepareStatement(sql);
            preparedStatement.setString(1, gender.getLetter());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("count");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }



    private Pet buildPetFromData(ResultSet resultSet) throws SQLException {
        String nome = resultSet.getString("name");
        String owner = resultSet.getString("owner");
        String species = resultSet.getString("species");
        String birth = resultSet.getString("birth");
        String death = resultSet.getString("death");


        Pet pet = convertDataToPet(resultSet, nome, owner, species, birth, death);
        return pet;
    }

    @Override
    public List<Pet> bornBetween(LocalDate begin, LocalDate end) {
        String sql = "select * from pet where birth > ? and death < ?";
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DB.getConnection().getPreparedStatement(sql);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String sbirth = begin.format(formatter).toString();
            String sdeath = end.format(formatter).toString();
            preparedStatement.setString(1, sbirth);
            preparedStatement.setString(2, sdeath);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Pet pet = buildPetFromData(resultSet);
                pets.add(pet);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }


    @Override
    public List<Pet> deadBetween(LocalDate begin, LocalDate end) {
        String sql = "select * from pet where death between ? and ?";
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DB.getConnection().getPreparedStatement(sql);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String sbirth = begin.format(formatter).toString();
            String sdeath = end.format(formatter).toString();
            preparedStatement.setString(1, sbirth );
            preparedStatement.setString(2, sdeath);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Pet pet = buildPetFromData(resultSet);
                pets.add(pet);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }
}
