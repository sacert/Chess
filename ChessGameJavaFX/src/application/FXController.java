package application;

import gameStart.GameStart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import board.Board;
import board.Move;
import board.Point;
import constants.Constants;


public class FXController implements Initializable, Constants {

	public static boolean isWhiteTurn = true;
	private boolean gameOver = false;
	@FXML private GridPane chessGrid;
	@FXML private Text turnText;

	private final int gridSize = 8;
	private double squareSize;
	
	private boolean selectingPiece = true;
	
	private Vector<Move> moves;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setTurnText("WHITE");
		squareSize = chessGrid.getPrefHeight()/gridSize;
		
		Board board = new Board();
		initializeBoard("white", "grey", board);
		chessGridMouseClickEvents(board);
		
//		GameStart game = new GameStart();
//		start(board);
	}





	private void setTurnText(String color) {
		turnText.setText("TURN:" + " " + color);
	}





	// Colors the board with the two specified colors (ie black/white) and puts all the initial pieces on.
	private void initializeBoard(String color1, String color2, Board board) {
		for (int col = 0; col < gridSize; col++) {
			for (int row = 0; row < gridSize; row ++) {
				StackPane square = new StackPane();
				String color;
				if ((col + row) % 2 == 0) {
					color = color1;
				} else {
					color = color2;
				}
				square.setStyle("-fx-background-color: " + color + ";");
				chessGrid.add(square, col, row);

				Image piece = getPiece(row, col, board);
				ImageView imagePiece = new ImageView(piece);

				imagePiece.setFitHeight(squareSize);
				imagePiece.setFitWidth(squareSize);
				chessGrid.add(imagePiece, col, row);
			}
		}
	}

	// determines the location of the board in terms of a 8x8 array
	private void chessGridMouseClickEvents(Board board) {
		chessGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				int col = (int)(e.getY()/squareSize);
				int row = (int)(e.getX()/squareSize);
				String columnString = "Column: " + col;
				String rowString = "Row: " + row;
				System.out.println(columnString + ", " + rowString);
				


				Board.checkTrue = Board.isCheck();
				//getTurn(isWhiteTurn);
				boolean valid = false;
				
				
				printWhoseTurn();


				int xCoord = row;
				int yCoord = col;
				
				if(selectingPiece){ // if we are SELECTING a piece



					if(!valid) {
						printWhoseTurn();
//
//
//						int xCoord = row;
//						int yCoord = col;

						if(isWhiteTurn) {
							if(board.board[yCoord][xCoord] != null && board.board[yCoord][xCoord].isWhite) {

								if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
									System.out.println("***Select a new piece***");
									return;
								}

								//				    		board.movePiece(yCoord, xCoord);

								// the board can move
//								moves = board.getMoves();
								valid = true;
								selectingPiece = false;
//								isWhiteTurn = false;
							}
							else {
								System.out.println("Invalid!");
							}
						} else { // black's turn
							if(board.board[yCoord][xCoord] != null && !board.board[yCoord][xCoord].isWhite) {

								if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
									System.out.println("***Select a new piece***");
									return;
								}

								//				    		board.movePiece(yCoord, xCoord);
								// the board can move
//								moves = board.getMoves();
								valid = true;
								selectingPiece = false;
//								isWhiteTurn = true;
							}
							else {
								System.out.println("Invalid!");
							}
						}
					}
				} else { // if we are MOVING a piece.
					
					
					final Vector<Move> moves = board.getMoves();
					
					if(board.movePiece(yCoord, xCoord, moves)){ // the piece moved successfully
						selectingPiece = true;
						isWhiteTurn = !isWhiteTurn;
						initializeBoard("white", "grey", board);
						printWhoseTurn();
					} else {
						selectingPiece = false;
						printWhoseTurn();
					}
					
					
					
					
					
//					if(!valid) {
//						// get the position of the user's input in the form of y and then x
//						// NOTE * can swap these to make it in the format of x and y
//						System.out.print("\nMove to: ");
//						
//						
//
////				    	String input =  user_input.nextLine();
////				    	
////				    	if(input.equals(undoPieceSelectionPrompt)){
////				    		return false;
////				    	}
//				    	
//						// find if that move the user is requesting is part of the possible moves
//						for( int i = 0; i < moves.size(); i++) {
//							mm = (Move) moves.elementAt(i);
//							if(xCoord == mm.x2 && yCoord == mm.y2) {
//								valid = true;
//								validInt = i;
//								break;
//							}
//						}
//						
//						// pawn promotion
//						if(board[y][x].isWhite && board[y][x].type == PAWN) {
//							if(yCoord == 0) {
//								System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//								System.out.print(":");
//								input =  user_input.nextLine();
//								while(stringToByte(input) == -1) {
//									System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//									System.out.print(":");
//									input =  user_input.nextLine();
//								}
//								board[y][x].type = stringToByte(input);
//							}
//						}
//						else if (!board[y][x].isWhite && board[y][x].type == PAWN) {
//							if(yCoord == 7) {
//								System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//								System.out.print(":");
//								input =  user_input.nextLine();
//								while(stringToByte(input) == -1) {
//									System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//									System.out.print(":");
//									input =  user_input.nextLine();
//								}
//								board[y][x].type = stringToByte(input);
//							}
//						}
//						// if not, display an error message and let them try again
//						if(!valid)
//							System.out.println("Invalid!");
//					}
//					if(valid) {
//						// if the move is in the set of possible moves, perform that move
//						board[y][x].canCastle = false;
//
//						mm = (Move) moves.elementAt(validInt);
//						
//
//						// if a castling move
//						if(board[y][x] != null){
//							if(board[y][x].type == KING){
//								
//								// white King
//								boolean whiteKingIsInOriginalPosition = y == 7 && x == 4;
//								if(board[y][x].isWhite && whiteKingIsInOriginalPosition){
//									
//									// Move left rook to appropriate spot
//									if(mm.y2 == 7 & mm.x2 == 2){
//										board[7][3] = board[7][0];
//										board[7][0] = null;
//									}
//									
//									// Move right rook to appropriate spot
//									if(mm.y2 == 7 & mm.x2 == 6){
//										board[7][5] = board[7][7];
//										board[7][7] = null;
//										
//									}
//								}
//								
//								// black King
//								boolean blackKingIsInOriginalPosition = y == 0 && x == 4;
//								if(!board[y][x].isWhite && blackKingIsInOriginalPosition){
//									
//									// Move left rook to appropriate spot
//									if(mm.y2 == 0 & mm.x2 == 2){
//										board[0][3] = board[0][0];
//										board[0][0] = null;
//									}
//									
//									// Move right rook to appropriate spot
//									if(mm.y2 == 0 & mm.x2 == 6){
//										board[0][5] = board[0][7];
//										board[0][7] = null;
//										
//									}
//								}
//								
//								
//
//							}
//						}
//						
//						
//						mm.movePiece(board);
//						
//
//					}
					
					
					
					
					
					
					
				}
				
				
				
				
				
				
			}
		});
	}


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


public void printWhoseTurn() {
		if(isWhiteTurn) {
			System.out.println("White's turn");
			setTurnText("WHITE");
		} else {
			System.out.println("Black's turn");
			setTurnText("BLACK");
		}
	}





public static void start(Board board) {
	
//	Point test = new Point(1,2);
//	System.out.println(convertPointToString(test));   	
	boolean gameOver = false;
	boolean valid = false;
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
		    		
		    		if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
				    	System.out.println("***Select a new piece***");
		    			continue;
		    		}
		    		
//		    		board.movePiece(yCoord, xCoord);
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
		    		
//		    		board.movePiece(yCoord, xCoord);
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

