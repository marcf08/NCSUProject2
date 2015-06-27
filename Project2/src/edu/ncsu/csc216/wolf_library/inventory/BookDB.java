package edu.ncsu.csc216.wolf_library.inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import edu.ncsu.csc216.wolf_library.util.*;

/**
 * The book db class represents an internal book data base used by the Wolf
 * Library system. It consists of a multi-purpose list used to keep track of the
 * books in the library.
 * 
 * @author Marcus
 *
 */
public class BookDB {
    /**
     * The primary data member is a multi-purpose list developed in the
     * wolf_library.util package.
     */
    private MultiPurposeList<Book> books;

    /**
     * The constructor creates the database using a string of input obtained
     * from the input file.
     * 
     * @param input
     *            information taken from an input file and formatted properly
     */
    public BookDB(String input) {
        books = new MultiPurposeList<Book>();
        readFromFile(input);
    }

    /**
     * The read from file method accepts a file name as a string and uses this
     * to access the text file of books.
     * 
     * @param fileName
     *            the name of an input file
     */
    private void readFromFile(String fileName) {
        // Set up the input file
        File inputFile = new File(fileName);

        // Ensure the user has read access
        if (!inputFile.canRead()) {
            throw new IllegalArgumentException();
        }

        // Set up the scanner to read from the input file
        Scanner readInput = null;
        try {
            readInput = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from input.");
        }
        while (readInput.hasNextLine()) {
            String lineFromFile = readInput.nextLine();
            insertInOrder(new Book(lineFromFile));

        }

    }

    /**
     * The insert in order method examines the books from the input file. It
     * compares sets of two books to see where the new book goes. This method 
     *  runs comparisons between the last book and current book taken
     *  from the input file. If needed, it swaps the locations of unordered books.
     *   
     * @param book
     *            a book to insert into the inventory
     */
    private void insertInOrder(Book book) {
        if (books.isEmpty()) {
            books.addToRear(book);
            //The first book obviously goes to the rear/front of the list
        } else {
            //If it's not the first book, we have work to do.
            Book prev = null; //Book prior to the current
            Book temp = null; //Temporary book for shuffling items
            int i = 1; // Index to send down the list
            while (i <= books.size()) {
                prev = books.lookAtItem(i - 1); //Define the previous book
                if (prev.compareTo(book) > 0) { //Check the compare values
                    temp = books.remove(i - 1); //Remove the previous, it's out of order
                    books.addItem(i - 1, book); //Add the new book to it's place
                    books.addItem(i, temp); //Re-add the out of order book to the proper place
                    //Once this is done, there's no need to stay in the method.
                    //We have to bail out with the return statement to make sure
                    //none of the other statements execute.
                    return;
                }
                //Keep looking for something out of order
                i++;
            }
            //If we're still in the method at this point, nothing must have been out of order,
            //and we're at the end of the list, so insert the new book at the back.
            books.addToRear(book);


        }
    }

    /**
     * The traverse method returns a string of the books in the database in the
     * proper order.
     * 
     * @return bookListing the listing of books in the database
     */
    // TODO: REMOVE COUNT
    public String traverse() {
        String bookListing = "";
        int i = 0;
        while (i < books.size()) {
            bookListing = bookListing + books.lookAtItem(i) + "\n";
            i++;

        }
        return bookListing;
    }

}
