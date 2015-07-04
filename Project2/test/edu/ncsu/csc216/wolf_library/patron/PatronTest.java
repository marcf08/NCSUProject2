/**
 * 
 */
package edu.ncsu.csc216.wolf_library.patron;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.wolf_library.inventory.Book;
import edu.ncsu.csc216.wolf_library.util.Constants;

/**
 * @author Marcus
 *
 */
public class PatronTest {
    /**
     * Instance field for a bad password
     */
    private String badPassword = "has    Whitespace";
    /**
     * Instance field for good password
     */
    private String goodPassword = "hasNoWhiteSpace";
    /**
     * Instance field for bad user ID
     */
    private String badUserId = "user   WithWhiteSpace";
    /**
     * Instance field for good user ID
     */
    private String goodUserId = "user";
    /**
     * Instance field for bad checked out number
     */
    private int badCheckedOut = -4;
    /**
     * Instance field for good checked out number
     */
    private int goodCheckedOut = 3;
    /**
     * Instance field for the dummy patron used for testing
     */
    private Patron dummyPatron;
    /**
     * Instance field for test book zero
     */
    private Book bookZero;
    /**
     * Instance field for test book one
     */
    private Book bookOne;
    /**
     * Instance field for test book two
     */
    private Book bookTwo;
    /**
     * Instance field for test book three
     */
    private Book bookThree;
    /**
     * Instance field for bad index (negative)
     */
    private int badIndex = -8;
    /**
     * Instance field for bad index (too high)
     */
    private int badIndexTooHigh = 27;
    /**
     * Instance field for okay index
     */
    private int justRight = 1;

