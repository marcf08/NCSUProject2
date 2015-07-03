/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * The library account system test tests the methods of the library account system. It checks
 * the exceptions. 
 * @author Marcus
 *
 */
public class LibraryAccountSystemTest {
    /**
     * Instance field for patron database used for testing.
     */
    private PatronDB testPatrons;
    
    
    /**
     * The library account
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
       testPatrons = new PatronDB();
       //Add some test patrons
       for (int i = 0; i < 19; i++) {
           testPatrons.addNewPatron("patron" + i, "password" + i, 1);
       }
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#LibraryAccountSystem()}.
     */
    @Test
    public void testLibraryAccountSystem() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#login(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#logout()}.
     */
    @Test
    public void testLogout() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#getCurrentPatron()}.
     */
    @Test
    public void testGetCurrentPatron() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#isAdminLoggedIn()}.
     */
    @Test
    public void testIsAdminLoggedIn() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#isPatronLoggedIn()}.
     */
    @Test
    public void testIsPatronLoggedIn() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#addNewPatron(java.lang.String, java.lang.String, int)}.
     */
    @Test
    public void testAddNewPatron() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#cancelAccount(java.lang.String)}.
     */
    @Test
    public void testCancelAccount() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.LibraryAccountSystem#listAcounts()}.
     */
    @Test
    public void testListAcounts() {
        fail("Not yet implemented");
    }

}
