package sergeant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import librarymanagementsystemproject.Postgres;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class SetupProjectDatabase {
    
    // Postgres Object - allow for connection and querying the database    
    private static Postgres db = new Postgres();
    
    /**
     * Main method or executing all necessary table entries and tables for the project
     * @param args
     */
    public static void main(String[] args) {
        
        // Adding tables and fields to database
        try{            
            // All the SQL scripts are saved in a text file
            Scanner scnScript = new Scanner(new File("./TextFiles/postgresScript.txt"));            
            while(scnScript.hasNext()){
                String sql = scnScript.nextLine();   
                
                /*
                For the scripts, if the script begins with ###, it is a comment and should be ignored.
                If the line fetched is empty, it should also be ignored.
                */
                if(sql.startsWith("###") || sql.equals(""))
                    continue; // exit if statement
                else   
                    db.update(sql); // executing the SQL script
                
            }// end while
            scnScript.close();                                 
            
            
        }catch(FileNotFoundException err){ // Handling any caught excpetions
            err.printStackTrace();
        }// end try-catch
        
        // Statement saying that the database was successfully connected to and that all scripts ran without any errors
        System.out.println("[DB] Successfully created tables and populated the necessary tables");
        
    }// main method   

}// END SetupProjectDatabase class
