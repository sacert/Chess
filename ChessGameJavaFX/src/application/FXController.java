package application;

import gameStart.GameStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.Timer;

import org.controlsfx.control.PopOver;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
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
	@FXML private BorderPane blackPiecesPane;
	@FXML private BorderPane whitePiecesPane;
	@FXML private BorderPane blackTimerPane;
	@FXML private BorderPane whiteTimerPane;


	private final int gridSize = 8;
	private double squareSize;

	private boolean selectingPiece = true;

	// these colors must support the Java FX color coding scheme
	private String color1 = "white";
	private String color2 = "grey";

	private static final String imgsFolder = "/images/";


	private static final String bPawnImg = imgsFolder + "bp.gif";
	private static final String bRookImg = imgsFolder + "br.gif";
	private static final String bBishopImg = imgsFolder + "bb.gif";
	private static final String bQueenImg = imgsFolder + "bq.gif";
	private static final String bKingImg = imgsFolder + "bk.gif";
	private static final String bKnightImg = imgsFolder + "bn.gif";

	private static final String wPawnImg = imgsFolder + "wp.gif";
	private static final String wRookImg = imgsFolder + "wr.gif";
	private static final String wBishopImg = imgsFolder + "wb.gif";
	private static final String wQueenImg = imgsFolder + "wq.gif";
	private static final String wKingImg = imgsFolder + "wk.gif";
	private static final String wKnightImg = imgsFolder + "wn.gif";


	private static final String[] whitePawnPromotionOptionsImgs = {wQueenImg, wKnightImg, wBishopImg, wRookImg};
	private static final String[] blackPawnPromotionOptionsImgs = {bQueenImg, bKnightImg, bBishopImg, bRookImg};


	private String moveListAddition;
	private Vector<Move> moves;

