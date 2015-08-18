package board;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import main.Main;
import constants.Constants;
import units.Piece;
import units.PieceAlgorithms;

public class Board implements Constants {

	public Piece[][]board; // the board

	public byte x1;
	public byte y1;
	public byte x2;
	public byte y2;
	
	public int type;
	
	public Board() {
		
		board = new Piece[8][8]; // initialize the size
		
		// set both the white and black pawns
		for(int i = 0; i < 8; i++) {
			board[1][i] = new Piece(PAWN,false); 	// for the black side
			board[6][i] = new Piece(PAWN,true); 	// for the white side 
		}
		
		// black pieces
		board[0][0] = board[0][7] = new Piece(ROOK, false);
		board[0][1] = board[0][6] = new Piece(KNIGHT, false);
		board[0][2] = board[0][5] = new Piece(BISHOP, false);
		board[0][3] = new Piece(QUEEN, false);
		board[0][4] = new Piece(KING, false);
		
		// white pieces
		board[7][0] = board[7][7] = new Piece(ROOK, true);
		board[7][1] = board[7][6] = new Piece(KNIGHT, true);
		board[7][2] = board[7][5] = new Piece(BISHOP, true);
		board[7][3] = new Piece(QUEEN, true);
		board[7][4] = new Piece(KING, true);
		
		// delete after, testing purposes
		board[1][2] = new Piece(PAWN, true);
		//board[6][2] = new Piece(PAWN, false);
	
	}
	
	// print out the board
	public void printBoard() {
		Vector test;
		
		System.out.println("   a b c d e f g h");
		System.out.println("  -----------------");
		
		for(int i = 0; i < 8; i++) {
			System.out.print(8-i + "| ");
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null) 
					getBoardPiece(i,j);
				else 
					System.out.print("- ");
			}
			System.out.println("|");
		}
		System.out.println("  -----------------");
	}
	
	public void movePiece(int y, int x) {
		
		if(board[y][x] == null) {
			return;
		}
		Scanner user_input = new Scanner(System.in);
		Vector moves = new Vector();
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
		
		while(!valid) {
			// get the position of the user's input in the form of y and then x
			// NOTE * can swap these to make it in the format of x and y
			System.out.print("\nMove to: ");
			

	    	String input =  user_input.nextLine();	
			Point point = Point.convertStringToPoint(input);
	    	yCoord = point.getY();
	    	xCoord = point.getX();
			
			
//			yCoord = user_input.nextInt();
//			xCoord = user_input.nextInt();
	    	
	    	
			// find if that move the user is requesting is part of the possible moves
			for( int i = 0; i < moves.size(); i++) {
				mm = (Move) moves.elementAt(i);
				if(xCoord == mm.x2 && yCoord == mm.y2) {
					valid = true;
					validInt = i;
					break;
				}
			}
			
			// MAKE SURE IT IS A PAWN
			// pawn promotion
			if(board[y][x].isWhite && board[y][x].type == PAWN) {
				if(yCoord == 0) {
					System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
					System.out.print(":");
					input =  user_input.nextLine();
					while(stringToByte(input) == -1) {
						System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
						System.out.print(":");
						input =  user_input.nextLine();
					}
					board[y][x].type = stringToByte(input);
				}
			}
			else if (!board[y][x].isWhite && board[y][x].type == PAWN) {
				if(yCoord == 7) {
					System.out.println("PAWN PROMOTION: Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
					System.out.print(":");
					input =  user_input.nextLine();
					while(stringToByte(input) == -1) {
						System.out.println("INVALID! Type [QUEEN] [KNIGHT] [ROOK] [BISHOP]");
						System.out.print(":");
						input =  user_input.nextLine();
					}
					board[y][x].type = stringToByte(input);
				}
			}
			// if not, display an error message and let them try again
			if(!valid)
				System.out.println("Invalid!");
		}
		if(valid) {
			// if the move is in the set of possible moves, perform that move
			mm = (Move) moves.elementAt(validInt);
			mm.movePiece(board);
		}
		
		printBoard();
	}
	
	private void getBoardPiece(int y, int x) {
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
}
