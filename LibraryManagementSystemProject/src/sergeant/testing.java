package sergeant;

// Daniel-Ryan Sergeant - 07 Jul 2020

import java.util.ArrayList;
import librarymanagementsystemproject.BooksRequest;
import librarymanagementsystemproject.Library;


public class testing {
    public static void main(String[] args) {
        
        Library lib = new Library();
        
        ArrayList<BooksRequest> arr = lib.fetchRequestedBooks();
        for(BooksRequest s: arr){
            System.out.println(s.getTitle());
        }
        
    }
}// END testing class
