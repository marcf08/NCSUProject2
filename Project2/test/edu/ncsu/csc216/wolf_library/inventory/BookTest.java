/**
 * 
 */
package edu.ncsu.csc216.wolf_library.inventory;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 *The 
 * @author Marcus
 *
 */
public class BookTest {
    /**
     * Instance field for test book
     */
     private Book warAndPeace;
     /**
      * Instance field for test book no copies
      */
    private Book bloodMeridian;
    
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        warAndPeace = new Book("1 War and Peace");
        bloodMeridian = new Book("0 Blood Meridian");
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#Book(java.lang.String)}.
     * 
     * The test method for the constructor ensures exceptions are thrown for invalid books and books without
     * the proper format.
     */
    @Test
    public void testBook() {
        //Test with null String
        Book test = null; 
        try {
            test = new Book("");
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should get thrown
        }
        
        //Test with no number of copies
        Book testTwo = null;
        try {
            testTwo = new Book("Generic Book Title without No of Copies");
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            //This should get thrown
        }
        

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#getInfo()}.
     * 
     * The test get info method ensures the book returns the correct information.
     */
    @Test
    public void testGetInfo() {
        assertEquals("War and Peace", warAndPeace.getInfo());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#toString()}.
     * 
     * The method should place an asterisk in front of the book if no copies exist
     */
    @Test
    public void testToString() {
        //The first test book should return as it is. since it's available
        assertEquals("War and Peace", warAndPeace.toString());
        
        //The second book should be prefixed with an asterisk
        assertEquals("* Blood Meridian", bloodMeridian.toString());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#isAvailable()}.
     * 
     * The is available method tests whether or not a book is available. It should return a simple
     * boolean (true) for the first test book since it's available. It should return a false value for a
     * book that's unavailable.
     */
    @Test
    public void testIsAvailable() {
        //This test should return true since it's available
        assertTrue(warAndPeace.isAvailable());
        
        //This test should return false since it's not available
        assertFalse(bloodMeridian.isAvailable());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#backToInventory()}.
     * 
     * The back to inventory method should increment the number of copies of book. The second
     * test book (Blood Meridian) should be available after running this method. 
     */
    @Test
    public void testBackToInventory() {
        //Return a copy of an unavailable book
        bloodMeridian.backToInventory();        
        //Should be available now
        assertTrue(bloodMeridian.isAvailable());
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#removeOneCopyFromInventory()}.
     * 
     * The test remove a copy from inventory method should make a book with only 1 copy unavailable after
     * running it. The test creates a book with one copy, and removes it to ensure its unavailable. Furthermore
     * the test runs the exceptions to ensure they are thrown.
     * 
     * 
     */
    @Test
    public void testRemoveOneCopyFromInventory() {
        //Create the new book
        Book oneCopy = new Book("1 Moby Dick");
        
        //Remove a copy
        oneCopy.removeOneCopyFromInventory();
        
        //Run the assert
        assertFalse(oneCopy.isAvailable());
        
        //Trying to remove another copy should throw an exception
        try {
            oneCopy.removeOneCopyFromInventory();
            fail(); //This should not get called            
        } catch (IllegalStateException e) {
            //This should get thrown
        }
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.inventory.Book#compareTo(edu.ncsu.csc216.wolf_library.inventory.Book)}.
     * 
     * The method tests books to ensure they compare properly. Two books with the same name should
     * return 0. The method also tests a null pointer if the book of comparison is null.
     * 
     * In order to ensure the private correctBooks method is working properly, we'll run several tests on books that start with
     * "a" or "an" or "the" as well.
     */
    @Test
    public void testCompareTo() {
        //Test null pointer
        Book other = null;
        try {
            warAndPeace.compareTo(other);
            fail(); //Should not get here
        } catch (NullPointerException e) {
            //This should get thrown
        }
        
        Book alpha = new Book("1 Alpha");
        Book beta = new Book("1 Beta");
        Book alphaTwo = new Book("1 Alpha");
        
        //Should be equal since books have the same title
        assertEquals(0, alpha.compareTo(alphaTwo));
        
        //Should NOT be 0, books have different titles
        assertNotEquals(0,alpha.compareTo(beta));
        
        //Also should NOT be 0 when tested the other way around
        assertNotEquals(0, beta.compareTo(alpha));
        
        //To make sure the private correct books method is getting thoroughly tested
        Book startsWithThe = new Book("1 The Odyssey");
        Book startsWithAn = new Book("1 A Perfect Day for Bananafish");
        
        //These books sohuld not be equal
        assertNotEquals(0, startsWithAn.compareTo(startsWithThe));
        
        
        
        
        
    }

}