    /**
     * The set up method creates several books and patrons for testing.
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        bookZero = new Book("0 BookZero");
        bookOne = new Book("1 BookOne");
        bookTwo = new Book("1 BookTwo");
        bookThree = new Book("0 BookThree");
        dummyPatron = new Patron("user", "password", 2);
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#Patron(java.lang.String, java.lang.String, int)}
     * . The test method tests patron constructor. It primarily ensures the
     * class throws the proper exceptions.
     */
    @Test
    public void testPatron() {
        // Test bad ID
        Patron test = null;
        try {
            test = new Patron(goodUserId, badPassword, goodCheckedOut);
        } catch (IllegalArgumentException e) {
            if (test != null) {
                fail(); // Patron should not be constructed
            }
        }

        // Test bad password
        Patron testTwo = null;
        try {
            testTwo = new Patron(badUserId, goodPassword, goodCheckedOut);
        } catch (IllegalArgumentException e) {
            if (testTwo != null) {
                fail(); // Patron should not be constructed
            }
        }

        // Test bad checked out number
        Patron testThree = null;
        try {
            testThree = new Patron(goodUserId, goodPassword, badCheckedOut);
        } catch (IllegalArgumentException e) {
            if (testThree != null) {
                fail(); // Patron should not be constructed
            }
        }

        // Test with admin username
        Patron testFour = null;
        try {
            testFour = new Patron(Constants.ADMIN, goodPassword, goodCheckedOut);
            
        } catch (IllegalArgumentException e) {
            if (testFour != null) {
                fail(); // Patron should not be constructed
            }
        }
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#traverseReserveQueue()}
     * . The test reserve queue tests whether we can traverse the reserve queue.
     * The first test checks to ensure the method works with an empty String.
     * The second ensures it works a book added.
     */
    @Test
    public void testTraverseReserveQueue() {
        // Test prior to adding books when the method should return empty string
        assertEquals("", dummyPatron.traverseReserveQueue());

        // Test after adding a book
        dummyPatron.reserve(bookZero);
        // The book should be preceded with an asterisk and a new line escape
        // sequence should follow
        assertEquals("* BookZero \n", dummyPatron.traverseReserveQueue());

    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#traverseCheckedOut()}.
     * 
     * The test traverse checked out method tests whether we can traverse the
     * checked out queue. The first test checks to ensure the method works with
     * an empty queue. The second checks if it works after having added a book.
     */
    @Test
    public void testTraverseCheckedOut() {
        // Test prior to adding books when the method should return an empty
        // string
        assertEquals("", dummyPatron.traverseCheckedOut());
        
        // Test after adding a book
        dummyPatron.reserve(bookOne);
        assertEquals("BookOne\n", dummyPatron.traverseCheckedOut());
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#moveAheadOneInReserves(int)}
     * .
     * 
     * The test move ahead one method ensures the reserve queue will swap pairs
     * of books to move one ahead. It also ensures the boundaries throw the
     * appropriate exceptions.
     */
    @Test
    public void testMoveAheadOneInReserves() {
        // Test with negative index to throw an exception
        try {
            dummyPatron.moveAheadOneInReserves(badIndex);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            // This should be thrown
        }

        // Test with high index to throw an exception
        try {
            dummyPatron.moveAheadOneInReserves(badIndexTooHigh);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            // This should be thrown
        }

        // Test with two books to ensure they swap positions
        // Add two books
        dummyPatron.reserve(bookZero);
        dummyPatron.reserve(bookThree);

        dummyPatron.moveAheadOneInReserves(1);

        // In the reserve queue, bookThree should be in bookZero's spot
        assertEquals("* BookThree \n* BookZero \n",
                dummyPatron.traverseReserveQueue());

        // Test with position of 0 to ensure nothing happens
        dummyPatron.moveAheadOneInReserves(0);
        assertEquals("* BookThree \n* BookZero \n",
                dummyPatron.traverseReserveQueue());

    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#unReserve(int)}.
     * 
     * The test unreserve method ensures the book at the appropriate position
     * gets removed. It also ensures the appropriate exceptions are thrown from
     * using bad positions.
     */
    @Test
    public void testUnReserve() {
        // Test with negative index to throw the exception
        try {
            dummyPatron.unReserve(badIndex);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            // This should be thrown
        }

        // Test with high index to throw the exception
        try {
            dummyPatron.unReserve(badIndexTooHigh);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            // This should be thrown
        }

        // Test with two books to ensure one gets removed
        // Add two books
        dummyPatron.reserve(bookZero);
        dummyPatron.reserve(bookThree);

        dummyPatron.unReserve(0);

        // Book three should be the only book available
        assertEquals("* BookThree \n", dummyPatron.traverseReserveQueue());

    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#reserve(edu.ncsu.csc216.wolf_library.inventory.Book)}
     * .
     * 
     * The test reserve method ensures a book is added to the reserve queue
     * appropriately. It also checks the exception for a null book.
     */
    @Test
    public void testReserve() {
        // Test for exception with a null book
        Book testNull = null;
        try {
            dummyPatron.reserve(testNull);
            fail(); //Should not make it here
        } catch (IllegalArgumentException e) {
            // This should be thrown
        }

        // Test with a valid book
        dummyPatron.reserve(bookThree);
        assertEquals("* BookThree \n", dummyPatron.traverseReserveQueue());

    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#closeAccount()}.
     * 
     * The close account method ensures all the books have been returned. Other
     * methods remove it from the patron database. The important thing is to ensure
     * the books do not get lost with the account.
     */
    @Test
    public void testCloseAccount() {
        // Add books to the account
        dummyPatron.reserve(bookZero);
        dummyPatron.reserve(bookOne);
        dummyPatron.reserve(bookTwo);
        dummyPatron.reserve(bookThree);

        //Try to close it
        dummyPatron.closeAccount();
        
        //Ensure both queues are empty
        assertEquals("", dummyPatron.traverseCheckedOut());
        assertEquals("", dummyPatron.traverseReserveQueue());
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.Patron#returnBook(int)}.
     * 
     * The return book method ensures the book disappears from the patron's
     * checked out queue once returned. It also tests the exceptions using bad indexes
     * to ensure the exceptions get caught.
     */
    @Test
    public void testReturnBook() {
        //Test with bad index (negative)
        try {
            dummyPatron.returnBook(badIndex);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            //This should be caught
        }
        
        //Test with bad index (too high)
        try {
            dummyPatron.returnBook(badIndexTooHigh);
            fail(); //Should not make it here
        } catch (IndexOutOfBoundsException e) {
            //This should be caught
        }
        
        //Test with a reserved book
        dummyPatron.reserve(bookOne);
        //Try to return the book
        dummyPatron.returnBook(0);

        //Checked out queue should be empty
        assertEquals("", dummyPatron.traverseReserveQueue());
        

    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.User#hashCode()}.
     * 
     * The method tests the hash code. The hash code should be the same for
     * different patrons with identical data members. 
     */
    @Test
    public void testHashCode() {
        Patron one = new Patron("id", "pw", 1);
        Patron two = new Patron("id", "pw", 1);
        //Hashcodes should be equal
        assertTrue(one.hashCode() == two.hashCode());
        
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.User#User(java.lang.String, java.lang.String)}
     * .
     * The test user method tests the creation of the user parent class. Since it throws several exceptions,
     * it's important to cover as many of them as possible.
     * 
     */
    @Test
    public void testUser() {
      //Test with bad user id
        try {
            Patron test = new Patron(badUserId, goodPassword, 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test with bad password
        try {
            Patron test = new Patron(goodUserId, badPassword, 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test with null id
        try {
            Patron test = new Patron(null, goodPassword, 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
        //Test with null password
        try {
            Patron test = new Patron(goodUserId, null, 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        } 
        
        //Test id with empty string
        try {
            Patron test = new Patron("", goodPassword, 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
        
      //Test password with empty string
        try {
            Patron test = new Patron(goodUserId, "", 1); //Calls user constructor from parent
            fail(); //Should not get here
        } catch (IllegalArgumentException e) {
            //This should be thrown
        }
    
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.User#verifyPassword(java.lang.String)}
     * .
     * 
     * The verify password method checks the password twice. The first uses the actual password,
     * which should return true. The second test uses the wrong password to ensure the method
     * returns false.
     */
    @Test
    public void testVerifyPassword() {
        assertTrue(dummyPatron.verifyPassword("password"));
        assertFalse(dummyPatron.verifyPassword("jibberish"));
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.patron.User#getId()}.
     * 
     * The test get id ensures the user's id actually gets returned. 
     */
    @Test
    public void testGetId() {
        assertEquals("user", dummyPatron.getId());
    }

    /**
     * Test method for
     * {@link edu.ncsu.csc216.wolf_library.patron.User#compareTo(edu.ncsu.csc216.wolf_library.patron.User)}
     * .
     * 
     * The compare to method ensures the patron's lexicographical comparisons work correctly.
     * The method should return not equals if patron alpha compares to patron beta.
     *  Patrons with the same name should return 0. 
     */
    @Test
    public void testCompareTo() {
        Patron alpha = new Patron("alpha", "pw", 1);
        Patron beta = new Patron("beta", "pw", 1);
        Patron alphaTwo = new Patron("alpha", "pw", 1);
        Patron nullPatron = null;
        
        //If these two are the same, there are problems.
        assertNotEquals(alpha.compareTo(beta), beta.compareTo(alpha));
        
        //Ensure the same names return 0
        assertEquals(0, alpha.compareTo(alphaTwo));
        
        //Ensure it works reflexively
        assertEquals(0, alphaTwo.compareTo(alpha));
        
        //Ensure the exception works for nulls
        try {
            alpha.compareTo(nullPatron);
            fail();
        }
        catch (NullPointerException e) {
            //This should be thrown
        }
    }

}
