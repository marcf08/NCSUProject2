/**
 * 
 */
package edu.ncsu.csc216.wolf_library.lending_system;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 *The library lending system test ensures the library lending system functions appropriately in logging
 *patrons in and out.
 * @author Marcus
 *
 */
public class LibraryLendingSystemTest {
    /**
     * Private instance field for the test system    
     */
    private LibraryLendingSystem library;
    
    /**
     * The set up method creates an instance of the library lending system for testing.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        //Use the test book list from the other methods
        library = new LibraryLendingSystem("TestBookList");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#showInventory()}.
     */
    @Test
    public void testShowInventory() {
        String expected = "All the Pretty Horses by Cormac McCarthy\n" + 
                "The Golden Bough by Sir James George Frazer\n" + 
                "* Great Short Works of Leo Tolstoy\n" + 
                "Lolita by Vladimir Nabokov\n" + 
                "Why I am Not a Christian by Bertrand Russell\n";
        assertEquals(expected, library.showInventory());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#login(java.lang.String, java.lang.String)}.
     * 
     * The test login method ensures patrons and the admin can log in. Specifically, it checks that the
     * get current patron field matches the patron actually logged in.
     */
    @Test
    public void testLogin() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        //Login
        library.login("user", "pw");
        //Should be "user"
        assertEquals("user", library.getCurrentUserId());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#logout()}.
     * 
     * Upon logging out, the current user should be null.
     */
    @Test
    public void testLogout() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        //Login
        library.login("user", "pw");
        library.logout();
        //Should be no user
        assertEquals("", library.getCurrentUserId());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#addNewPatron(java.lang.String, java.lang.String, int)}.
     * 
     * The add new patron method ensures a patron gets added to the system correctly.
     * It also tests whether or no the admin is logged in for throwing exceptions.
     */
    @Test
    public void testAddNewPatron() {
        //Test without admin for exception
        try {
            library.addNewPatron("user", "pw", 1);
            fail(); //Should not get here
        }
        catch (IllegalStateException e) {
            //This should get thrown
        }
        
        //Test with admin
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        //Login
        library.login("user", "pw");
        //Should be "user", verifies the patron was created
        assertEquals("user", library.getCurrentUserId());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#getCurrentUserId()}.
     * 
     * The current user id method is tested for it's exceptions. It's also tested in the event
     * the current user is null. This should return an empty string.
     */
    @Test
    public void testGetCurrentUserId() {
        //Test admin
        library.login("admin", "admin");
        assertEquals("admin", library.getCurrentUserId());

        //Test no one logged in
        library.logout();
        assertEquals("", library.getCurrentUserId());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#cancelAccount(java.lang.String)}.
     * 
     * The cancel account method verifies an account was cancelled by ensuring the cancelled patron cannot
     * log back in. It also checks the exception to make sure the admin was logged in to perform the cancellation.
     */
    @Test
    public void testCancelAccount() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        //Verify the patron can log in
        library.login("user", "pw");
        try {
            library.cancelAccount("jibberish");
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should get thrown
        }
        library.logout();
        
        library.login("admin", "admin");
        library.cancelAccount("user");
        library.logout();
        
        //The patron should not be able to log in
        try {
            library.login("user", "pw");
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //Should get here
        }
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#reserveItem(int)}.
     * 
     * The reserve item method ensures no items can be reserved if a patron is not logged in. This situation should
     * throw an exception.
     */
    @Test
    public void testReserveItem() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        //Test with no patron logged in
        try {
            library.reserveItem(0);
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should get thrown
        }
        
        //Attempt to reserve a book
        library.login("user", "pw");
        library.reserveItem(0);
  
        //Book should appear in checked out queue
        assertEquals("All the Pretty Horses by Cormac McCarthy\n", library.traverseCheckedOut());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#reserveMoveAheadOne(int)}.
     * 
     * The test reserve move ahead tests whether a patron has been logged in. If not, it throws an exception.
     * 
     */
    @Test
    public void testReserveMoveAheadOne() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        //Test illegal state
        try {
            library.reserveMoveAheadOne(0);
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //Should catch this
        }
        
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#removeSelectedFromReserves(int)}.
     * 
     * The remove selected from reserves tests to make sure it only works if a patron is logged in. If not,
     * it throws the necessary exceptions. It then checks to ensure the book actually was removed by traversing
     * the queue.
     */
    @Test
    public void testRemoveSelectedFromReserves() {
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        try {
            library.removeSelectedFromReserves(0);
            fail(); //Should not make it here
        } catch (IllegalStateException e) {
            //This should get thrown
        }
        
        library.login("user", "pw");
        library.reserveItem(2);
        //The book with no copies should now be in the reserve queue
        assertEquals("* Great Short Works of Leo Tolstoy \n", library.traverseReserveQueue());
        
        //Try to remove it
        library.removeSelectedFromReserves(0);
        //Reserve queue should be completely empty now
        assertEquals("", library.traverseReserveQueue());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#traverseReserveQueue()}.
     * 
     * The reserve queue should be empty when logging in as a patron who has not reserved any books.
     * The test method will ensure that occurs, and it will also ensure the reserve queue has a book if one
     * has been reserved. Lastly, it tests the exception for a patron being logged in.
     * 
     */
    @Test
    public void testTraverseReserveQueue() {
        //Test with valid reserve queue
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        library.login("user", "pw");
        //Queue should be empty
        assertEquals("",library.traverseReserveQueue());
               
        library.logout();
        
        //Test for illegal state with no patron logged in
        try {
            library.traverseReserveQueue();
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should be caught
        }
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#traverseCheckedOut()}
     * 
     * The traverse tests an empty queue for no books checked out and also checks the exception.
     * A patron must be logged in before this can be displayed.
     * 
     */
    @Test
    public void testTraverseCheckedOut() {
        //Test with valid queue
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        library.login("user", "pw");
        //Queue should be empty
        assertEquals("",library.traverseCheckedOut());
               
        library.logout();
        
        //Test for illegal state with no patron logged in
        try {
            library.traverseCheckedOut();
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should be caught
        }
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#returnItem(int)}.
     * 
     * The test return item ensures an item is removed for the checked out queue when it's time to be returned.
     * It also tests the illegal state exception that should occur without a patron being logged in.
     */
    @Test
    public void testReturnItem() {
        //Test for illegal state with no patron logged in
        try {
            library.traverseCheckedOut();
            fail(); //Should not get here
        } catch (IllegalStateException e) {
            //This should be caught
        }
        
        //Try to reserve a book as user named "user"        
        library.login("admin", "admin");
        //Create patron
        library.addNewPatron("user", "pw", 1);
        library.logout();
        
        library.login("user", "pw");
        library.reserveItem(0);
        System.out.println(library.traverseCheckedOut());
        
        //The queue should have the book in it
        assertEquals("All the Pretty Horses by Cormac McCarthy\n", library.traverseCheckedOut());
        
        library.returnItem(0);
        
        //Queue should be completely empty
        assertEquals("", library.traverseCheckedOut());
        
        
        
    }

}
