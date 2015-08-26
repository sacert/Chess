package application;

import java.awt.Graphics;
//import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import board.*;
import units.*;
import constants.*;


public class FXController implements Initializable, Constants {

	public static boolean isWhiteTurn = true;
	@FXML private GridPane chessGrid;
	
	private final int gridSize = 8;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		// set the board
		Board board = new Board();
		
		for (int col = 0; col < gridSize; col++) {
			for (int row = 0; row < gridSize; row ++) {
				StackPane square = new StackPane();
				String color;
				if ((col + row) % 2 == 0) {
					color = "white";
				} else {
					color = "grey";
				}
				square.setStyle("-fx-background-color: "+color+";");
				chessGrid.add(square, col, row);
				
				Image piece = getPiece(row, col,board);
				ImageView imagePiece = new ImageView(piece);
				
				// dont like hard coding, need to change these to be dynamic
				imagePiece.setFitHeight(100);
				imagePiece.setFitWidth(100);
				chessGrid.add(imagePiece, col, row);
			}
		}
		
		// *NOTE: Switch the "/100" as it is static, want to have to relative to the board size
		// determines the location of the board in terms of a 8x8 array
		chessGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("Column: " + (int)(e.getX()/100));
				System.out.println("Row: " + (int)(e.getY()/100));
			}
		});
	}
	
	//private (row, col, board)
	// board[row][col].isWhite && type
	//case
	// return image
	
	private Image getPiece(int row, int col, Board board) {
		
		// store the piece's image in here
		Image piece = null;
		
		// make sure there the space isnt empty
		if(board.board[row][col] != null) {
			// if the piece is white
			if(board.board[row][col].isWhite) {
				
				switch(board.board[row][col].type){
				case PAWN:
					piece = new Image("/images/wp.gif");
					break;
				case ROOK:
					piece = new Image("/images/wr.gif");
					break;
				case BISHOP:
					piece = new Image("/images/wb.gif");
					break;
				case QUEEN:
					piece = new Image("/images/wq.gif");
					break;
				case KING:
					piece = new Image("/images/wk.gif");
					break;
				case KNIGHT:
					piece = new Image("/images/wn.gif");
					break;
				}
				
			} else { // if the piece is black
				
				switch(board.board[row][col].type){
				case PAWN:
					piece = new Image("/images/bp.gif");
					break;
				case ROOK:
					piece = new Image("/images/br.gif");
					break;
				case BISHOP:
					piece = new Image("/images/bb.gif");
					break;
				case QUEEN:
					piece = new Image("/images/bq.gif");
					break;
				case KING:
					piece = new Image("/images/bk.gif");
					break;
				case KNIGHT:
					piece = new Image("/images/bn.gif");
					break;
				}
			}
		}
		
		return piece;
		
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
