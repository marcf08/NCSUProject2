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
        String expected = "All the Pretty Horses by Cormac McCarthy \r\n" + 
                "The Golden Bough by Sir James George Frazer \r\n" + 
                "* Great Short Works of Leo Tolstoy \r\n" + 
                "All the Pretty Horses by Cormac McCarthy \r\n" + 
                "Lolita by Vladimir Nabokov \r\n" + 
                "The Golden Bough by Sir James George Frazer \r\n" + 
                "Why I am Not a Christian by Bertrand Russell ";
        System.out.println(library.showInventory());
        assertEquals(expected, library.showInventory());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#login(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogin() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#logout()}.
     */
    @Test
    public void testLogout() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#addNewPatron(java.lang.String, java.lang.String, int)}.
     */
    @Test
    public void testAddNewPatron() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#getCurrentUserId()}.
     */
    @Test
    public void testGetCurrentUserId() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#cancelAccount(java.lang.String)}.
     */
    @Test
    public void testCancelAccount() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#reserveItem(int)}.
     */
    @Test
    public void testReserveItem() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#reserveMoveAheadOne(int)}.
     */
    @Test
    public void testReserveMoveAheadOne() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#removeSelectedFromReserves(int)}.
     */
    @Test
    public void testRemoveSelectedFromReserves() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#traverseReserveQueue()}.
     */
    @Test
    public void testTraverseReserveQueue() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#traverseCheckedOut()}.
     */
    @Test
    public void testTraverseCheckedOut() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.lending_system.LibraryLendingSystem#returnItem(int)}.
     */
    @Test
    public void testReturnItem() {
        fail("Not yet implemented");
    }

}
