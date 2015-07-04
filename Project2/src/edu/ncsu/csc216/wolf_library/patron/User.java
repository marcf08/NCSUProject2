/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

import edu.ncsu.csc216.wolf_library.util.Constants;

/**
 *  The abstract class user creates the base functionality for
 *   creating users to interact with the library system.
 * @author Marcus
 */
public abstract class User  implements Comparable<User> {
    /**
     * The id field is for the user's ID.
     */
    private String id;
    /**
     * The password field is for the user's password.
     */
    private int password;

    /**
     * The constructor populates the fields of the parents. The children classes
     * will call this constructor in order to implement their functionality.
     * @param id the user's id
     * @param password the user's password
     */
    public User(String id, String password) {
        if (id == null) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_NULL);
        }
        if (password == null) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_NULL);
        }
        if (containsWhitespace(id.trim())) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
        }
        if (containsWhitespace(password.trim())) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
        }
        if (id.trim().length() == 0) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
        }
        if (password.trim().length() == 0) {
            throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
        }
        this.id = id;
        this.password = password.hashCode();
    }

    /**
     * The contains whitespace method inspects the parameters for whitespace,
     * which is not allowed per the project specifications.
     * 
     * @param idOrPassword
     *            an id or password to check for whitepsace
     * @return true if the string contains whitespace and false otherwise
     */
    private boolean containsWhitespace(String idOrPassword) {
        if (idOrPassword.contains(" ")) {
            return true;
        }
        return false;
    }

    /**
     * The verify password method checks the hash code to see if the parameter
     * matches the password (stored as a hash code of the password String.)
     * 
     * @param password
     *            a password to verify
     * @return true if the parameter matches the password and false otherwise
     */
    public boolean verifyPassword(String password) {
        return (this.password == password.hashCode());
    }

    /**
     * The get id method is a simple getting for the user's ID.
     * 
     * @return the user's id
     */
    public String getId() {
        return this.id;
    }

    /**
     * The compare to method looks at two users to see if they are in order. It
     * performs a lexicographical comparison.
     * 
     * @param other
     *            an other user to compare
     * @return an integer value obtained via lexicographical comparison
     */
    public int compareTo(User other) {
        if (other == null) {
            throw new NullPointerException(Constants.EXP_CANNOT_COMPARE);
        }
        return id.compareTo(other.getId());
    }

    /**
     * The hash code method is used to secure the password.
     * @return result the result of the hashing operation
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + password;
        return result;
    }

}
