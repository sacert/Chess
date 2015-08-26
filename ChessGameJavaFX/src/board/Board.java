package board;

import gameStart.GameStart;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import constants.Constants;
import units.Piece;
import units.PieceAlgorithms;

public class Board implements Constants {

	public static Piece[][]board; // the board
	public static boolean checkTrueWhite = false;
	public static boolean checkTrueBlack = false;
	private static final String undoPieceSelectionPrompt = "undo";

	public byte x1;
	public byte y1;
	public byte x2;
	public byte y2;
	
	public int type;

	public List<Piece> whitePieces = new ArrayList<Piece>();
	public List<Piece> blackPieces = new ArrayList<Piece>();
	
	
	private Vector<Move> moves;
	
	
	public Board() {

		
		moves = new Vector<Move>();
		
		
		board = new Piece[8][8]; // initialize the size
		
		// set both the white and black pawns
		for(int i = 0; i < 8; i++) {
			whitePieces.add(new Piece(PAWN,false));
			board[1][i] = whitePieces.get(whitePieces.size()-1); 	// for the black side
			
			blackPieces.add(new Piece(PAWN,true));
			board[6][i] = blackPieces.get(blackPieces.size()-1); 	// for the black side
		}
		
		// black pieces
		blackPieces.add(new Piece(ROOK, false));
		board[0][0] = blackPieces.get(blackPieces.size()-1);
		
		blackPieces.add(new Piece(ROOK, false));
		board[0][7] = blackPieces.get(blackPieces.size()-1);
	
		blackPieces.add(new Piece(KNIGHT, false));
		board[0][1] = blackPieces.get(blackPieces.size()-1);
		
		blackPieces.add(new Piece(KNIGHT, false));
		board[0][6] = blackPieces.get(blackPieces.size()-1);

		blackPieces.add(new Piece(BISHOP, false));
		board[0][2] = blackPieces.get(blackPieces.size()-1);
		
		blackPieces.add(new Piece(BISHOP, false));
		board[0][5] = blackPieces.get(blackPieces.size()-1);

		blackPieces.add(new Piece(QUEEN, false));
		board[0][3] = blackPieces.get(blackPieces.size()-1);
		
		blackPieces.add(new Piece(KING, false));
		board[0][4] = blackPieces.get(blackPieces.size()-1);
		
		
		// white pieces
		whitePieces.add(new Piece(ROOK, true));
		board[7][0] = whitePieces.get(whitePieces.size()-1);

		whitePieces.add(new Piece(ROOK, true));
		board[7][7] = whitePieces.get(whitePieces.size()-1);

		whitePieces.add(new Piece(KNIGHT, true));
		board[7][1] = whitePieces.get(whitePieces.size()-1);
		
		whitePieces.add(new Piece(KNIGHT, true));
		board[7][6] = whitePieces.get(whitePieces.size()-1);
		
		whitePieces.add(new Piece(BISHOP, true));
		board[7][2] = whitePieces.get(whitePieces.size()-1);
	
		whitePieces.add(new Piece(BISHOP, true));
		board[7][5] = whitePieces.get(whitePieces.size()-1);
		
		whitePieces.add(new Piece(QUEEN, true));
		board[7][3] = whitePieces.get(whitePieces.size()-1);
		
		whitePieces.add(new Piece(KING, true));
		board[7][4] = whitePieces.get(whitePieces.size()-1);
		
		// delete after, testing purposes
//		//board[3][5] = new Piece(KING, false);
//		//board[3][5] = new Piece(ROOK, true);
//		board[5][1] = new Piece(KING, false);
//		//board[5][1] = new Piece(QUEEN, true);
//		board[4][5] = new Piece(ROOK, false);
//		//board[2][2] = new Piece(BISHOP, false);
	
	}
	
