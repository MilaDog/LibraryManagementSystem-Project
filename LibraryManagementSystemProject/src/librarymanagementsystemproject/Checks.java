package librarymanagementsystemproject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Daniel Ryan Sergeant
 */
public class Checks {

    // Instantiating necessary Objects
    private Postgres db = new Postgres();    
    private ResultSet result = null;
    
    // Initializing necessary variables
    private static String validNameCharacters = "abcdefghijklmnopqrstuvwxyz-";
    private static String validEmailLocalPartCharacters = "abcdefghijklmnopqrstuvwxyz1234567890.!#$%&'*+-/=?^_`{|}~";
    private static String validEmailDomainCharacters = "abcdefghijklmnopqrstuvwxyz1234567890-.";
    
    /**
     *
     * @param inputEmail Takes in the email to check password against
     * @param inputPassword Takes in the password to check against email
     * @return If the account exists or not, as a Boolean
     */
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
    
    /**
     *
     * @param password1 Takes in the password to have checked
     * @param password2 Takes in the password to check against first password
     * @return If the passwords match or not, as a Boolean
     */
    public boolean checkPassword(String password1, String password2){
        boolean flag = false;
        
        if(password1.equalsIgnoreCase(password2))
            flag = true;
        else
            flag = false;
        return flag;
    }// END checkPassword()
    
    /**
     *
     * @param name Takes in the name of the registering user to check that it contains valid characters
     * @return If the name if valid or not, as a Boolean
     */
    public boolean checkNames(String name){
        boolean flag = true;
        
        String characters[] = name.toLowerCase().split("");
        
        for(String x: characters)
            if(!this.validNameCharacters.contains(x))
                flag = false;
        
        return flag;
    }// END checkNames()
    
    /**
     *
     * @param number Takes in the cell phone number of the registering user to check that it is a valid number
     * @return If the cell phone number is valid or not, as a Boolean
     */
    public boolean checkPhoneCellLength(String number){
        boolean flag = true;
        
        // Checks length of number - default is 10
        System.out.println(number.length());
        if(number.length() != 10){
            flag = false;
        }
        
        return flag;
    }// END checkPhoneCellLength()
      
    /**
     *
     * @param number Takes in the cell phone number of the registering user to check that it is a valid number
     * @return If the cell phone number is valid or not, as a Boolean
     */ 
    public boolean checkPhoneCellValidCharactersOrZero(String number){
        boolean flag = true;
        
        // Checks if the number does not contain an alphabetic letters or any other characters
        try{
            int x = Integer.parseInt(number);
        }catch(NumberFormatException err){
            flag = false;
        }
        
        return flag;
    }// END checkPhoneCellValidCharactersOrZero()
    
    /**
     *
     * @param email Takes in the email of the registering user to see if the email already registered or not
     * @return If the email is already registered or not, as a Boolean
     */
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
    
    /**
     *
     * @param email Takes in the email of the registering user to see if it is valid or not
     * @return If the email is valid or not, as a Boolean
     */
    public boolean checkEmail(String email){
        boolean flag = true;
        
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
                
        return flag;
    }// END checkEmail()
    
    /**
     *
     * @param email Takes in the email of the registering user to see if it is valid or not
     * @return If the email contains an '@' character, as a Boolean
     */
    public boolean emailContainsAT(String email){  
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
    
    /**
     *
     * @param email Takes in the email of the logging in user to see if it is the registered email of a staff member or not
     * @return If the email is a staff member email or not, as a Boolean
     */
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
    
    /**
     *
     * @param user Takes in the RegisateredUsers object of the logging in user to see if they are a staff member or not
     * @return If the user is a staff member or not, as a Boolean
     */
    public boolean isStaff(RegisteredUsers user){
        boolean flag = false;
        String query = String.format("SELECT * FROM registered_users AS rm INNER JOIN staff AS s ON s.userid = rm.userid WHERE rm.surname = '%s' AND rm.first_name = '%s'", user.getSurname(), user.getFirstName());
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
