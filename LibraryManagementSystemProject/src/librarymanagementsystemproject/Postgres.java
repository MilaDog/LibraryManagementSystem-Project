package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 29 May 2020

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Daniel
 */
public class Postgres {
    
    // Varibles used for connection to Postgres and Database and for statement executions
    private Connection connection = null;
    private ResultSet fetched = null;
    private Statement query = null;

    /**
     * Constructor Method. Loads the PostgreSQL Driver and connects to the database
     */
    public Postgres(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/librarymanagementsystem", Utils.getPostgresUser(), Utils.getPostgresPassword());
            
            System.out.println("Postgres Class");
            System.out.println("Driver Successfully connected");
            System.out.println("[DB] Connected Successfully");
            System.out.println();
            
        }catch(ClassNotFoundException | SQLException err){
            err.printStackTrace();
            
            System.out.println("[Error] Either Driver or DB connected unsuccessfully");
        }// END try-catch
        
    }// END Postgres()
    
    /**
     * Method used to fetch data from the Database - runs SELECT queries
     * @param statement Query to be executed
     * @return fetched ResultSet - the data fetched from the Database
     */
    public ResultSet fetch(String statement){
        try{
            query = connection.createStatement();
            fetched = query.executeQuery(statement);
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
        return fetched;
    }// END fetch()
    
    /**
     * Method used to make changes to a table or the Database - runs UPDATE, INSERT and DELETE queries
     * @param statement Query to be executed
     */
    public void update(String statement){
        try{
            query = connection.createStatement();
            query.executeUpdate(statement);
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch
    }// END update()
    
}// END Postgres class
