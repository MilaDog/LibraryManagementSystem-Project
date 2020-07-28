package librarymanagementsystemproject;

/**
 *
 * @author Daniel Ryan Sergeant
 */

public class BooksFix {

    // Initializing necessary fields
    private String fixID;
    private String bookID;
    private String staffID;
    private String reason;
    private boolean fixed;

    /**
     *
     * @param fixID Takes in the FixID of the book requested to be fixed
     * @param bookID Takes in the BookID of the book requested to be fixed
     * @param staffID Takes in the StaffID of the staff member that requested the book fix
     * @param reason Takes in the Reason of the requested book fix
     * @param fixed Takes in the Boolean of whether or not the book was fixed
     */
    public BooksFix(String fixID, String bookID, String staffID, String reason, boolean fixed) {
        this.fixID = fixID;
        this.bookID = bookID;
        this.staffID = staffID;
        this.reason = reason;
        this.fixed = fixed;
    }

    /**
     *
     * @return FixID of requested book fix
     */
    public String getFixID() {
        return fixID;
    }

    /**
     *
     * @return BookID of requested book fix
     */
    public String getBookID() {
        return bookID;
    }

    /**
     *
     * @return StaffID of the staff that requested the book fix
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     *
     * @return Reason as to the book fix request
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @return Boolean if the book is fixed or not
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     *
     * @return String of Object
     */
    @Override
    public String toString() {
        return "BooksFix{" + "fixID=" + fixID + ", bookID=" + bookID + ", staffID=" + staffID + ", reason=" + reason + ", fixed=" + fixed + '}';
    }
    
        
    
}// END BooksFix class
