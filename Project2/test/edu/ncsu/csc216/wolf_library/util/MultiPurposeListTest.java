/**
 * 
 */
package edu.ncsu.csc216.wolf_library.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * The test ensures the functionality of the multi-purpose list. Since the list is a generic type,
 * the test uses a string to run the tests. The GUI works with books types, so the assumption is that the list
 * works fairly well. However, we enhance our testing by using a different data type.
 * 
 * @author Marcus
 *
 */
public class MultiPurposeListTest {
    /**
     * Instance field for multipurpose list of strings.
     */
    private MultiPurposeList<String> testList;
    /**
     * The set up instantiates a new multipurpose list.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        testList = new MultiPurposeList<String>();
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#MultiPurposeList()}.
     * 
     * The constructor ensures the head of the list is set to null. If it's not null, the list was not created properly.
     */
    @Test
    public void testMultiPurposeList() {
        assertEquals(null, testList.head);
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#addItem(int, java.lang.Object)}.
     * 
     * The add item method tests to ensure the list throws the exceptions for incorrect positions and for
     * null objects. It also ensures valid items are added.
     * 
     */
    @Test
    public void testAddItem() {
        //Test null pointer
        String toAdd = null;
        try {
            testList.addItem(0, toAdd);
            fail(); //Should not get here
        } catch (NullPointerException e) {
            //This should get thrown
        }
        
        //Test negative index
        String valid = "valid item";
        try {
            testList.addItem(-5,valid);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should get thrown
        }
        
        //Add a valid item
        testList.addToRear(valid);
        
        //Test an index too high
        try {
            testList.addItem(25, "attempt");
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should get thrown
        }
        
        //Test to ensure the valid item was added
        assertEquals(valid, testList.lookAtItemN(0));
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#lookAtItemN(int)}.
     * 
     * The look at item ensures exceptions are thrown for invalid indexes. It also returns the item at a given
     * position to ensure that the item is in the position it was stored.
     */
    @Test
    public void testLookAtItemN() {
        String valid = "valid0";
        String valid2 = "valid1";
        //Add the valid item
        testList.addToRear(valid);
        testList.addToRear(valid2);
        
        //Test negative index
        try {
            testList.lookAtItemN(-25);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //Should be thrown
        }
        
        //Test an index too high
        try {
            testList.lookAtItemN(25);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //Should be thrown
        }
        
        //Test valid additions
        assertEquals("valid0", testList.lookAtItemN(0));
        assertEquals("valid1", testList.lookAtItemN(1));

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#addToRear(java.lang.Object)}.
     * 
     * The test add to rear method ensures an item gets added to the rear of the list. It also
     * tests the null pointer exception in the event the client code attempts to add a null item.
     */
    @Test
    public void testAddToRear() {
        String badString = null;
        //Test null addition
        try {
            testList.addToRear(badString);
            fail(); //Should not get here
        } catch (NullPointerException e) {
            //This should get thrown
        }
        
        //Test valid addition
        String validZero = "0";
        String validOne = "1";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        
        //Ensure they were added in the proper order
        assertEquals("0", testList.lookAtItemN(0));
        assertEquals("1", testList.lookAtItemN(1));

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#remove(int)}.
     * The test remove method ensures items remove from the list properly. It also throws the 
     * exceptions for boundaries (negative and greater than size).
     */
    @Test
    public void testRemove() {
        String validZero = "0";
        String validOne = "1";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        
        //Test bad (negative) index
        try {
            testList.remove(-5);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        //Test index too high
        try {
            testList.remove(5);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        //Test valid removal of first item (index 0)
        testList.remove(0);
        assertEquals("1", testList.lookAtItemN(0));
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#moveAheadOne(int)}.
     * 
     * The test move ahead one method ensures adjacent items swap positions. It verifies the exceptions
     * were thrown with regards to valid indexes (too high or negative.)
     */
    @Test
    public void testMoveAheadOne() {
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        //Test bad (negative) index
        try {
            testList.remove(-5);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        //Test index too high
        try {
            testList.remove(5);
            fail(); //Should not get here
        } catch (IndexOutOfBoundsException e) {
            //This should be thrown
        }
        
        
        //The last tests allow the value 2 to trace upward towards the front of the list
        //The tests examine it at each step
        testList.moveAheadOne(2);
        //Ensure the values have swapped places
        assertEquals("0", testList.lookAtItemN(0));
        assertEquals("2", testList.lookAtItemN(1));
        assertEquals("1", testList.lookAtItemN(2));
        
        testList.moveAheadOne(1);
        //Ensure the values have swapped places
        assertEquals("2", testList.lookAtItemN(0));
        assertEquals("0", testList.lookAtItemN(1));
        assertEquals("1", testList.lookAtItemN(2));

    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#size()}.
     * 
     * The test size method checks the size of the list to ensure it equals the items added.
     */
    @Test
    public void testSize() {
        //Should be zero for an empty list
        assertEquals(0,testList.size());
        
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        //Test size should be three now
        assertEquals(3, testList.size());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#isEmpty()}.
     * 
     * The empty method simply checks whether the list is empty. At first, the list should be empty
     * since it has no values. Upon adding a value, the method should return false.
     */
    @Test
    public void testIsEmpty() {
        //Empty list, should be true
        assertTrue(testList.isEmpty());
        
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        //List no longer empty, should be false
        assertFalse(testList.isEmpty());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#resetIterator()}.
     * 
     * The reset iterator method ensures the iterator moves all the way back to head. We'll add some values
     * for the iterator to traverse, ensure it moves, and then make sure it resets.
     */
    @Test
    public void testResetIterator() {
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        testList.next(); //Move the iterator ahead one value, should be 1
        testList.resetIterator(); //Reset it
        assertEquals("0", testList.next());//Should be 0, the first value
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#hasNext()}.
     * 
     * The test has next method checks to see if the iterator points to a valid value. It should return true
     * if something exists, and false otherwise.
     */
    @Test
    public void testHasNext() {
        //Add some values
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        //There should be something else
        assertTrue(testList.hasNext());
        
        //Move the iterator several times to the end of the list
        testList.next();
        testList.next();
        testList.next();
        //Should not be anything else, iterator is hanging off the end of the list
        assertFalse(testList.hasNext());
        
    }

    /**
     * Test method for {@link edu.ncsu.csc216.wolf_library.util.MultiPurposeList#next()}.
     * 
     * The next method ensures the iterator moves across the list. It should be one of the first
     * methods tested because other methods use it to test the iterator's movement. The method
     * tests the exceptions as well for no more values and for a null value.
     */
    @Test
    public void testNext() {
        //Add some values
        String validZero = "0";
        String validOne = "1";
        String validTwo = "2";
        testList.addToRear(validZero);
        testList.addToRear(validOne);
        testList.addToRear(validTwo);
        
        testList.next();
        testList.next();
        testList.next();
        //At this point, there should be nothing else, should throw no such element
        try {
            testList.next();
            fail(); //Should not make it here
        } catch (NoSuchElementException e) {
            //This should get thrown
        }
        
        testList.resetIterator(); //Reset the iterator
        
        //Test valid value at the front of the list
        assertEquals("0", testList.next());
 
        
        
    }

}
