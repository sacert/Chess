package main;
import player.*;
import units.*;

import java.util.Scanner;

import board.*;

public class Main {  
    public static void main(String[] args) {
    	
//    	Point test = new Point(1,2);
//    	System.out.println(convertPointToString(test));   	
    	boolean gameOver = false;
    	boolean isWhiteTurn = true;
    	boolean valid = false;
    	Board boardtest = new Board();
    	boardtest.printBoard();
    	
    	// *Note add this into another class
    	
    	// so long as the game is not over, keep the game running
    	// *NOTE: create a king checker, if either one has died, game is over
    	// so check both kings and determine winner
    	while(!gameOver) {
    		valid = false;
	    	// get user input on which piece to move
	    	Scanner user_input = new Scanner(System.in);
	    	int xCoord;
	    	int yCoord;
    	
	    	while(!valid) {
		    	System.out.print("Which piece to move: ");
		    	
		    	String input =  user_input.nextLine();
		    	
		    	Point point = Point.convertStringToPoint(input);
		    	yCoord = point.getY();
		    	xCoord = point.getX();
		    	
		    	if(isWhiteTurn) {
			    	if(boardtest.board[yCoord][xCoord] != null && boardtest.board[yCoord][xCoord].isWhite) {
			    		boardtest.movePiece(yCoord, xCoord);
			    		valid = true;
			    		isWhiteTurn = false;
			    	}
			    	else {
			    		System.out.println("Invalid!");
			    	}
		    	} else {
		    		if(boardtest.board[yCoord][xCoord] != null && !boardtest.board[yCoord][xCoord].isWhite) {
			    		boardtest.movePiece(yCoord, xCoord);
			    		valid = true;
			    		isWhiteTurn = true;
			    	}
			    	else {
			    		System.out.println("Invalid!");
			    	}
		    	}
	    	}
    	}
    }
}
