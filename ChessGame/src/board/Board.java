package board;

import java.util.Scanner;
import java.util.Vector;

import constants.Constants;
import units.Piece;

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
		board[4][2] = new Piece(ROOK, false);
	
	}
	
	// print out the board
	public void printBoard() {
		Vector test;
		//Board test1 = new Board();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null)
					System.out.print(board[i][j].type + " ");
				else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
	// put this into another class later - one for algorithms
	// pawn moves algorithm
	private Vector pawn(int y, int x) {
		Vector moves = new Vector(); // store the possible moves in here
		
		// for white pieces moving
		if(board[y][x].isWhite) {
			if(board[y-1][x] == null) {  // check if the piece above it is free
				if(y == 6) { // if it is in the starting position, can move 1 or 2 spaces
					moves.add(new Move(y,x,y-2,x));
					moves.add(new Move(y,x,y-1,x));
				}
				else // else only move one
					moves.add(new Move(y,x,y-1,x));
			}
		}
		else { // for black pieces moving
			if(board[y+1][x] == null) { // check if the piece above it is free
				if(y == 1) { // if it is in the starting position, can move 1 or 2 spaces
					moves.add(new Move(y,x,y+2,x));
					moves.add(new Move(y,x,y+1,x));
				}
				else // else only move one
					moves.add(new Move(y,x,y+1,x));
			}
		}
		
		// for white pieces attacking
		if(board[y][x].isWhite) {
			if((x+1) <= 7 ) { // for all pieces up until the end of the board, check upper right
				if(board[y-1][x+1] != null && !board[y-1][x+1].isWhite) // check if space is occupied by black
					moves.add(new Move(y,x,y-1,x-1));
			}
			if((x-1) >= 0 ) { // for all pieces up until the end of the board, check upper left
				if(board[y-1][x-1] != null && !board[y-1][x-1].isWhite) // check if space is occupied by black
				moves.add(new Move(y,x,y-1,x+1));
			}
		}
		else { // for black pieces attacking
			if((x+1) <= 7 ) { // for all pieces up until the end of the board, check upper right
				if(board[y+1][x+1] != null && board[y+1][x+1].isWhite) // check if space is occupied by white
					moves.add(new Move(y,x,y-1,x+1));
			}
			if((x-1) >= 0 ) { // for all pieces up until the end of the board, check upper left
				if(board[y+1][x-1] != null && board[y+1][x-1].isWhite) // check if space is occupied by white
				moves.add(new Move(y,x,y-1,x-1));
			}
		}
		return moves;
	}
	
	// rook moves algorithm
	private Vector rook(int y, int x) {
		Vector moves = new Vector(); // store the possible moves in here
		int counter = 1;
		
		// if - for white pieces moving
		// else - for black pieces moving
		
		// top
		if(board[y][x].isWhite) {
			while((y-counter) >= 0) {
				if(board[y-counter][x] != null && board[y-counter][x].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y-counter,x)); // add all moves 
				if(board[y-counter][x] != null && !board[y-counter][x].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((y-counter) >= 0) {
				if(board[y-counter][x] != null && !board[y-counter][x].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y-counter,x)); // add all moves 
				if(board[y-counter][x] != null && board[y-counter][x].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// bottom
		counter = 1;
		if(board[y][x].isWhite) {
			while((y+counter) <= 7) {
				if(board[y+counter][x] != null && board[y+counter][x].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y+counter,x)); // add all moves 
				if(board[y+counter][x] != null && !board[y+counter][x].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((y+counter) <= 7) {
				if(board[y+counter][x] != null && !board[y+counter][x].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y+counter,x)); // add all moves 
				if(board[y+counter][x] != null && board[y+counter][x].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// right
		counter = 1;
		if(board[y][x].isWhite) {
			while((x+counter) <= 7) {
				if(board[y][x+counter] != null && board[y][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y,x+counter)); // add all moves 
				if(board[y][x+counter] != null && !board[y][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((x+counter) <= 7) {
				if(board[y][x+counter] != null && !board[y][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y,x+counter)); // add all moves 
				if(board[y][x+counter] != null && board[y][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// left
		counter = 1;
		if(board[y][x].isWhite) {
			while((x-counter) >= 0) {
				if(board[y][x-counter] != null && board[y][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y,x-counter)); // add all moves 
				if(board[y][x-counter] != null && !board[y][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((x-counter) >= 0) {
				if(board[y][x-counter] != null && board[y][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				moves.add(new Move(y,x,y,x-counter)); // add all moves 
				if(board[y][x-counter] != null && !board[y][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		return moves;
	}
	
	// this is pretty useless
	public Vector moves(int y, int x) {
		
		Vector moves = new Vector();
		
		moves = pawn(y,x);
		
		System.out.println(moves);
		
		return moves;
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
		
		// probably use a switch statement
//		if(board[y][x].type == PAWN) {
//			moves = pawn(y,x);
//		}
		if(board[y][x].type == ROOK) {
			moves = rook(y,x);
		}
		
		// print out the pieces possile moves
		System.out.print("Options: ");
		for( int i = 0; i < moves.size(); i++) {
			mm = (Move) moves.elementAt(i);
			if(moves.size()-1 == i) {
				System.out.print(mm.y2 + "," + mm.x2 + " " );
				//System.out.print(mm.toString());
			}
			else {
				System.out.print(mm.y2 + "," + mm.x2 + " | " );
				//System.out.print(mm.toString());
			}
		} 
		
		while(!valid) {
			// get the position of the user's input in the form of y and then x
			// NOTE * can swap these to make it in the format of x and y
			System.out.print("\nMove to: ");
			yCoord = user_input.nextInt();
			xCoord = user_input.nextInt();
			
			// find if that move the user is requesting is part of the possible moves
			for( int i = 0; i < moves.size(); i++) {
				mm = (Move) moves.elementAt(i);
				if(xCoord == mm.x2 && yCoord == mm.y2) {
					valid = true;
					validInt = i;
					break;
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
}
