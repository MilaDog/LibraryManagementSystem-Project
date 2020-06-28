package librarymanagementsystemproject;

// Daniel-Ryan Sergeant - 28 Jun 2020

public class BooksFix {

    private String fixID;
    private String bookID;
    private String staffID;
    private String reason;
    private boolean fixed;

    public BooksFix(String fixID, String bookID, String staffID, String reason, boolean fixed) {
        this.fixID = fixID;
        this.bookID = bookID;
        this.staffID = staffID;
        this.reason = reason;
        this.fixed = fixed;
    }

    public String getFixID() {
        return fixID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getReason() {
        return reason;
    }

    public boolean isFixed() {
        return fixed;
    }

    @Override
    public String toString() {
        return "BooksFix{" + "fixID=" + fixID + ", bookID=" + bookID + ", staffID=" + staffID + ", reason=" + reason + ", fixed=" + fixed + '}';
    }
    
        
    
}// END BooksFix class
