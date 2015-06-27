package edu.ncsu.csc216.wolf_library.util;

import edu.ncsu.csc216.wolf_library.inventory.Book;

public class ListTest {

    public static void main(String[] args) {

        Book bookZero = new Book("1 bookZero");
        Book bookOne = new Book("1 bookOne");
        Book bookTwo = new Book("1 bookTwo");
        Book bookThree = new Book("1 bookThree");

        MultiPurposeList<Book> books = new MultiPurposeList<Book>();
        
        books.addToRear(bookZero);
        books.addToRear(bookOne);
        books.addToRear(bookTwo);
        books.addToRear(bookThree);
        
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.lookAtItem(i));
        }
        System.out.println(books.size());
        books.remove(3);
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.lookAtItem(i));
        }
        System.out.println(books.size());

         books.moveAheadOne(2);
         System.out.println("After move ahead");
         for (int i = 0; i < books.size(); i++) {
             System.out.println(books.lookAtItem(i));
         }
        System.out.println(books.size());
    }
}