	// print out the board
	public void printBoard() {
		//System.out.println("isCheck = " + isCheck());
		Vector test;
		
		System.out.println("   a b c d e f g h");
		System.out.println("  -----------------");
		
		for(int i = 0; i < 8; i++) {
			System.out.print(8-i + "| ");
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null) 
					getBoardPiece(i,j,board);
				else 
					System.out.print("- ");
			}
			System.out.println("|");
		}
		System.out.println("  -----------------");
	}
	
	public Boolean selectPiece(int y, int x) {
		if(board[y][x] == null) {
			return false;
		}
		Scanner user_input = new Scanner(System.in);
		moves = new Vector<Move>();
		int xCoord;
		int yCoord;
		boolean valid = false;
		int validInt = 0;
		Move mm;
			
		switch(board[y][x].type){
		case PAWN:
			moves = PieceAlgorithms.pawn(board,y,x);
			break;
		case ROOK:
			moves = PieceAlgorithms.rook(board,y,x);
			break;
		case BISHOP:
			moves = PieceAlgorithms.bishop(board,y,x);
			break;
		case QUEEN:
			moves = PieceAlgorithms.queen(board,y,x);
			break;
		case KING:
			moves = PieceAlgorithms.king(board,y,x);
			break;
		case KNIGHT:
			moves = PieceAlgorithms.knight(board,y,x);
			break;
		}
		
		if(moves.isEmpty()){
			// doesn't do anything in regards to turns. maybe it should?
			System.out.println("     Nowhere to move.");
			return false;
		}
		
		return true;
	}

	public Vector<Move> getMoves() {
		Move mm;
		
		// print out the piece's possible moves
		System.out.print("Options: ");
		for( int i = 0; i < moves.size(); i++) {
			mm = (Move) moves.elementAt(i);
			if(moves.size()-1 == i) {
				Point p = new Point(mm.y2, mm.x2);
				String point = Point.convertPointToString(p);
				System.out.print(point + " " );
				
				
//				System.out.print(mm.y2 + "," + mm.x2 + " " );
				//System.out.print(mm.toString());
			}
			else {
				Point p = new Point(mm.y2, mm.x2);
				String point = Point.convertPointToString(p);
				System.out.print(point + " | " );
				
//				System.out.print(mm.y2 + "," + mm.x2 + " | " );
				//System.out.print(mm.toString());
			}
		} 

		System.out.print(" | " + undoPieceSelectionPrompt + "\n");
		return moves;
	}
	
	
	public static boolean movePiece(int yCoord, int xCoord, int selectedPieceY, int selectedPieceX, Vector moves){
		boolean valid = false;
		int validInt = 0;
		
		Move mm;
		
		// is the square we're trying to move to within the valid set of moves?
		for( int i = 0; i < moves.size(); i++) {
			mm = (Move) moves.elementAt(i);
			if(xCoord == mm.x2 && yCoord == mm.y2) {
				valid = true;
				validInt = i;
				break;
			}
		}
		
		if(!valid){
			return false;
		}
		
		int y = selectedPieceY;
		int x = selectedPieceX;
		// if the move is in the set of possible moves, perform that move
		
//		// pawn promotion
//		if(board[y][x].isWhite && board[y][x].type == PAWN) {
//			if(yCoord == 0) {
//				System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//				System.out.print(":");
//				input =  user_input.nextLine();
//				while(stringToByte(input) == -1) {
//					System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//					System.out.print(":");
//					input =  user_input.nextLine();
//				}
//				board[y][x].type = stringToByte(input);
//			}
//		}
//		else if (!board[y][x].isWhite && board[y][x].type == PAWN) {
//			if(yCoord == 7) {
//				System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//				System.out.print(":");
//				input =  user_input.nextLine();
//				while(stringToByte(input) == -1) {
//					System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
//					System.out.print(":");
//					input =  user_input.nextLine();
//				}
//				board[y][x].type = stringToByte(input);
//			}
//		}
		
		// NOTE* MAY BE AN ISSUE WITH THIS
		if(board[y][x] != null) {
			board[y][x].canCastle = false; // a piece that moves has zero chance of castling. eg a King that moved from its orig position
		}
		mm = (Move) moves.elementAt(validInt);


		// if a castling move
		if(board[y][x] != null){
			if(board[y][x].type == KING){

				// white King
				boolean whiteKingIsInOriginalPosition = y == 7 && x == 4;
				if(board[y][x].isWhite && whiteKingIsInOriginalPosition){

					// Move left rook to appropriate spot
					if(mm.y2 == 7 & mm.x2 == 2){
						board[7][3] = board[7][0];
						board[7][0] = null;
					}

					// Move right rook to appropriate spot
					if(mm.y2 == 7 & mm.x2 == 6){
						board[7][5] = board[7][7];
						board[7][7] = null;

					}
				}

				// black King
				boolean blackKingIsInOriginalPosition = y == 0 && x == 4;
				if(!board[y][x].isWhite && blackKingIsInOriginalPosition){

					// Move left rook to appropriate spot
					if(mm.y2 == 0 & mm.x2 == 2){
						board[0][3] = board[0][0];
						board[0][0] = null;
					}

					// Move right rook to appropriate spot
					if(mm.y2 == 0 & mm.x2 == 6){
						board[0][5] = board[0][7];
						board[0][7] = null;

					}
				}



			}
		}


		mm.movePiece(board);


		return true;
	}

	
	
	
	
	
	
	
	
	private static void getBoardPiece(int y, int x, Piece[][] board) {
		// black pieces
		if(!board[y][x].isWhite) {
			switch(board[y][x].type){
			case PAWN:
				System.out.print("p ");
				break;
			case ROOK:
				System.out.print("r ");
				break;
			case BISHOP:
				System.out.print("b ");
				break;
			case QUEEN:
				System.out.print("q ");
				break;
			case KING:
				System.out.print("k ");
				break;
			case KNIGHT:
				System.out.print("n ");
				break;
			}
		} else { // white pieces
			switch(board[y][x].type){
			case PAWN:
				System.out.print("P ");
				break;
			case ROOK:
				System.out.print("R ");
				break;
			case BISHOP:
				System.out.print("B ");
				break;
			case QUEEN:
				System.out.print("Q ");
				break;
			case KING:
				System.out.print("K ");
				break;
			case KNIGHT:
				System.out.print("N ");
				break;
			}
		}
	}
	
	// converts a string to a byte for the chess pieces
	byte stringToByte(String input) {
		byte value = -1; // set byte variable
		switch(input){ // switch statement to for each string
		case "PAWN":
			value = 0;
			break;
		case "ROOK":
			value = 1;
			break;
		case "BISHOP":
			value = 3;
			break;
		case "QUEEN":
			value = 4;
			break;
		case "KING":
			value = 5;
			break;
		case "KNIGHT":
			value = 2;
			break;
		}
		return value;
	}
	
	// determine whether the king is in check
	public static boolean isCheckBlack() {
		GameStart getTurn = new GameStart();
		Vector moves = new Vector();
		Move mm;
		boolean check = false;
		
		//if(getTurn.isWhiteTurn == true) {
			//System.out.println("white");
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					// test
					if(board[i][j] != null && board[i][j].isWhite) {
						switch(board[i][j].type){
						case PAWN:
							moves = PieceAlgorithms.pawn(board,i,j);
							break;
						case ROOK:
							moves = PieceAlgorithms.rook(board,i,j);
							break;
						case BISHOP:
							moves = PieceAlgorithms.bishop(board,i,j);
							break;
						case QUEEN:
							moves = PieceAlgorithms.queen(board,i,j);
							break;
						case KING:
							moves = PieceAlgorithms.king(board,i,j);
							break;
						case KNIGHT:
							moves = PieceAlgorithms.knight(board,i,j);
							break;
						}
						
						// check if the possible moves are a king
						for( int k = 0; k < moves.size(); k++) {
							mm = (Move) moves.elementAt(k);
							//System.out.println(i + " " + j);
							if((board[mm.y2][mm.x2] != null && board[mm.y2][mm.x2].type == 5) && board[mm.y2][mm.x2].isWhite == false) {
								System.out.println("Black in check");
								System.out.println(i + " " + j);
								check = true;
							}
						}
					}
				}
			}
		//} else {
			
		//}
		return check;
	}
	
	public static boolean isCheckWhite() {
		GameStart getTurn = new GameStart();
		Vector moves = new Vector();
		Move mm;
		boolean check = false;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				// test
				if(board[i][j] != null && !board[i][j].isWhite) {
					switch(board[i][j].type){
					case PAWN:
						moves = PieceAlgorithms.pawn(board,i,j);
						break;
					case ROOK:
						moves = PieceAlgorithms.rook(board,i,j);
						break;
					case BISHOP:
						moves = PieceAlgorithms.bishop(board,i,j);
						break;
					case QUEEN:
						moves = PieceAlgorithms.queen(board,i,j);
						break;
					case KING:
						moves = PieceAlgorithms.king(board,i,j);
						break;
					case KNIGHT:
						moves = PieceAlgorithms.knight(board,i,j);
						break;
					}
					
					// check if the possible moves are a king
					for( int k = 0; k < moves.size(); k++) {
						mm = (Move) moves.elementAt(k);
						//System.out.println(mm.type);
						//System.out.println(i + " " + j);
						if((board[mm.y2][mm.x2] != null && board[mm.y2][mm.x2].type == 5) && board[mm.y2][mm.x2].isWhite == true) {
							System.out.println("White in check");
							check = true;
						}
					}
				}
			}
		}
		
		return check;
	}
	
	// determine if the move will cause the player to be in check
	// output options that will remove the player from being in check
	public static boolean whiteCheck(int x1, int y1, int x2, int y2, Piece[][] board) {
		GameStart getTurn = new GameStart();
		Vector moves = new Vector();
		Move mm;
		boolean check = false;
		
		//System.out.println(Board.checkTrue);
		
		Piece[][] boardCopy = new Piece[8][8]; // play the copy of the board in here
		
		// copy the board
		for ( int k = 0; k < 8; k++) {
			for ( int l = 0; l < 8; l++) {
				if( board[k][l] != null) {
					boardCopy[k][l] = board[k][l];
				}
			}
		}
		
		// within the copied board, imitate as if the move had been selected
		boardCopy[x2][y2] = board[x1][y1];
		boardCopy[x1][y1] = null;
		
		// check if the move will cause the player to be in check
		if(getTurn.isWhiteTurn == true) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(boardCopy[i][j] != null && !boardCopy[i][j].isWhite) {
						switch(boardCopy[i][j].type){
						case PAWN:
							moves = PieceAlgorithms.pawn(boardCopy,i,j);
							break;
						case ROOK:
							moves = PieceAlgorithms.rook(boardCopy,i,j);
							break;
						case BISHOP:
							moves = PieceAlgorithms.bishop(boardCopy,i,j);
							break;
						case QUEEN:
							moves = PieceAlgorithms.queen(boardCopy,i,j);
							break;
						case KING:
							moves = PieceAlgorithms.king(boardCopy,i,j);
							break;
						case KNIGHT:
							moves = PieceAlgorithms.knight(boardCopy,i,j);
							break;
						}
						
						// check if the possible moves are a king
						for( int k = 0; k < moves.size(); k++) {
							mm = (Move) moves.elementAt(k);
							if(boardCopy[mm.y2][mm.x2] != null && boardCopy[mm.y2][mm.x2].type == 5) {
								check = true;
							}
						}
					}
				}
			}
		}
		
		return check;
	}
		
	// determine if the move will cause the player to be in check
	// output options that will remove the player from being in check
	public static boolean blackCheck(int x1, int y1, int x2, int y2, Piece[][] board) {
		GameStart getTurn = new GameStart();
		Vector moves = new Vector();
		Move mm;
		boolean check = false;
		
		Piece[][] boardCopy = new Piece[8][8]; // play the copy of the board in here
		
		// copy the board
		for ( int k = 0; k < 8; k++) {
			for ( int l = 0; l < 8; l++) {
				if( board[k][l] != null) {
					boardCopy[k][l] = board[k][l];
				}
			}
		}
		
		// within the copied board, imitate as if the move had been selected
		boardCopy[x2][y2] = board[x1][y1];
		boardCopy[x1][y1] = null;
		
		// check if the move will cause the player to be in check
		if(getTurn.isWhiteTurn == false) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(boardCopy[i][j] != null && boardCopy[i][j].isWhite) {
						switch(boardCopy[i][j].type){
						case PAWN:
							moves = PieceAlgorithms.pawn(boardCopy,i,j);
							break;
						case ROOK:
							moves = PieceAlgorithms.rook(boardCopy,i,j);
							break;
						case BISHOP:
							moves = PieceAlgorithms.bishop(boardCopy,i,j);
							break;
						case QUEEN:
							moves = PieceAlgorithms.queen(boardCopy,i,j);
							break;
						case KING:
							moves = PieceAlgorithms.king(boardCopy,i,j);
							break;
						case KNIGHT:
							moves = PieceAlgorithms.knight(boardCopy,i,j);
							break;
						}
						
						// check if the possible moves are a king
						for( int k = 0; k < moves.size(); k++) {
							mm = (Move) moves.elementAt(k);
							if(boardCopy[mm.y2][mm.x2] != null && boardCopy[mm.y2][mm.x2].type == 5) {
								check = true;
							}
						}
					}
				}
			}
		}
		
		return check;
	}
	
}
