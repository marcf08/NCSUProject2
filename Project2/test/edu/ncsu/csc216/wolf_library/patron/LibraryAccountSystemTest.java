package edu.ncsu.csc216.wolf_library.patron;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



/**
 * The library account system test tests the login features of the library
 * account system. It also ensures the system handles the patrons correctly.
 * @author Marcus
 *
 */
public class LibraryAccountSystemTest {
    /**
     * Instance field for library account system
     */
    private LibraryAccountSystem library;
    /**
     * Instance field for patron database
     */
    private PatronDB patronTestList;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        library = new LibraryAccountSystem();
        patronTestList = new PatronDB();
        library.login("admin", "admin");
        patronTestList.addNewPatron("user0", "p0", 1);
        patronTestList.addNewPatron("user1", "p1", 1);
        library.logout();
    }

  
    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#login(java.lang.String, java.lang.String)}.
     * 
     * The test login method checks the exceptions to make sure the system throws an exception if
     * a user logs in while someone else is in. It also ensures the system denies access in the case of
     * incorrect passwords.
     */
    @Test
    public void testLogin() {
        //Try to log in while admin is logged in
        try {
               library.login("admin", "admin");
               library.login("user0", "p0");
               fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should be thrown
        }
        
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.addNewPatron("user1", "pw1", 1);
        libraryTestTwo.logout();
        
        //Try to log in while another patron is logged in
        try {
               libraryTestTwo.login("user0", "pw0");
               libraryTestTwo.login("user1", "pw1");
               fail();
        } catch (IllegalStateException e) {
            //This should be thrown
        }
        
        //With all patrons logged out, the current patron should be null
        libraryTestTwo.logout();
        assertEquals(null, libraryTestTwo.getCurrentPatron());
        
        //Test a valid patron
        libraryTestTwo.login("user0", "pw0");
        //User0 should be logged in
        assertEquals("user0", libraryTestTwo.getCurrentPatron().getId());
        
        libraryTestTwo.logout();
        
        //Test the exception for invalid password
        try {
            libraryTestTwo.login("user0", "jibbeerish");
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should get caught
        }
       
        
        
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#logout()}.
     * 
     * The test log out method ensures that when a patron logs out, the current patron is null.
     * Without this change, the system could be compromised.
     */
    @Test
    public void testLogout() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.addNewPatron("user1", "pw1", 1);
        libraryTestTwo.logout();
        assertEquals(null, libraryTestTwo.getCurrentPatron());
        
        //Test admin
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.logout();
        assertFalse(libraryTestTwo.isAdminLoggedIn());
        
        
        //Test patron
        libraryTestTwo.login("user0", "pw0");
        assertTrue(libraryTestTwo.isPatronLoggedIn());
        libraryTestTwo.logout();
        assertFalse(libraryTestTwo.isPatronLoggedIn());
   
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#getCurrentPatron()}.
     * 
     * The get current patron is one of the first methods tested. It is tested first because
     * the system uses the current patron field in order to show the appropriate menu type.
     */
    @Test
    public void testGetCurrentPatron() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.addNewPatron("user1", "pw1", 1);
        libraryTestTwo.logout();
        
        libraryTestTwo.login("user0", "pw0");
        assertEquals("user0", libraryTestTwo.getCurrentPatron().getId());

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#isAdminLoggedIn()}.
     * 
     * The test is admin log in method verifies the patron is logged in prior to adding / removing users.
     * It's a simple boolean test.
     */
    @Test
    public void testIsAdminLoggedIn() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        assertFalse(libraryTestTwo.isAdminLoggedIn());
        libraryTestTwo.login("admin", "admin");
        assertTrue(libraryTestTwo.isAdminLoggedIn());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#isPatronLoggedIn()}.
     * 
     * The test is patron logged in checks to see if a patron is set to the current patron. It's a simple
     * boolean test.
     */
    @Test
    public void testIsPatronLoggedIn() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.logout();
        
        //Should have no patron since none is logged in
        assertFalse(libraryTestTwo.isPatronLoggedIn());
        
        //Should get true for a patron logged in.
        libraryTestTwo.login("user0", "pw0");
        assertTrue(libraryTestTwo.isPatronLoggedIn());
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#addNewPatron(java.lang.String, java.lang.String, int)}.
     * 
     * The test add new patron method checks to see if a patron can log in after the admin account
     * has added him. This method also checks to see if the exception is thrown if the admin is not logged in.
     */
    @Test
    public void testAddNewPatron() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.logout();
        
        //Try to log in as the patron
        libraryTestTwo.login("user0", "pw0");
        
        //If the log in was successful, the patron must have been added
        assertEquals("user0", libraryTestTwo.getCurrentPatron().getId());
        
        LibraryAccountSystem libraryTestThree = new LibraryAccountSystem();
        try {
            libraryTestThree.addNewPatron("testUser", "testPassword", 1);
            fail(); //Should not make it here
        } catch (IllegalStateException e) {
            //This should get thrown
        }
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#cancelAccount(java.lang.String)}.
     * 
     * The test cancel account method adds and account, cancels it, and then attempts to log in with
     * the cancelled account. If the log in fails, the patron was successfully removed. The test also
     * tests the exception--the admin must be logged in in order to cancel an account.
     */
    @Test
    public void testCancelAccount() {
        //Do the adding to the account
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.logout();
        
        //Try to log in as the new user        
        libraryTestTwo.login("user0", "pw0");
        
        //The patron should have logged in successfully
        assertEquals("user0", libraryTestTwo.getCurrentPatron().getId());
        
        libraryTestTwo.logout();
        try {
            libraryTestTwo.cancelAccount("user0");
            fail();
        } catch (IllegalStateException e) {
            //This should be caught
        }
        
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.cancelAccount("user0");
        libraryTestTwo.logout();
        
        //Try to log in as the deleted patron
        try {
            libraryTestTwo.login("user0", "pw0");
            fail(); //This should not be executed
        } catch (IllegalArgumentException e) {
            //This should get thrown
        }

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#listAcounts()}.
     * 
     * The test list accounts method should print an empty string if no users are registered.
     * It should also print a user if one has been added.
     */
    @Test
    public void testListAcounts() {
        LibraryAccountSystem libraryTestTwo = new LibraryAccountSystem();
        
        System.out.println(libraryTestTwo.listAcounts());
        
        assertEquals("", libraryTestTwo.listAcounts());
        
        libraryTestTwo.login("admin", "admin");
        libraryTestTwo.addNewPatron("user0", "pw0", 1);
        libraryTestTwo.logout();
        
        //Should get one user printed out        
        assertEquals("user0\n", libraryTestTwo.listAcounts());
        
    }

}
