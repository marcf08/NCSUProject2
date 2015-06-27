/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

/**
 * The patron DB uses a custom array-based list to keep track of the patrons
 * that can use the library.
 * 
 * @author Marcus
 *
 */
public class PatronDB {
    /**
     * The max size constant keeps track of the max size of the patron database.
     * Per the specifications, the max size is 20.
     */
    private static final int MAX_SIZE = 20;
    /**
     * The size instance field keeps track of the current size of the patron
     * databse.
     */
    private int size;
    /**
     * The list instance field is the array that holds the patrons.
     */
    private Patron[] list;

    /**
     * The Patron DB constructor constructs a new database of the patron used in
     * the library system.
     */
    public PatronDB() {
        list = new Patron[MAX_SIZE];
    }

    /**
     * The verify patron method returns the patron in the list whose id and
     * password match the parameters in the method signature.
     * 
     * @param id
     *            the user's id to verify
     * @param password
     *            the user's password to verify
     * @return the patron that was verified
     */
    public Patron verifyPatron(String id, String password) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        if (password == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            if (list[i].equals(id) && list[i].verifyPassword(password)) {
                return list[i];
            }
        }
        // The loop will return the first (and only) user in the
        // database with the matching login credentials. If such a
        // user cannot be found, either the password was incorrect
        // or the user does not exist in the database. Hence we throw
        // the error if the loop fully executes without returning a patron.
        throw new IllegalArgumentException();

    }

    /**
     * The list accounts method will list the accounts in the database as a
     * string. It is used for testing only.
     * 
     * @return users a string representation of all the users in the database
     */

    public String listAccounts() {
        String users = "";
        for (int i = 0; i < size; i++) {
            users = users + list[i].getId() + "\n";
        }
        return users;
    }

    /**
     * The add new patron method adds a new patron to the database.
     * 
     * @param id
     *            a user's id
     * @param password
     *            a user's password
     * @param maxAllowed
     *            the maximum number of books the user can check out
     */
    public void addNewPatron(String id, String password, int maxAllowed) {
        if (size == MAX_SIZE) {
            throw new IllegalStateException();
        }
        if (id.contains(" ")) {
            // This will get thrown even if the whitespace is longer than one
            // space
            throw new IllegalArgumentException();
        }
        if (password.contains(" ")) {
            // This will get thrown even if the whitespace is longer than one
            // space
            throw new IllegalArgumentException();
        }
        if (id.equals("")) {
            // Null strings are illegal per the specifications
            throw new IllegalArgumentException();
        }
        if (password.equals("")) {
            // Null strings are illegal per the specifications
            throw new IllegalArgumentException();
        }
        //Ensure the user id is unique
        if (!isUnique(id)) {
                throw new IllegalArgumentException();
            }


        // If we make it here, the new patron is unique and valid
        Patron toAdd = new Patron(id, password, maxAllowed);
        //Use the insert alphabetically method to insert the user
        //in the proper spot in the database
        insertAlphabetically(toAdd);

    }
    
    
    /**
     * The cancel account method removes the patron with the given id from the
     * list. It returns any books that the patron has checked out to the inventory.
     * @param id the user id to remove from the database
     */
    public void cancelAccount(String id) {
        for (int i = 0; i < size; i++) {
            if (id.equals(list[i].getId())) {
                list[i].closeAccount();
                list[i] = null; //Sever the reference
                shift(i); //Run the shift algorithm
                //TODO: shift algorithm
            }
            
        }
        //If the for-loop completes its execution, the
        //account must not have been found.
        throw new IllegalArgumentException();
    }
    
    
    /**
     * The shift method is a private method that shifts the accounts back to 
     * the proper position (leaving no holes in the list.)
     * @param removedPos the position of the "hole" in the list (from the
     * cancel account operation)
     */      
    private void shift(int removedPos) {
        for (int i =removedPos - 1; i < size - 1; i++ ) {
            list[i] = list[i+1];
        }
    }

    
    
    /**
     * The insert alphabetically method is a private method that searches the
     * list of users to see where the new patron should be added. It also 
     * adds the patron.
     * 
     * @param toAdd
     *            the newly created patron
     */
    private void insertAlphabetically(Patron toAdd) {
        // Search for where to add the patron in the database
        if (size == 0) { // If the list is empty, the patron gets added to the
                         // zero position
            list[0] = toAdd;
            size++; // Increment the size
        } else {
            // Set up a previous and a temp to check for out of order users
            Patron prev = null;
            Patron temp = null;
            // Start the search at 1 since the list has at least one patron at
            // this point
            for (int i = 1; i < size; i++) {
                prev = list[i - 1];
                if (prev.compareTo(toAdd) > 0) {
                    temp = prev; // Save the previous user, it's out of order
                    prev = null; // Set the previous user to null so it's out of
                                 // the database
                    size--; // Though not necessary, it's proper to reduce the
                            // size since
                    // we're technically removing a user
                    list[i - 1] = toAdd; // Add the new patron to the place
                                         // where previous was located
                    size++; // Increment the size
                    list[i] = temp; // Re-add the previous patron to the proper
                                    // place--behind the new user
                    size++;
                } else {
                    // The else statement will execute if nothing is out of
                    // order.
                    // In that case, we add the patron to the rear.
                    list[size - 1] = toAdd;
                    size++; // And increase the size
                }

            }

        }

    }

    /**
     * The is unique ensures a patron does not exist in the database with the
     * same user id. It loops through the list and returns a user if that
     * user's id matches that of the new user. 
     * @param id the user id to check
     * @return true if the id is unique and false otherwise
     */
    private boolean isUnique(String id) {
        for (int i = 0; i < size; i++) {
            if (id.equals(list[i].getId())) {
                //A user with the same id exists, return false
                return false;
            }
        }
        //If we make it here, the user must be unique
        return true;
    }

}