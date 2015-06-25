package edu.ncsu.csc216.wolf_library.inventory;


public class Testing {

    public static void main(String [] args){
        BookDB test = new BookDB(args[0]);
        System.out.println(test.traverse());
        
        Book love = new Book("1 Love in the Time of Cholera by Gabriel Garcia Marquez");
        Book pride = new Book("1 Pride and Prejudice by Jane Austin");
        
        int compare = love.compareTo(pride);
        System.out.println(compare);
        
        
        
    }

}
