package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 29 May 2020

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Daniel
 */
public class Utils {

    private static String postgresUser;
    private static String postgresPassword;
    
    private static void getDetails(){
        
        try{
            // Getting TextFile to read
            Scanner scnFile = new Scanner(new File("./TextFiles/postgresDetails.txt"));
            String line ="";
            
            // Going through the text file
            while(scnFile.hasNext()){
                line = scnFile.nextLine();
                
                // ### is a comment, so is ignored
                if(line.startsWith("###"))
                    continue;
                else{
                    // Getting details
                    Scanner scnLine = new Scanner(line).useDelimiter("#");
                    postgresUser = scnLine.next();
                    postgresPassword = scnLine.next();
                    scnLine.close();
                }// end if-else
            }// end while
            scnFile.close();       
        }catch(FileNotFoundException err){
            err.printStackTrace();
        }// END try-catch
        
    }// END getDetails()    
    
    /**
     * Get the username for Postgres - used for connecting to the database
     * @return postgresUser Username
     */
    public static String getPostgresUser(){
        getDetails();
        return postgresUser;
    }
    
    /**
     * Get the password for Postgres - used for connecting to the database
     * @return postgresPassword Password
     */
    public static String getPostgresPassword(){
        getDetails();
        return postgresPassword;
    }
    
    public static String formatDate(LocalDate date){
        String formatDOB = date.format(DateTimeFormatter.ofPattern("ddMMyy"));
        return formatDOB;
    }
    
    public static String removeHyphens(String str){
        return str.replace("-", "");
    }
    
    public static String toTitleCase(String str){
        String genre[] = str.split(" ");
        
        String finalGenre = "";
        for(String part: genre){
            if(part.equalsIgnoreCase("and") || part.equalsIgnoreCase("a") || part.equalsIgnoreCase("the")){ // add in of AND check if pos0 starts with either of these, then that gets capitalised
                continue;
            }else{
                String first = Character.toString(part.charAt(0)).toUpperCase();
                String titledGenre = first + part.substring(1);
                finalGenre = finalGenre + titledGenre + "-";
            }// END - check for articles - do not get capitialised
        }
        
        
        if(finalGenre.endsWith("-")){
            finalGenre = finalGenre.substring(0, finalGenre.length()-1).replace("-", " ");
        }        
        return finalGenre;
    }
    
}// END Utils class
