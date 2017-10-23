import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class DBConnectionUT {

    // JDBC driver name and database URL
    final String DB_URL = "jdbc:mysql://localhost/webdb";

    //  Database credentials
    final String USER = "root";
    final String PASS = "123456";

    @Test
    public void test_connection() throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM pet";
        ResultSet rs = stmt.executeQuery(query);
//list pet
        while(rs.next()) {
            String petName = rs.getString("name");
            Assert.assertNotNull(petName);
            //pet novo
            //adicionar lista
        }
    }
}
