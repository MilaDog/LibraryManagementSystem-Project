package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 29 May 2020

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class Checks {

    private Postgres db = new Postgres();    
    private ResultSet result = null;
    
    private static String validNameCharacters = "abcdefghijklmnopqrstuvwxyz-";
    private static String validEmailLocalPartCharacters = "abcdefghijklmnopqrstuvwxyz1234567890.!#$%&'*+-/=?^_`{|}~";
    private static String validEmailDomainCharacters = "abcdefghijklmnopqrstuvwxyz1234567890-.";
    
    public boolean checkLogin(String inputEmail, String inputPassword){
        
        String email = "";
        String password = "";
        boolean flag = false;
        
        try{            
            String query = "SELECT r.email, l.password FROM registered_users AS r INNER JOIN login_details AS l ON r.userid = l.userid WHERE r.email = '" + inputEmail + "'";            
            result = db.fetch(query);
            
            if(result.next()){
                email = result.getString("email");
                password = result.getString("password");
                
                if(email.equalsIgnoreCase(inputEmail))
                    if(password.equalsIgnoreCase(inputPassword))
                        flag = true;
                
            }            
            result.close();
            
        }catch(SQLException err){
            err.printStackTrace();
        }// END try-catch 
        
        return flag;
    }// END checkLogin()
    
    
    public boolean checkPassword(String password1, String password2){
        boolean flag = false;
        
        if(password1.equalsIgnoreCase(password2))
            flag = true;
        else
            flag = false;
        return flag;
    }// END checkPassword()
    
    
    public boolean checkNames(String name){
        boolean flag = true;
        
        String characters[] = name.toLowerCase().split("");
        
        for(String x: characters)
            if(!this.validNameCharacters.contains(x))
                flag = false;
        
        return flag;
    }// END checkNames()
    
    public boolean checkPhoneCell(String number){
        boolean flag = true;
        
        // Checks if the number does not contain an alphabetic letters or any other characters
        try{
            int x = Integer.parseInt(number);
        }catch(NumberFormatException err){
            flag = false;
        }
        
        // Checks length of number - default is 10
        if(number.length() != 10){
            flag = false;
        }
        
        return flag;
    }// END checkPhoneCell()
    
    public boolean existingEmail(String email){
        boolean flag = false;
        
        try{
            String query = "SELECT * FROM registered_users AS r WHERE r.email = '" + email + "'";
            result = db.fetch(query);
            
            if(result.isBeforeFirst())
                flag = true;
            
            
        }catch(SQLException err){
            err.printStackTrace();
        }
        return flag;
    }
    
    public boolean checkEmail(String email){
        boolean flag = true;
        
        if(containsAT(email)){
            String parts[] = email.toLowerCase().split("@");
            String localPart[] = parts[0].split("");
            String domain[] = parts[1].split("");
            
            // Checking of the LocalPart - before the @
            for(String x: localPart)
                // Checking if each character is a valid character - if invalid, email is invalid
                if(validEmailLocalPartCharacters.contains(x) == false)
                    flag = false;
            
            // Checking local-part length -  x > 64 characters is invalid
            if(parts[0].length() > 64)
                flag = false;
            
            // Checking if there are any consecutive dots - if so, invalid
            if(parts[0].contains(".."))
                flag = false;
            
            // Checking if the local-part starts or ends with a dot - if so, invalid
            if(parts[0].startsWith(".") || parts[0].endsWith("."))
                flag = false;
            
           
            // Checking of the Domain - after the @
            for(String x: domain)
                // Check if each character is a vliad character - if invalid, email is invalid
                if(validEmailDomainCharacters.contains(x) == false)
                    flag = false;
            
            // Checking domain length - (x > 63) characters is invalid
            if(parts[1].length() > 63)
                flag = false;
            
            // Checking if the domain contains at least one dot - valid subdomain. If not, email is invalid
            if(!parts[1].contains(".")){
                flag = false;
            }
            
            // Checking if the domain starts or ends with a dot - if so, invalid
            if(parts[1].startsWith(".") || parts[1].endsWith("."))
                flag = false;
            
            // Checking if the domain starts or ends with a hyphen - if so, invalid
            if(parts[1].startsWith("-") || parts[1].endsWith("-"))
                flag = false;
            
            // Checking if there are any consecutive dots - if so, invalid
            if(parts[1].contains(".."))
                flag = false;
            
        }else{
            flag = false;  
        }
                
        return flag;
    }// END checkEmail()
    
    private boolean containsAT(String email){  
       char characters[] = email.toLowerCase().toCharArray();
       int countAT = 0;
       
       for(char x: characters){
           int xValue = x;
           if(xValue == 64)
               countAT++;
       }// END loop
       
       if(countAT == 1)
           return true;
       else
           return false;
       
    }// END containsAT()      
    
    public boolean isStaffEmail(String email){
        boolean flag = false;
        String query = String.format("SELECT * FROM registered_users AS r INNER JOIN staff AS s ON s.UserID = r.UserID WHERE r.email = '%s'", email);
        result = db.fetch(query);
        try{
            if(result.isBeforeFirst()){
                flag = true;
            }else{
                flag = false;
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return flag;
    }// END isStaffEmail()    
    
    public boolean isStaff(String member){
        boolean flag = false;
        String name[] = member.replace(" ", "").split(",");
        String query = String.format("SELECT * FROM registered_users AS rm INNER JOIN staff AS s ON s.userid = rm.userid WHERE rm.surname = '%s' AND rm.first_name = '%s'", name[0], name[1]);
        result = db.fetch(query);
        
        try{
            if(result.isBeforeFirst()){
                flag = true;
            }else{
                flag = false;
            }
        }catch(SQLException err){
            err.printStackTrace();
        }
        return flag;
    }// END isStaff()
        
}// END Checks class
