/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

/**
 * @author Marcus The abstract class user creates the base functionality for
 *         creating users to interact with the library system.
 */
public abstract class User {
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
     */
    public User(String id, String password) {
        if (id.trim() == null) {
            throw new IllegalArgumentException();
        }
        if (password.trim() == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.password = password.hashCode(); // TODO: does this work?
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
     * @return the user's id
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * The compare to method looks at two users to see if they are in order.
     * It performs a lexicographical comparison.
     * @param other an other user to compare
     * @return an integer value obtained via lexicographical comparison
     */
    public int compareTo(User other) {
        return id.compareTo(other.getId());
    }
    
   
    /**
     * The hash code method is used to secure the password.
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + password;
        return result;
    }

}
