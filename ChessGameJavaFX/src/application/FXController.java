package application;

import gameStart.GameStart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
	
	// these colors must support the Java FX color coding scheme
	private String color1 = "white";
	private String color2 = "grey";
	
	private Vector<Move> moves;
	
	
	private int selectedPieceY, selectedPieceX;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		
		setTurnText("WHITE");
		squareSize = chessGrid.getPrefHeight()/gridSize;
		
		Board board = new Board();
		refreshBoard(board);
		chessGridMouseClickEvents(board);
		
//		GameStart game = new GameStart();
//		start(board);
	}





	private void setTurnText(String color) {
		turnText.setText("TURN:" + " " + color);
	}





	// Colors the board with the two specified colors (ie black/white) and puts all the pieces on from the board.
	private void refreshBoard(Board board) {
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
		
//		Node c = getNodeFromGridPane(chessGrid, 0, 0);
//		 c = chessGrid.getChildren().get(1);
//		System.out.println(c.getClass());
//		c.setStyle("-fx-background-color:yellow;");
	}
	
	
		
	// Input: column and row of gridpane. Output: gridpane cell at specified col & row
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    return gridPane.getChildren().get(row+row+1+(16*col));
	}
	

	// The mouse click listener is used for selecting and moving pieces on the grid.
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

				int xCoord = row;
				int yCoord = col;

				if(selectingPiece){ // if we are SELECTING a piece

					if(!valid) {
						printWhoseTurn();
						
						if(isWhiteTurn) {
							if(board.board[yCoord][xCoord] != null && board.board[yCoord][xCoord].isWhite) {

								if(!board.selectPiece(yCoord, xCoord)){ // if the piece cannot move there, restart the loop.
									System.out.println("***Select a new piece***");
									return;
								}

								valid = true;
								pieceSuccessfullySelected(col, row);
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

								valid = true;
								pieceSuccessfullySelected(col, row);
							}
							else {
								System.out.println("Invalid!");
							}
						}
					}
				} else { // if we are MOVING a piece.
					
					
					final Vector<Move> moves = board.getMoves();

					if(board.movePiece(yCoord, xCoord, selectedPieceY, selectedPieceX, moves)){ // the piece moved successfully
//					if(board.movePiece(yCoord, xCoord, moves)){ // the piece moved successfully
						selectingPiece = true;
						isWhiteTurn = !isWhiteTurn;
						refreshBoard(board);
						printWhoseTurn();
					} else {
						selectingPiece = false;
						printWhoseTurn();
					}
				}
			}

			public void pieceSuccessfullySelected(int col, int row) {
				Node c = getNodeFromGridPane(chessGrid, row, col);
				c.setStyle("-fx-background-color:yellow;");
				selectedPieceX = row;
				selectedPieceY = col;
				selectingPiece = false;
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




}

