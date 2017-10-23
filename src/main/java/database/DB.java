package database;

import java.sql.*;

public class DB {

    // JDBC driver name and database URL
    private String DB_URL = "jdbc:mysql://localhost/webdb";

    //  Database credentials
    private String USER = "root";
    private String PASS = "q1w2e3r4";

    private Connection conn;

    private static DB instance;

    private DB() throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }

    public static synchronized DB getConnection() throws SQLException {
         if(instance == null) {
             instance = new DB();
         };

        return instance;
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
    	return instance.conn.prepareStatement(sql);
    }

    public ResultSet query(String query) throws SQLException{
        Statement statement = instance.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //CRUD
    public int update() {
    	return 0;
    }
    
    public Connection getConn() {
		return conn;
	}
}