//	private List<Piece> deadWhitePieces = new ArrayList<Piece>();
//	private List<Piece> deadBlackPieces = new ArrayList<Piece>();
	
	
	private int[][] whiteDeadPiecesTally = new int[2][3];
	private int[][] blackDeadPiecesTally = new int[2][3];

	private int whiteDeadPiecesTallySum;
	private int blackDeadPiecesTallySum;
	
	private boolean killTakingPlace;

	private final int timerInitialValue = 300;
	private int timerInSeconds = timerInitialValue;
	
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
		initializeDeadPiecePanes();
		initializeTimerPanes();
		
		chessGridMouseClickEvents(board);
		movesList.getStyleClass().add("list");

		movesList.setItems(movesListStringEmpty);

		
		
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(1000),
		        ae -> refreshTimers()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		
		//		GameStart game = new GameStart();
		//		start(board);

		// prevents the user in selecting a cell in the table view
		movesList.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				mouseEvent.consume();
			}

		});
	}



	private void initializeTimerPanes() {

		String timerTextFontStyle = "-fx-font-size: 25;";
		Text whiteTimerText = null;
		Text blackTimerText = null;

		if(isWhiteTurn){
			blackTimerText = new Text("0:00");
			whiteTimerText = new Text("5:00");	
		} else {
			blackTimerText = new Text("5:00");
			whiteTimerText = new Text("0:00");	
		}
			
			
		blackTimerText.setFill(Color.WHITE);
		blackTimerText.setStyle(timerTextFontStyle);
		
		whiteTimerText.setFill(Color.WHITE);
		whiteTimerText.setStyle(timerTextFontStyle);
		
		blackTimerPane.setRight(blackTimerText);
		whiteTimerPane.setRight(whiteTimerText);

		refreshTimerPanes();

	}



	private void refreshTimerPanes(){
		Text blackText = new Text("Black");
		blackText.setFill(Color.WHITE);
		

		Text whiteText = new Text("White");
		whiteText.setFill(Color.WHITE);
		
		if(isWhiteTurn){
			whiteText.underlineProperty().setValue(true);
		} else {
			blackText.underlineProperty().setValue(true);
		}
		
		blackTimerPane.setTop(blackText);
		whiteTimerPane.setTop(whiteText);
		
	}


	private void refreshTimers() {		
		timerInSeconds--;

		String timerTextFontStyle = "-fx-font-size: 25;";
		Text whiteTimerText = null;
		Text blackTimerText = null;
		if(isWhiteTurn){
			whiteTimerText = new Text("" + timerInSeconds/60 + ":" + timerInSeconds % 60);
			
			whiteTimerText.setFill(Color.WHITE);
			whiteTimerText.setStyle(timerTextFontStyle);
			
			blackTimerText = new Text("0:00");
			blackTimerText.setFill(Color.WHITE);
			blackTimerText.setStyle(timerTextFontStyle);


		} else{
			blackTimerText = new Text("" + timerInSeconds/60 + ":" + timerInSeconds % 60);
			blackTimerText.setFill(Color.WHITE);
			blackTimerText.setStyle(timerTextFontStyle);
			
			whiteTimerText = new Text("0:00");
			whiteTimerText.setFill(Color.WHITE);
			whiteTimerText.setStyle(timerTextFontStyle);
		}

		blackTimerPane.setRight(blackTimerText);
		whiteTimerPane.setRight(whiteTimerText);
	}



	private void initializeDeadPiecePanes() {

		
		final double spacing = 37.5; // TINKER with this one. 
		final double size = 30;
		final double deadOpacity = 0.25;
		final double aliveOpacity = 1;
		final String[] whitePieces = {wKingImg, wQueenImg, wRookImg, wPawnImg, wBishopImg, wKnightImg};
		final String[] blackPieces = {bKingImg, bQueenImg, bRookImg, bPawnImg, bBishopImg, bKnightImg};
		
		

		GridPane whiteDeadPieces = new GridPane();
		GridPane blackDeadPieces = new GridPane();
		
		Insets x = new Insets(0, 15, 0, 15);
		
		whiteDeadPieces.setPadding(x);
		blackDeadPieces.setPadding(x);
		
		
		ColumnConstraints column1 = new ColumnConstraints();
	     column1.setPercentWidth(spacing);
	     ColumnConstraints column2 = new ColumnConstraints();
	     column2.setPercentWidth(spacing);
	     
	     
	     whiteDeadPieces.getColumnConstraints().addAll(column1, column2);
	     blackDeadPieces.getColumnConstraints().addAll(column1, column2);
	     
	     
		Text t = new Text("Dead white pieces: " + whiteDeadPiecesTallySum);
		t.setFill(Color.WHITE);
		whitePiecesPane.setTop(t);
		
		int i = 0;
		ImageView w,b;
		Text tally;
		
		for(int r = 0; r < 2; r++){
			for(int c = 0; c < 3; c++){

				w = new ImageView(new Image(whitePieces[i]));
				w.setFitHeight(size);
				w.setFitWidth(size);
				
				if(whiteDeadPiecesTally[r][c] > 0){
					w.setStyle("-fx-opacity: " + aliveOpacity + ";");

				} else {
					w.setStyle("-fx-opacity: " + deadOpacity + ";");
				}
				
				tally = new Text("");
//				tally.setText("   x");
				tally.setText("   x" + whiteDeadPiecesTally[r][c]);

				whiteDeadPieces.add(w, c, r);
				whiteDeadPieces.add(tally, c, r);
				
				

				b = new ImageView(new Image(blackPieces[i]));
				b.setFitHeight(size);
				b.setFitWidth(size);
				if(blackDeadPiecesTally[r][c] > 0){
					b.setStyle("-fx-opacity: " + aliveOpacity + ";");

				} else {
					b.setStyle("-fx-opacity: " + deadOpacity + ";");
				}
				

				tally = new Text("");
//				tally.setText("   x0");
				tally.setText("   x" + blackDeadPiecesTally[r][c]);
				
				
				blackDeadPieces.add(b, c, r);
				blackDeadPieces.add(tally, c, r);
				
				i++;
			}
		}


		whitePiecesPane.setCenter(whiteDeadPieces);
		blackPiecesPane.setCenter(blackDeadPieces);




		Text bt = new Text("Dead black pieces: " + blackDeadPiecesTallySum);
		bt.setFill(Color.WHITE);
		blackPiecesPane.setTop(bt);




		Text n = new Text("Dead black pieces:");
		bt.setFill(Color.WHITE);
		blackPiecesPane.setTop(bt);
		//blackDeadPieces.add(bt, 0, 0);

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
					color = "linear-gradient(to bottom right, grey, silver)";
				}
				square.getStyleClass().add("panel");
				square.setStyle("-fx-background-color: " + color + ";");
				chessGrid.add(square, col, row);

				Image piece = getPiece(row, col, board);
				ImageView imagePiece = new ImageView(piece);

				imagePiece.setFitHeight(squareSize);
				imagePiece.setFitWidth(squareSize);
				chessGrid.add(imagePiece, col, row);
			}
		}
		initializeDeadPiecePanes();
		refreshTimerPanes();
		

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
					
					killTakingPlace = false;
					
