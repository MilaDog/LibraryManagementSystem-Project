package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 May 2020

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Algorithms {
    
    // Instantiating Dictionarys
    private static Map<Character, String> dictAlpha = new HashMap<Character, String>();
    private static Map<Integer, String> dictNumeric = new HashMap<Integer, String>();
        
    
    static{
        // Numeric Dictionary
        dictNumeric.put(1, "01");
        dictNumeric.put(2, "02");
        dictNumeric.put(3, "03");
        dictNumeric.put(4, "04");
        dictNumeric.put(5, "05");
        dictNumeric.put(6, "06");
        dictNumeric.put(7, "07");
        dictNumeric.put(8, "08");
        dictNumeric.put(9, "09");
    }
    
    static{
        // Alphabetic Dictionary
        dictAlpha.put('a', "01");
        dictAlpha.put('b', "02");
        dictAlpha.put('c', "03");
        dictAlpha.put('d', "04");
        dictAlpha.put('e', "05");
        dictAlpha.put('f', "06");
        dictAlpha.put('g', "07");
        dictAlpha.put('h', "08");
        dictAlpha.put('i', "09");
        dictAlpha.put('j', "10");
        dictAlpha.put('k', "11");
        dictAlpha.put('l', "12");
        dictAlpha.put('m', "13");
        dictAlpha.put('n', "14");
        dictAlpha.put('o', "15");
        dictAlpha.put('p', "16");
        dictAlpha.put('q', "17");
        dictAlpha.put('r', "18");
        dictAlpha.put('s', "19");
        dictAlpha.put('t', "20");
        dictAlpha.put('u', "21");
        dictAlpha.put('v', "22");
        dictAlpha.put('w', "23");
        dictAlpha.put('x', "24");
        dictAlpha.put('y', "25");
        dictAlpha.put('z', "26");
    }
    
    
    // BOOKID GENERATOR
    public String generateBookID(String author){
                        
        /*
        BookID -> The initials of the author (main) are used - their respected position in the alphabet is used. This number is then multiplied by 3
               -> If the initals, once multiplied by 3, is a double digits (x > 9), the two digits are added
               -> A random number is generated between 00 and 99;
               -> Every odd positioned digits, from right to left, is multiplied by the remainder when subtracted from 10 - The two initials and the random number are put into a string of 6 digits
               -> If the multiplied number is > 9, the digits are added
               -> All the odd positioned digits are added together with the sum of all the even positioned digits
               -> Check number is this number MOD 10
               
               -> Final ID has: LMS [Library Management System] + Initials value * 3 [both initals] + random number + check digit
        */
        
        // Spliting up the main author's names
        List<String> split_author = Arrays.asList(author.split(" "));
        
        // Populating the dictionaries for use
        //populateDicts();
        
        char first_name = split_author.get(0).toLowerCase().charAt(0); // Uses the first name
        char surname = split_author.get(split_author.size() -1).toLowerCase().charAt(0); // Uses the last name (surname)  
        
        // Getting the letter's alphabetical position
        String alpha_num_first = this.dictAlpha.get(first_name);
        String alpha_num_surname = this.dictAlpha.get(surname);

        // Multiplying the position by three
        int num_times_3_first = Integer.parseInt(alpha_num_first)*3;
        int num_times_3_surname = Integer.parseInt(alpha_num_surname)*3;
        
        // If the number is smaller than 10 [x<10], the digit gets a 0 before it
        String xx = addZero(num_times_3_first);
        String yy = addZero(num_times_3_surname);       

        // Getting the random number
        int random_num = (int) (Math.random()*100);
        
        // If the number is smaller than 10 [x<10], the digit gets a 0 before it
        String set_num = addZero(random_num);
        
        // String all the numbers to make the 6-digit String
        String fnl_num = xx + yy + set_num;        

        // Getting the sum of all digits after the algorithm has happened
        int total = algorithmBook(fnl_num);
        
        // Getting check digit
        String check_digit = Integer.toString(total % 10);
        
        // Making BookID
        String fnl_id = "LMS" + fnl_num + check_digit;
        
        return fnl_id;        
    }
    
    private String addZero(int num){
        // Number smaller than 10 gets a zero before it - checks using a dictionary
        if(num < 10)
            return dictNumeric.get(num);
        else
            return Integer.toString(num);
    }// END addZero()
    
    private int algorithmBook(String fnl_num){
        // Spliting string to get each digits on its own
        String[] ind = fnl_num.split("");

        // Digits from right to left
        int num1 = Integer.parseInt(ind[5]);
        int num2 = Integer.parseInt(ind[4]);
        int num3 = Integer.parseInt(ind[3]);
        int num4 = Integer.parseInt(ind[2]);
        int num5 = Integer.parseInt(ind[1]);
        int num6 = Integer.parseInt(ind[0]);

        // Odd positioned digits - times by remainder when subtracted from 10
        int num11 = num1 * (10-num1);
        int num33 = num3 * (10-num3);
        int num55 = num5 * (10-num5);

        // Seeing if the multiplied digits is greater than 9
        int fnl11 = addDigits(num11);
        int fnl33 = addDigits(num33);
        int fnl55 = addDigits(num55);
        
        // Summing all digits and returning
        return fnl11 + fnl33 + fnl55 + num2 + num4 + num6;
    }// END algorithmBook()
    
    private int addDigits(int num){
        // If number is bigger than 9, each digit is summed together
        if(num > 9){
            String temp = Integer.toString(num); // String the integer
            String split_temp[] = temp.split(""); // Split into array for easier summing
            return Integer.parseInt(split_temp[0]) + Integer.parseInt(split_temp[1]); // summing and returning value
        }else{
            return num; // smaller than 10, thus one digit, so returned
        }
    }// END addDigits()
    
    
    // USERID GENERATOR
    public String generateUserID(String firstName, String surname, String date){
        char firstNameChar = firstName.toLowerCase().charAt(0);
        char surnameChar = surname.toLowerCase().charAt(0);
        
        String firstNameAlpha = this.dictAlpha.get(firstNameChar);
        String surnameAlpha = this.dictAlpha.get(surnameChar);
        
        int firstNameMulti = Integer.parseInt(firstNameAlpha)*3;
        int surnameMulti = Integer.parseInt(surnameAlpha)*3;
        
        String firstNameValue = addZero(firstNameMulti);
        String surnameValue = addZero(surnameMulti);
        
        int random = (int) (Math.random()*100);
        String randomValue = addZero(random);
        
        String fnlValue = firstNameValue + surnameValue + date + randomValue;
        
        int result = algorithmUser(fnlValue);
        
        String checkDigit = Integer.toString(result%10);
        
        return String.format("%c%c%s%s%s", firstName.charAt(0), surname.charAt(0), date, randomValue, checkDigit);        
    }// END UserID()
    
    
    private int algorithmUser(String value){
        String digits[] = value.split("");
        
        int num1 = Integer.parseInt(digits[11]);
        int num2 = Integer.parseInt(digits[10]);
        int num3 = Integer.parseInt(digits[9]);
        int num4 = Integer.parseInt(digits[8]);
        int num5 = Integer.parseInt(digits[7]);
        int num6 = Integer.parseInt(digits[6]);
        int num7 = Integer.parseInt(digits[5]);
        int num8 = Integer.parseInt(digits[4]);
        int num9 = Integer.parseInt(digits[3]);
        int num10 = Integer.parseInt(digits[2]);
        int num11 = Integer.parseInt(digits[1]);
        int num12 = Integer.parseInt(digits[0]);
        
        int numm1 = num1 * (10-num1); 
        int numm3 = num3 * (10-num3); 
        int numm5 = num5 * (10-num5); 
        int numm7 = num7 * (10-num7); 
        int numm9 = num9 * (10-num9); 
        int numm11 = num11 * (10-num11); 
        
        int nummm1 = addDigits(numm1);
        int nummm3 = addDigits(numm3);
        int nummm5 = addDigits(numm5);
        int nummm7 = addDigits(numm7);
        int nummm9 = addDigits(numm9);
        int nummm11 = addDigits(numm11);
        
        return nummm1 + nummm3 + nummm5 + nummm7 + nummm9 + nummm11 + num2 + num4 + num6 + num8 + num10 + num12;
        
    }// END algorithmUser()
    
    
    // STAFFID GENERATOR
    public String generateStaffID(String firstName, String surname){
        char firstNameChar = firstName.toUpperCase().charAt(0);
        char surnameChar = surname.toUpperCase().charAt(0);
        
        int random = (int) (Math.random()*100);
        String randomValue = addZero(random);
        
        String staffID = "ST" + Character.toString(firstNameChar) + Character.toString(surnameChar) + randomValue;
        
        return staffID;        
    }// END generateStaffID()
    
    
    // TAKEOUTID GENERATOR
    public String generateTakeOutID(String returnDate){
        int random1 = (int) (Math.random()*100);
        int random2 = (int) (Math.random()*100);
        
        String randomValue1 = addZero(random1);
        String randomValue2 = addZero(random2);
        
        String takeoutID = "BT" + returnDate + randomValue1 + randomValue2; 
        return takeoutID;
    }// END generateTakeOutID()
    
    
    private String algorithmFixRequest(String bookID){
        int random1 = (int) (Math.random()*100);        
        String randomValue1 = addZero(random1);
        return randomValue1 + bookID;
    }// END algorithmFixRequest()
    
    
    // BOOKREQUESTID GENERATOR
    public String generateRequestID(String userid){        
        String requestID = "RQ" + algorithmFixRequest(userid);
        return requestID;        
    }// END generateRequestID()
    
    
    // FIXID GENERATOR
    public String generateFixID(String bookID){        
        String fixID = "FX" + algorithmFixRequest(bookID);
        return fixID;                
    }// END generateFixID()
    
}// END Algorithms class
