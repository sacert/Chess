package gameStart;
import player.*;
import units.*;

import java.util.Scanner;

import board.*;

public class GameStart {  
	
	public static boolean isWhiteTurn = true;
	
    public static void start() {
    	
//    	Point test = new Point(1,2);
//    	System.out.println(convertPointToString(test));   	
    	boolean gameOver = false;
    	boolean valid = false;
    	Board board = new Board();
    	board.printBoard();
   
    	
    	// *Note add this into another class
    	
    	// so long as the game is not over, keep the game running
    	// *NOTE: create a king checker, if either one has died, game is over
    	// so check both kings and determine winner
    	while(!gameOver) {
    		Board.checkTrueWhite = Board.isCheckWhite();
    		Board.checkTrueBlack = Board.isCheckBlack();
    		//getTurn(isWhiteTurn);
    		valid = false;
	    	// get user input on which piece to move
	    	Scanner user_input = new Scanner(System.in);
	    	int xCoord;
	    	int yCoord;
    	
	    	while(!valid) {
	    		if(isWhiteTurn) {
	    			System.out.println("White's turn");
	    		} else {
	    			System.out.println("Black's turn");
	    		}
		    	System.out.print("Which piece to move: ");
		    	String input =  user_input.nextLine();
		    	Point point = Point.convertStringToPoint(input);
		    	yCoord = point.getY();
		    	xCoord = point.getX();
		    	
		    	if(isWhiteTurn) {
			    	if(board.board[yCoord][xCoord] != null && board.board[yCoord][xCoord].isWhite) {
			    		
			    		if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
					    	System.out.println("***Select a new piece***");
			    			continue;
			    		}
			    		
//			    		board.movePiece(yCoord, xCoord);
			    		valid = true;
			    		isWhiteTurn = false;
			    	}
			    	else {
			    		System.out.println("Invalid!");
			    	}
		    	} else {
		    		if(board.board[yCoord][xCoord] != null && !board.board[yCoord][xCoord].isWhite) {
			    		
			    		if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
					    	System.out.println("***Select a new piece***");
			    			continue;
			    		}
			    		
//			    		board.movePiece(yCoord, xCoord);
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
