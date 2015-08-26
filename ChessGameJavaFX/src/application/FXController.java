package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import board.Board;
import constants.Constants;


public class FXController implements Initializable, Constants {

	public static boolean isWhiteTurn = true;
	@FXML private GridPane chessGrid;

	private final int gridSize = 8;
	private double squareSize;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		squareSize = chessGrid.getPrefHeight()/gridSize;
		
		Board board = new Board();
		initializeBoard("white", "grey", board);
		chessGridMouseClickEvents();
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

				Image piece = getPiece(row, col,board);
				ImageView imagePiece = new ImageView(piece);

				imagePiece.setFitHeight(squareSize);
				imagePiece.setFitWidth(squareSize);
				chessGrid.add(imagePiece, col, row);
			}
		}
	}
	
	private void chessGridMouseClickEvents() {
		// *NOTE: Switch the "/100" as it is static, want to have to relative to the board size
		// determines the location of the board in terms of a 8x8 array
		chessGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				String column = "Column: " + (int)(e.getX()/squareSize);
				String row = "Row: " + (int)(e.getY()/squareSize);
				System.out.println(column + ", " + row);
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
}
