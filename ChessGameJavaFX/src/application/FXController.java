package application;

import gameStart.GameStart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import units.Piece;
import board.Board;
import board.Move;
import board.Point;
import constants.Constants;


public class FXController implements Initializable, Constants {

	public static boolean isWhiteTurn = true;
	private boolean gameOver = false;
	@FXML private GridPane chessGrid;
	@FXML private Text turnText;
	@FXML private ListView<String> movesList;


	private final int gridSize = 8;
	private double squareSize;
	
	private boolean selectingPiece = true;
	
	// these colors must support the Java FX color coding scheme
	private String color1 = "white";
	private String color2 = "grey";
	
	private Vector<Move> moves;
	
	boolean originalSpot = false;
	
	private int counter = 1;
	ObservableList<String> movesListStringEmpty = FXCollections.observableArrayList("");
	ObservableList<String> movesListString = FXCollections.observableArrayList();
	
	private int selectedPieceY, selectedPieceX;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setTurnText("WHITE");
		squareSize = chessGrid.getPrefHeight()/gridSize;
		
		Board board = new Board();
		refreshBoard(board);
		chessGridMouseClickEvents(board);
		movesList.getStyleClass().add("list");
		
		movesList.setItems(movesListStringEmpty);
		
//		GameStart game = new GameStart();
//		start(board);
		
		// prevents the user in selecting a cell in the table view
		movesList.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				mouseEvent.consume();
			}
			
		});
	}





	private void setTurnText(String color) {
		turnText.setText("TURN:" + " " + color);
	}

	// Colors the board with the two specified colors (ie black/white) and puts all the pieces on from the board.
	private void refreshBoard(Board board) {
		Scene scene = new Scene(new Group());
		// get the css file
		
		for (int col = 0; col < gridSize; col++) {
			for (int row = 0; row < gridSize; row ++) {
				StackPane square = new StackPane();
				String color;
				if ((col + row) % 2 == 0) {
					color = color1;
				} else {
					color = color2;
				}
				square.getStyleClass().add("panel");
				square.setStyle("-fx-background-color: " + color + ";");
				//square.setStyle("-fx-border-radius: 100;");
				//square.setStyle("bordered-titled-title;");
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

	// The mouse click listener is used for selecting and moving pieces on the grid.
	private void chessGridMouseClickEvents(Board board) {
		chessGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				int row = (int)(e.getY()/squareSize);
				int col = (int)(e.getX()/squareSize);
				String columnString = "Column: " + col;
				String rowString = "Row: " + row;
				System.out.println(columnString + ", " + rowString);

				Board.checkTrueWhite = Board.isCheckWhite();
				Board.checkTrueBlack = Board.isCheckBlack();
				
				System.out.println("------");
				System.out.println(Board.checkTrueWhite);
				System.out.println(Board.checkTrueBlack);
				//getTurn(isWhiteTurn);
				boolean valid = false;

				int xCoord = col;
				int yCoord = row;
				
				final Vector<Move> moves;

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
								
								// highlight piece clicked
								selectedPieceX = col;
								selectedPieceY = row;
								System.out.println("SELECTED y is : " + selectedPieceY + " and x is : " + selectedPieceX); 

								pieceSuccessfullySelected(col, row, true);
								moves = board.getMoves();
								
								// highlight all possible moves
								for(int i = 0; i < moves.size(); i++) {
									pieceSuccessfullySelected(moves.elementAt(i).x2, moves.elementAt(i).y2, false);
								}
								
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
								
								// highlight piece clicked
								selectedPieceX = col;
								selectedPieceY = row;
								System.out.println("SELECTED y is : " + selectedPieceY + " and x is : " + selectedPieceX); 
								pieceSuccessfullySelected(col, row,true);
								moves = board.getMoves();
								
								// highlight all possible moves
								for(int i = 0; i < moves.size(); i++) {
									pieceSuccessfullySelected(moves.elementAt(i).x2, moves.elementAt(i).y2,false);
								}
							}
							else {
								System.out.println("Invalid!");
							}
						}
					}
				} else { // if we are MOVING a piece.
					moves = board.getMoves();
					if(Board.movePiece(yCoord, xCoord, selectedPieceY, selectedPieceX, moves)){ // the piece moved successfully
//					if(board.movePiece(yCoord, xCoord, moves)){ // the piece moved successfully
						
						// get the type of turn it is
						String whoseTurn = null;
						if(isWhiteTurn)
							whoseTurn = "W";
						else
							whoseTurn = "B";
						
						// determine the string of the piece that has moved (ex. board[0][0] = a7)
						Point p1 = new Point(selectedPieceY, selectedPieceX);
						String boardPiece = getBoardPieceLetter(row, col);
						Point p = new Point(row, col);
						
						// determine string format that will be place in the list view
						String moveListAddition = null;
						if(counter < 10) 
							 moveListAddition = String.format("%s. %6s %s->%s     %s", counter, boardPiece,Point.convertPointToString(p1),Point.convertPointToString(p),whoseTurn); 
						else
							 moveListAddition = String.format("%s.%6s %s->%s     %s", counter, boardPiece,Point.convertPointToString(p1),Point.convertPointToString(p),whoseTurn); 
						
						// add the created string into the list and insert it into the list view
						movesListString.add(moveListAddition);
						movesList.setItems(movesListString);
						
						// increment counter for next list view slot
						counter++;
						
						selectingPiece = true;
						isWhiteTurn = !isWhiteTurn;
						refreshBoard(board);
						printWhoseTurn();
					} else {
						selectingPiece = true;
						refreshBoard(board);
						printWhoseTurn();
					}
				}
			}

			public void pieceSuccessfullySelected(int col, int row, boolean originalSpot) {


				
				
				// put the child in here 
				Node results = null;
				
				// find where on the gridpane does the piece selected lie
				// get all children
				ObservableList<Node> children = chessGrid.getChildren();
				if(children != null && chessGrid != null)
				{
					for(Node node : children) {
						if(node != null)
						{
							if(chessGrid.getRowIndex(node) != null && chessGrid.getColumnIndex(node) != null) {
								
								// once you find the child within the gridpane that matches the selected piece, highlight it
								if(chessGrid.getRowIndex(node) == row && chessGrid.getColumnIndex(node) == col ) {
									results = node;
									
									if(originalSpot) 
										results.setStyle("-fx-background-color: #8AACB8;");
									else
										results.setStyle("-fx-background-color: #ADD8E6;");
								}
							}
						}
					}
				}
				
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
	
	private String getBoardPieceLetter(int y, int x) {

		String piece = null;
		
		switch(Board.board[y][x].type){
		case PAWN:
			piece = ("Pawn");
			break;
		case ROOK:
			piece = ("Rook");
			break;
		case BISHOP:
			piece = ("Bishop");
			break;
		case QUEEN:
			piece = ("Queen");
			break;
		case KING:
			piece = ("King");
			break;
		case KNIGHT:
			piece = ("Knight");
			break;
		}
		return piece;
	}




}

