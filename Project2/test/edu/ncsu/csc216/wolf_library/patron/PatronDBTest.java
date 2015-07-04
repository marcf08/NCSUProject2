/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * The patron DB creates a list of patrons in order to test the methods of the Patron DB class.
 * 
 * 
 * @author Marcus
 *
 */
public class PatronDBTest {
    /**
     * Instance field for patron database test.
     */
    private PatronDB testDB;
    
    /**
     * The set up method instantiates the classes needed for the tests.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        //Instantiate the new test database
        testDB = new PatronDB();
        
        //Add 20 patrons to the list
        for (int i = 0; i <= 19; i++) {
            testDB.addNewPatron("patron" + i, "pw" + i, 1);
        }
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.PatronDB#verifyPatron(java.lang.String, java.lang.String)}.
     * 
     * The test verify patron method tests to make sure the method throws exceptions. It then ensures the 
     * correct patron was found with valid credentials. It then checks the exception for incorrect
     * credentials.
     */
    @Test
    public void testVerifyPatron() {
        //Test null user id
        try {
            testDB.verifyPatron(null, "pw");
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test null password
        try {
            testDB.verifyPatron("jibberish", null);
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test nonexistent patron
        try {
            testDB.verifyPatron("patron000", "pw000");
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test wrong password
        try {
            testDB.verifyPatron("patron0", "pw255");
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        
        //Test valid login
        testDB.verifyPatron("patron0", "pw0");
        
        
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.PatronDB#listAccounts()}.
     * 
     * Since the list accounts method verifies the list of patrons, it should be tested first.
     * It should print an empty string at first (with no patrons added.) Upon confirming the
     * initial result, we will proceed with adding patrons and repriting the list.
     *  
     */
    @Test
    public void testListAccounts() {
        PatronDB test = new PatronDB();
        assertEquals("", test.listAccounts());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.PatronDB#addNewPatron(java.lang.String, java.lang.String, int)}.
     * 
     * The add new patron ensures patrons get added to the database. Since it is critical
     * for adding patrons, it is one of the first methods tested. The test method also runs through
     * testing the exceptions for empty strings, white space, etc.
     */
    @Test
    public void testAddNewPatron() {
        PatronDB otherTest = new PatronDB();
        //Add a patron
        otherTest.addNewPatron("user", "pw", 1);
        
        //Ensure the string comes back correctly
        assertEquals("user\n", otherTest.listAccounts());
        
        
        //Ensure the error throws for 21 patrons
        try {
            testDB.addNewPatron("userTest", "pw", 1);
            fail(); //Should not get here
        }
        catch (IllegalStateException e) {
            //This should be thrown
        }
        
        //Test an already existing patron
        try {
            //This partron already exists from earlier in the test method
            otherTest.addNewPatron("user", "pw", 1);
            fail(); //Should not make it here
         } catch (IllegalArgumentException e) {
             //This should be thrown
         }
        
        //Test a patron with whitespace in id
        try {
            otherTest.addNewPatron(" ", "password", 1);
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test a patron with whitespace in pw
        try {
            otherTest.addNewPatron("tryUser", " ", 1);
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }

        
        //Test a patron with empty string for the user id
        try {
            otherTest.addNewPatron("", "pw", 1);
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
      //Test a patron with empty string for the password
        try {
            otherTest.addNewPatron("user", "", 1);
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.PatronDB#cancelAccount(java.lang.String)}.
     * 
     * The cancel account method ensures a patron's account has been removed. It tests to ensure the 
     * account actually exists first. Secondly, it ensures the patron was removed.
     */
    @Test
    public void testCancelAccount() {
        //Create a patron database
        PatronDB otherTest = new PatronDB();
        
        //Add one patron
        otherTest.addNewPatron("user", "pw", 1);
        
        //Test the exception for a nonexistent patron
        try {
           otherTest.cancelAccount("jibberish");
           fail();
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Cancel the only account
        otherTest.cancelAccount("user");
        
        //The list should be empty
        assertEquals("", otherTest.listAccounts());
    }
}
