package edu.ncsu.csc216.wolf_library.patron;

import edu.ncsu.csc216.wolf_library.util.Constants;

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
        size = 0;
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
        int indexOfMatch = findMatchingAccount(id);
        if (indexOfMatch == -1) {
            throw new IllegalArgumentException(Constants.EXP_INCORRECT);
        }
        for (int i = 0; i < size; i++) {
            if (list[i].getId().equals(id) && list[i].verifyPassword(password)) {
                return list[i];
            }
        }
        // The loop will return the first (and only) user in the
        // database with the matching login credentials. If such a
        // user cannot be found, either the password was incorrect
        // or the user does not exist in the database. Hence we throw
        // the error if the loop fully executes without returning a patron.
        throw new IllegalArgumentException(Constants.EXP_INCORRECT);

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
            if (list[i] != null) {
                users = users + list[i].getId() + "\n";
            }
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
            throw new IllegalStateException(Constants.EXP_PATRON_DB_FULL);
        }
        if (id.contains(" ")) {
            // This will get thrown even if the whitespace is longer than one
            // space
            throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
        }
        if (password.contains(" ")) {
            // This will get thrown even if the whitespace is longer than one
            // space
            throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
        }
        if (id.equals("")) {
            // Null strings are illegal per the specifications
            throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
        }
        if (password.equals("")) {
            // Null strings are illegal per the specifications
            throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
        }
        if (!isNewPatron(id)) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_DB_FULL);
        }
        // If we make it here, the new patron is unique and valid
        // Add it alphabetically
        insert(new Patron(id, password, maxAllowed));


    }

    /**
     * The cancel account method removes the patron with the given id from the
     * list. It returns any books that the patron has checked out to the
     * inventory.
     *
     * @param id
     *            the user id to remove from the database
     */
    public void cancelAccount(String id) {
        int num = findMatchingAccount(id);
        if (num == -1) {
            throw new IllegalArgumentException(Constants.EXP_INCORRECT);
        } else {
            Patron toCancel = list[num];
            toCancel.closeAccount();
            list[num] = null; // Remove the patron from the list
            shift(num); // Run the shift algorithm to maintain list
                        // integrity (will only execute if necessary)
        }
    }

    /**
     * The find matching account method searches the database for a matching
     * account.
     * 
     * @param id
     *            the id to search for
     * @return i the index of the account or -1 if the user was not found in the
     *         database
     */
    private int findMatchingAccount(String id) {
        if (id == null) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if ((list[i] != null)) {
                if (id.equals(list[i].getId())) {
                    return i;
                }
            }
        }
        return -1; // Indicate the account was not found
    }

    /**
     * The shift method is a private method that shifts the accounts back to the
     * proper position (leaving no holes in the list.)
     * 
     * @param removedPos
     *            the position of the "hole" in the list (from the cancel
     *            account operation)
     */
    private void shift(int removedPos) {
        if (size == 0) {
            return; // Do nothing
        } else if (size == 1) {
            return; // Do nothing, no reordering needs to occur
        } else {
            for (int i = removedPos; i < size; i++) {
                list[i] = list[i + 1];
            }
        }
    }

    /**
     * The insert alphabetically method is a private method that searches the
     * list of users to see where the new patron should be added. It also adds
     * the patron.
     * 
     * @param toAdd
     *            the newly created patron
     */
    private void insert(Patron toAdd) {
        // Search for where to add the patron in the database
        if (size == 0) { // If the list is empty, the patron gets added to the
                         // zero position
            list[0] = toAdd;
            size++; // Increment the size
        } else if (size == 1) {
            Patron otherTemp = null;
            if (list[0].compareTo(toAdd) > 0) {
                otherTemp = list[0];
                list[0] = null;
                list[0] = toAdd;
                list[1] = otherTemp;
                size++;
            } else {
                list[1] = toAdd;
                size++;
            }
        } else {
            int psn = size - 1;
            while (psn >= 0 && list[psn].compareTo(toAdd) > 0) {
                list[psn + 1] = list[psn];
                psn--;
            }
            list[psn + 1] = toAdd;
            size++;
        }
    }


    /**
     * The is new patron method checks that a patron does not exist in the
     * database with the same user id. It loops through the list and returns a
     * user if that user's id matches that of the new user.
     * 
     * @param id
     *            the user id to check
     * @return true if the id is unique and false otherwise
     */
    private boolean isNewPatron(String id) {
        for (int i = 0; i < size; i++) {
            if (id.equals(list[i].getId())) {
                // A user with the same id exists, return false
                return false;
            }
        }
        // If we make it here, the user must be unique
        return true;
    }

}
