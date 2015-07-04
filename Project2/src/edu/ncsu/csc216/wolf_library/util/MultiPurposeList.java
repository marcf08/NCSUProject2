package edu.ncsu.csc216.wolf_library.util;

import java.util.NoSuchElementException;


/**
 * The multi purpose list creates a multi-purpose list that works like a linked
 * list. It has a nested class called Node that corresponds to an element of the
 * list. The list works for generic type T.
 * 
 * @author Marcus
 *
 */
/**
 * The multipurpose list class instantiates a multipurpose list given a generic type T.
 * @author Marcus
 *
 * @param <T>T a generic type
 */
public class MultiPurposeList<T> {
    Node head; // The head node creates a reference to the first element in the
               // list
    Node iterator; // The iterator data member can traverse through the list

    /**
     * The constructor simply sets the head node to null.
     */
    public MultiPurposeList() {
        head = null;
    }

    /**
     * The add item method inserts an item into the linked list at a given
     * position.
     * 
     * @param pos
     *            the position of the item to be added
     * @param temp
     *            the item to add to the list
     */
    public void addItem(int pos, T temp) {
        if (pos == 0) {
            head = new Node(temp, head);
        } else if (head != null && pos > 0) {
            iterator = head;
            while (iterator != null && pos > 1) {
                iterator = iterator.link;
                pos--;
            }
        }
        if (iterator != null) {
            iterator.link = new Node(temp, iterator.link);
        }

        // Ensure we reset the iterator after the operation
        // Wouldn't want to leave it where it's at and then
        // attempt another operation.
        resetIterator();

    }

    /**
     * The look at item method examines the item in the list at a given
     * position.
     * 
     * @param pos
     *            the position of the item requested
     * @return the data requested at a specific position
     */
    public T lookAtItemN(int pos) {
        // Check the client's request to ensure it's a valid position
        if (pos >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }

        // If position is zero, return the head's data
        if (pos == 0) {
            return head.data;
        }

        resetIterator(); // Make sure the iterator is in the right place to
        // start the search

        while (head != null && pos >= 1) {
            iterator = iterator.link;
            pos--;
        }
        return iterator.data; // Return the data at the index
    }

    /**
     * The add to read method adds an item to the rear of the list.
     * 
     * @param item
     *            the item to add to the rear of the list
     */
    public void addToRear(T item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) { // If the list is empty, the item gets added to the
                         // first position
            head = new Node(item, head);

        } else {
            resetIterator(); // Start the iterator at head

            while (iterator.link != null) {
                iterator = iterator.link; // Traverse the list

                // The iterator should stop at the end of the list)
            }

            // The iterator should be null at this point

            // Create a new node with null at the end to signify the end of the
            // list

            iterator.link = new Node(item, null);

            resetIterator(); // Reset the iterator to prevent errors elsewhere
        }
    }

    /**
     * The remove method removes an item from the list at a given position. It
     * sends the iterator down the list once to find the item. Since it will
     * have already passed the item at that point, the iterator will then need
     * to run through it once more to remove the item one element before the
     * item it found on the first traversal through the list.
     * 
     * @param pos
     *            the position of the item to remove
     * @return the item that was removed
     */
    public T remove(int pos) {

        // First ensure the position is valid
        if (pos >= size()) {
            throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
        }

        if (pos < 0) {
            throw new IndexOutOfBoundsException(Constants.EXP_NO_MORE_VALUES_IN_LIST);
        }

        // Special case for if the front item needs to be removed
        if (pos == 0) {
            T headRemove = head.data;
            head = head.link;
            return headRemove;
        }

        Node current = head;
        Node previous = null;

        while (current != null && pos > 0) {
            previous = current;
            current = current.link;
            pos--;
        }

        T removed = current.data;

        if (current != null) { // current should point to the item to be removed
            if (current == head) { // remove item 0?
                head = head.link;
            } else { // not removing item 0
                previous.link = current.link;
            }

        }

        return removed;

    }

    /**
     * The move ahead method moves an element forward one position in the list.
     * If the position is 0, the method does nothing.
     * 
     * @param pos
     *            the item to advance forward
     */
    public void moveAheadOne(int pos) {
        if (pos == 0) {
            // Do nothing
            return;
        } else if (pos == 1) {

            resetIterator(); // Reset the iterator for good measure

            T temp = iterator.data; // "Store" the item after head

            head = head.link; // Head becomes the reference to the next

            addItem(pos, temp); // Add the original item

            resetIterator(); // Reset the iterator for good measure

        } else {
            //Simple swap algorithm
            T temp = remove(pos);
            addItem(pos - 1, temp); 
            
            
        }

    }

    /**
     * The size method runs the iterator through the list to count the number of
     * elements.
     * 
     * @return size the number of elements in the list.
     */
    public int size() {
        int size = 0;
        if (isEmpty()) {
            return size; // Return zero if the list is empty
        }

        resetIterator(); // Reset the iterator to ensure it starts at head

        while (iterator != null) {
            iterator = iterator.link; // Traverse through the list
            size++; // Increment the size
        }
        resetIterator();

        return size;

    }

    /**
     * The is empty method examines the list to see if it is empty. It examines
     * the head node to see if it's null.
     * 
     * @return true if the list is empty and false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * The reset iterator method moves the iterator back to the first element of
     * the list.
     */
    public void resetIterator() {
        iterator = head;
    }

    /**
     * The has next method returns true if the iterator is pointing to a list
     * element.
     * 
     * @return true if the iterator is pointing to a list element and false
     *         otherwise.
     */
    public boolean hasNext() {
        return (iterator != null);
    }

    /**
     * The next method returns the data of the node that the iterator currently
     * points to. If the iterator returns null, the element must have no data,
     * and so the method throws a no such element exception.
     * 
     * @return the data of the node that the iterator currently points to
     */
    public T next() {
        if (iterator == null) {
            throw new NoSuchElementException();
        }
        if (!hasNext()) {
            throw new NoSuchElementException(Constants.EXP_NO_MORE_VALUES_IN_LIST);
        }
        T data = iterator.data; //Save the data
        
        iterator = iterator.link; //Shift the iterator       
        
        return data; //Return the data
        
    }

    /**
     * The node class creates a node with data and a reference to another node.
     * 
     * @author Marcus
     *
     */
    private class Node {
        /**
         * The string data member is an element of data in the list.
         */
        public T data;
        /**
         * The link references the next node in a list of nodes.
         */
        public Node link;

        /**
         * The constructor accepts a parameter of book data and links (refers)
         * to another node.
         */
        public Node(T item, Node link) {
            this.data = item;
            this.link = link;
        }

    }

}