package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import board.*;
import units.*;
import constants.*;


public class FXController implements Initializable {

	public static boolean isWhiteTurn = true;
	@FXML private GridPane chessGrid;
	
	
	private final int gridSize = 8;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	

		for (int col = 0; col < gridSize; col++) {
			for (int row = 0; row < gridSize; row ++) {
				StackPane square = new StackPane();
				String color;
				if ((col + row) % 2 == 0) {
					color = "white";
				} else {
					color = "black";
				}
				square.setStyle("-fx-background-color: "+color+";");
				chessGrid.add(square, col, row);
			}
		}

	}
	
	
	private void oldMain(){
    	boolean gameOver = false;
    	boolean valid = false;
    	Board board = new Board();
    	board.printBoard();
   
    	
    	// *Note add this into another class
    	
    	// so long as the game is not over, keep the game running
    	// *NOTE: create a king checker, if either one has died, game is over
    	// so check both kings and determine winner
    	while(!gameOver) {
    		Board.checkTrue = Board.isCheck();
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
			    		
			    		if(!board.movePiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
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
			    		
			    		if(!board.movePiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
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