//					Piece squareBeingMovedTo = new Piece((byte) 0, false);
					Piece squareBeingMovedTo = board.board[yCoord][xCoord];
//					squareBeingMovedTo = board.board[yCoord][xCoord];
					
					if(squareBeingMovedTo != null){
						killTakingPlace = true;
					}
					
					
					
					
					moves = board.getMoves();
					byte moveCode = Board.movePiece(yCoord, xCoord, selectedPieceY, selectedPieceX, moves);
					

					boolean whitePawnPromotion = moveCode == 2;
					boolean blackPawnPromotion = moveCode == 3;
					boolean pawnPromotion = whitePawnPromotion || blackPawnPromotion;
					
					if(moveCode > 0){ // the piece moved successfully

						if(!pawnPromotion){
							if(killTakingPlace){
								if(squareBeingMovedTo.isWhite){
									System.out.println("WHITE DIED: " + squareBeingMovedTo.type );
									//								deadWhitePieces.add(squareBeingMovedTo);
									whiteDeadPiecesTallySum++;

								}
								else{
									System.out.println("BLACK DIED: " + squareBeingMovedTo.type );
									//								deadBlackPieces.add(squareBeingMovedTo);
									blackDeadPiecesTallySum++;
								}
								tallyDeadPieces(squareBeingMovedTo);
							}
						}
						
						// get the type of turn it is
						String whoseTurn = null;
						if(isWhiteTurn)
							whoseTurn = "W";
						else
							whoseTurn = "B";

						// determine the string of the piece that has moved (ex. board[0][0] = a7)
						Point p1 = new Point(selectedPieceY, selectedPieceX);

						String boardPiece;
						if(pawnPromotion)
							boardPiece = getBoardPieceLetter(selectedPieceY, selectedPieceX);
						else
							boardPiece = getBoardPieceLetter(row, col);
						
						
						Point p = new Point(row, col);

						// determine string format that will be place in the list view
//						String moveListAddition = null;
						moveListAddition = null;
						
							if(counter < 10) 
								moveListAddition = String.format("%s. %6s %s-%s    %s", counter, boardPiece,Point.convertPointToString(p1),Point.convertPointToString(p),whoseTurn); 
							else
								moveListAddition = String.format("%s.%6s %s-%s    %s", counter, boardPiece,Point.convertPointToString(p1),Point.convertPointToString(p),whoseTurn); 
						
						// add the created string into the list and insert it into the list view
							if(!pawnPromotion)
								movesListString.add(moveListAddition);

						
							movesList.setItems(movesListString);
						

						// increment counter for next list view slot
						counter++;




						final double pawnPromotionSquareSize = squareSize/1.5;	

						if(pawnPromotion){

							PopOver pawnPromotionPopOver = new PopOver();
							BorderPane promotionPane = new BorderPane();
							GridPane promotionOptions = new GridPane();
							promotionPane.setOpacity(1);
							promotionPane.setPrefHeight(pawnPromotionSquareSize);
							promotionPane.setPrefWidth(pawnPromotionSquareSize*4);
							final String css = "-fx-border-color: black;\n"
									+ "-fx-border-insets: 3;\n"
									+ "-fx-border-width: 3;\n"
									+ "-fx-border-style: dotted;\n"
									+ "-fx-background-color: grey;";
							promotionPane.setStyle(css);
							promotionPane.setCenter(promotionOptions);
							pawnPromotionPopOver.setContentNode(promotionPane);

							
							pawnPromotionPopOver.setStyle("-fx-effect: null;");
							


							String[] promotionOptionsImgs = null;
							if(whitePawnPromotion)
								promotionOptionsImgs = whitePawnPromotionOptionsImgs;
							if(blackPawnPromotion)
								promotionOptionsImgs = blackPawnPromotionOptionsImgs;


							for(int i = 0; i < promotionOptionsImgs.length; i++){
								Image piece = new Image(promotionOptionsImgs[i]);
								ImageView imagePiece = new ImageView(piece);
								imagePiece.setFitHeight(pawnPromotionSquareSize);
								imagePiece.setFitWidth(pawnPromotionSquareSize);
								promotionOptions.add(imagePiece, i, 0);
							}

							pawnPromotionPopOver.show(findNode(col, row));

							pawnPromotionPopOver.setDetachable(false);
							promotionOptions.setOnMouseClicked(new EventHandler<MouseEvent>() {
								public void handle(MouseEvent e) {
									int c = (int)(e.getX()/pawnPromotionSquareSize);
									
									switch(c){
									case 0:
										// spawn a white queen
										board.board[row][col] = (new Piece(QUEEN, isWhiteTurn));
										break;
									case 1:
										//knight
										board.board[row][col] = (new Piece(KNIGHT, isWhiteTurn));
										break;
									case 2:
										// bishop
										board.board[row][col] = (new Piece(BISHOP, isWhiteTurn));
										break;
									case 3:
										// rook
										board.board[row][col] = (new Piece(ROOK, isWhiteTurn));
										break;
									}
									
									///////


									
									if(killTakingPlace){
										if(squareBeingMovedTo.isWhite){
											System.out.println("WHITE DIED: " + squareBeingMovedTo.type );
											//								deadWhitePieces.add(squareBeingMovedTo);
											whiteDeadPiecesTallySum++;

										}
										else{
											System.out.println("BLACK DIED: " + squareBeingMovedTo.type );
											//								deadBlackPieces.add(squareBeingMovedTo);
											blackDeadPiecesTallySum++;
										}
										tallyDeadPieces(squareBeingMovedTo);
									}
									
									
									movesListString.add(moveListAddition);							
								movesList.setItems(movesListString);
									
									board.board[selectedPieceY][selectedPieceX] = null;
									refreshBoard(board);
									pawnPromotionPopOver.hide();
									isWhiteTurn = !isWhiteTurn;
									timerInSeconds = timerInitialValue;
									initializeTimerPanes();
									selectingPiece = true;
									printWhoseTurn();
									return;
								}
							});
						}





						if(!pawnPromotion){
							selectingPiece = true;
							isWhiteTurn = !isWhiteTurn;
							timerInSeconds = timerInitialValue;
							initializeTimerPanes();
							refreshBoard(board);
							printWhoseTurn();
						}
					} else {
						// if piece can't move to square that was clicked on...
						selectingPiece = true;
						refreshBoard(board);
						printWhoseTurn();
					}
				}
			}




			private Node findNode(int col, int row){
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
				return results;
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
					piece = new Image(wPawnImg);
					break;
				case ROOK:
					piece = new Image(wRookImg);
					break;
				case BISHOP:
					piece = new Image(wBishopImg);
					break;
				case QUEEN:
					piece = new Image(wQueenImg);
					break;
				case KING:
					piece = new Image(wKingImg);
					break;
				case KNIGHT:
					piece = new Image(wKnightImg);
					break;
				}

			} else { // if the piece is black

				switch(board.board[row][col].type){
				case PAWN:
					piece = new Image(bPawnImg);
					break;
				case ROOK:
					piece = new Image(bRookImg);
					break;
				case BISHOP:
					piece = new Image(bBishopImg);
					break;
				case QUEEN:
					piece = new Image(bQueenImg);
					break;
				case KING:
					piece = new Image(bKingImg);
					break;
				case KNIGHT:
					piece = new Image(bKnightImg);
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

		if(Board.board[y][x] != null){

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
		}
		return piece;
	}
	
	private void addToDeadPieces(int col, int row, Piece piece){
		if(piece.isWhite)
			whiteDeadPiecesTally[row][col]++;
		else
			blackDeadPiecesTally[row][col]++;
	}

	private void tallyDeadPieces(Piece piece){
		byte type = piece.type;
		switch(type){
		case PAWN:
			addToDeadPieces(0, 1, piece);
			break;
		case ROOK:
			addToDeadPieces(2, 0, piece);
			break;
		case BISHOP:
			addToDeadPieces(1, 1, piece);
			break;
		case QUEEN:
			addToDeadPieces(1, 0, piece);
			break;
		case KING:
			addToDeadPieces(0, 0, piece);
			break;
		case KNIGHT:
			addToDeadPieces(2, 1, piece);
			break;
		}
	}


}

