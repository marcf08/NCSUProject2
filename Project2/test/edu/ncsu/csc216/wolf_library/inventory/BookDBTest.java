package edu.ncsu.csc216.wolf_library.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * The book DB test runs tests on the book DB test to ensure it properly creates the book object. The methods also
 * tests the exceptions.
 * 
 * @author Marcus
 *
 */
public class BookDBTest {
    /**
     * Instance field for book database used for testing
     */
    BookDB testDB;

    /**
     * The set up method instantiates the classes needed for the tests.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testDB = new BookDB("TestBookList");
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.inventory.BookDB#BookDB(java.lang.String)}
     * .
     * 
     * The test book method ensures the constructor creates a file properly. It
     * also checks the exceptions, which should be thrown if the book database
     * tries to read from a corrupt or nonexistent file.
     */
    @Test
    public void testBookDB() {
        BookDB secondTest = null;
        try {
            secondTest = new BookDB("invalid file");
            fail(); // Should not get here
        } catch (IllegalArgumentException e) {
            // This should be thrown
        }
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.inventory.BookDB#traverse()}.
     * 
     * The test traverse method shows the books in proper (alphabetical) order.
     * If the books are not in order, there are problems.
     */
    @Test
    public void testTraverse() {
        //Books should not be in this order
        String notEquals = "Lolita by Vladimir Nabokov\r\n" + 
                "Great Short Works of Leo Tolstoy\r\n" + 
                "The Golden Bough by Sir James George Frazer\r\n" + 
                "All the Pretty Horses by Cormac McCarthy\r\n" + 
                "Why I am Not a Christian by Bertrand Russell\r\n" + 
                "Lolita by Vladimir Nabokov";
        assertNotEquals(notEquals, testDB.traverse());
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.inventory.BookDB#findItemAt(int)}.
     * 
     * The find item at method checks the exceptions at the boundaries. It should
     * throw an exception for less than 0 or for more books than are in the list.
     */
    @Test
    public void testFindItemAt() {
        //Test negative index        
        try {
            testDB.findItemAt(-27);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        //Test index too high
        try {
            testDB.findItemAt(27);
            fail();
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        //Test first book (book should be All the Pretty Horses)
        assertEquals("All the Pretty Horses by Cormac McCarthy", testDB.findItemAt(0).toString());
    }

}
