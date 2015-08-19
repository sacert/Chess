package units;

import java.util.Vector;

import board.Move;
import board.Board;

public class PieceAlgorithms {

	public PieceAlgorithms() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public static Vector<Move> king(Piece[][] board, int y, int x){
		Vector<Move> moves = new Vector<Move>();
		Boolean isMovingPieceWhite = board[y][x].isWhite;

		//  find all possible moves in the 3x3 square containing the King piece
		for(int c = y-1; c <= y+1; c++){
			for(int r = x-1; r <= x+1; r++){
				Boolean outOfBounds = false;
				if(c < 0 || r > 7){
					outOfBounds = true;
				}

				if(!(outOfBounds || (c == y && r == x))){ // if not the case that it's out of bounds or in the original position, then...
					if(board[c][r] == null){ // if empty, move there.
						moves.add(new Move(y,x,c,r));
					} else {
						Boolean isOtherPieceWhite = board[c][r].isWhite;
						if(isMovingPieceWhite != isOtherPieceWhite){ // if the space is occupied by an enemy, we can go there, otherwise we can't
							moves.add(new Move(y,x,c,r));
						}								
					}	
				}	
			}	
		}
		
		return moves;
	}
	

	// put this into another class later - one for algorithms
	// pawn moves algorithm
	public static Vector<Move> pawn(Piece[][] board, int y, int x){
		Vector<Move> moves = new Vector<Move>(); // store the possible moves in here
		// for white pieces moving
		if(board[y][x].isWhite) {
			if(board[y-1][x] == null) {  // check if the piece above it is free
				if(y == 6) { // if it is in the starting position, can move 1 or 2 spaces
						moves.add(new Move(y,x,y-2,x));
						moves.add(new Move(y,x,y-1,x));
				}
				else { // else only move one
					if(Board.checkTrue) {
						if(!Board.whiteCheck(y, x, y-1, x, board)) {
							moves.add(new Move(y,x,y-1,x));
						}
					} else {
						moves.add(new Move(y,x,y-1,x));
					}
				}
			}
		}
		else { // for black pieces moving
			if(board[y+1][x] == null) { // check if the piece above it is free
				if(y == 1) { // if it is in the starting position, can move 1 or 2 spaces
						moves.add(new Move(y,x,y+2,x));
						moves.add(new Move(y,x,y+1,x));
				}
				else { // else only move one
					if(Board.checkTrue) {
						if(!Board.blackCheck(y, x, y+1, x, board)) {
							moves.add(new Move(y,x,y+1,x));
						}
					} else {
						moves.add(new Move(y,x,y+1,x));
					}	
				}
			}
		}
		
		// for white pieces attacking
		if(board[y][x].isWhite) {
			if((x+1) <= 7 ) { // for all pieces up until the end of the board, check upper right
				if(board[y-1][x+1] != null && !board[y-1][x+1].isWhite) // check if space is occupied by black
					if(Board.checkTrue)	{
						if(!Board.whiteCheck(y, x, y-1, x+1, board)) {
							moves.add(new Move(y,x,y-1,x+1));
						}
					} else {
						moves.add(new Move(y,x,y-1,x+1));
					}
			}
			if((x-1) >= 0 ) { // for all pieces up until the end of the board, check upper left
				if(board[y-1][x-1] != null && !board[y-1][x-1].isWhite) // check if space is occupied by black
					if(Board.checkTrue)	{
						if(!Board.whiteCheck(y, x, y-1, x-1, board)){
							moves.add(new Move(y,x,y-1,x-1));
						}
					} else {
						moves.add(new Move(y,x,y-1,x-1));
					}
			}
		}
		else { // for black pieces attacking
			if((x+1) <= 7 ) { // for all pieces up until the end of the board, check upper right
				if(board[y+1][x+1] != null && board[y+1][x+1].isWhite) { // check if space is occupied by white
					if(Board.checkTrue) {
						if(!Board.blackCheck(y, x, y+1, x+1, board)) {
							moves.add(new Move(y,x,y+1,x+1));
						}
					} else {
						moves.add(new Move(y,x,y+1,x+1));
					}	
				}
			}
			if((x-1) >= 0 ) { // for all pieces up until the end of the board, check upper left
				if(board[y+1][x-1] != null && board[y+1][x-1].isWhite) { // check if space is occupied by white
					if(Board.checkTrue) {
						if(!Board.blackCheck(y, x, y+1, x-1, board)) {
							moves.add(new Move(y,x,y+1,x-1));
						}
					} else {
						moves.add(new Move(y,x,y+1,x-1));
					}	
				}
			}
		}
		return moves;
	}
	
	// rook moves algorithm
	public static Vector<Move> rook(Piece[][] board, int y, int x) {
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
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y-counter, x, board)) {
						moves.add(new Move(y,x,y-counter,x)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y-counter,x)); // add all moves
				} 
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
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y-counter, x, board)) {
						moves.add(new Move(y,x,y-counter,x)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y-counter,x)); // add all moves
				}  
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
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y+counter, x, board)) {
						moves.add(new Move(y,x,y+counter,x)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y+counter,x)); // add all moves
				}  
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
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y+counter, x, board)) {
						moves.add(new Move(y,x,y+counter,x)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y+counter,x)); // add all moves
				}  
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
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y, x+counter, board)) {
						moves.add(new Move(y,x,y,x+counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y,x+counter)); // add all moves
				}   
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
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y, x+counter, board)) {
						moves.add(new Move(y,x,y,x+counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y,x+counter)); // add all moves
				}   
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
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y, x-counter, board)) {
						moves.add(new Move(y,x,y,x-counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y,x-counter)); // add all moves
				}  
				if(board[y][x-counter] != null && !board[y][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((x-counter) >= 0) {
				if(board[y][x-counter] != null && !board[y][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y, x-counter, board)) {
						moves.add(new Move(y,x,y,x-counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y,x-counter)); // add all moves
				}  
				if(board[y][x-counter] != null && board[y][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		return moves;
	}
	
	// rook bishop algorithm
	public static Vector<Move> bishop(Piece[][] board, int y, int x) {
		Vector moves = new Vector(); // store the possible moves in here
		int counter = 1;
		
		// if - for white pieces moving
		// else - for black pieces moving
		
		// top left
		if(board[y][x].isWhite) {
			while((y-counter) >= 0 && (x-counter) >= 0) {
				if(board[y-counter][x-counter] != null && board[y-counter][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y-counter, x-counter, board)) {
						moves.add(new Move(y,x,y-counter,x-counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y-counter,x-counter)); // add all moves
				}  
				if(board[y-counter][x-counter] != null && !board[y-counter][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((y-counter) >= 0 && (x-counter) >= 0) {
				if(board[y-counter][x-counter] != null && !board[y-counter][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y-counter, x-counter, board)) {
						moves.add(new Move(y,x,y-counter,x-counter)); // add all moves
					}
				} else {
					moves.add(new Move(y,x,y-counter,x-counter)); // add all moves
				}  
				if(board[y-counter][x-counter] != null && board[y-counter][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// bottom right
		counter = 1;
		if(board[y][x].isWhite) {
			while((y+counter) <= 7 && (x+counter) <= 7) {
				if(board[y+counter][x+counter] != null && board[y+counter][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y+counter, x+counter, board)) {
						moves.add(new Move(y,x,y+counter,x+counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y+counter,x+counter)); // add all moves 
				} 
				if(board[y+counter][x+counter] != null && !board[y+counter][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((y+counter) <= 7 && (x+counter) <= 7) {
				if(board[y+counter][x+counter] != null && !board[y+counter][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y+counter, x+counter, board)) {
						moves.add(new Move(y,x,y+counter,x+counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y+counter,x+counter)); // add all moves 
				}
				if(board[y+counter][x+counter] != null && board[y+counter][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// top right
		counter = 1;
		if(board[y][x].isWhite) {
			while((y-counter) >= 0 && (x+counter) <= 7) {
				if(board[y-counter][x+counter] != null && board[y-counter][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y-counter, x+counter, board)) {
						moves.add(new Move(y,x,y-counter,x+counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y-counter,x+counter)); // add all moves 
				} 
				if(board[y-counter][x+counter] != null && !board[y-counter][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((y-counter) >= 0 && (x+counter) <= 7) {
				if(board[y-counter][x+counter] != null && !board[y-counter][x+counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y-counter, x+counter, board)) {
						moves.add(new Move(y,x,y-counter,x+counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y-counter,x+counter)); // add all moves 
				} 
				if(board[y-counter][x+counter] != null && board[y-counter][x+counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		// bottom left
		counter = 1;
		if(board[y][x].isWhite) {
			while((x-counter) >= 0 && (y+counter) <= 7) {
				if(board[y+counter][x-counter] != null && board[y+counter][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.whiteCheck(y, x, y+counter, x-counter, board)) {
						moves.add(new Move(y,x,y+counter,x-counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y+counter,x-counter)); // add all moves  
				} 
				if(board[y+counter][x-counter] != null && !board[y+counter][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				counter++;
			}
		} else { // for black pieces moving
			counter = 1;
			while((x-counter) >= 0 && (y+counter) <= 7) {
				if(board[y+counter][x-counter] != null && !board[y+counter][x-counter].isWhite) {  // if it gets to a black piece, leave the while loop
					break;
				}
				if(Board.checkTrue)	{
					if(!Board.blackCheck(y, x, y+counter, x-counter, board)) {
						moves.add(new Move(y,x,y+counter,x-counter)); // add all moves 
					}
				} else {
					moves.add(new Move(y,x,y+counter,x-counter)); // add all moves  
				} 
				if(board[y+counter][x-counter] != null && board[y+counter][x-counter].isWhite) {  // if it gets to a white piece, leave the while loop
					break;
				}
				counter++;
			}
		}
		
		return moves;
	}
	
	// pawn queen algorithm
	public static Vector<Move> queen(Piece[][] board, int y, int x) {
		
		// queen is just the combination of rook and bishop moves
		
		Vector moves = new Vector(); // store the possible moves in here
		Vector bishop = new Vector(); // store bishop moves
		Vector rook = new Vector(); // store rook moves
		
		bishop = bishop(board, y,x);
		rook = rook(board, y,x);
		
		// add the moves from bishop to the queens moves
		for(int i = 0; i < bishop.size(); i++) {
			moves.add(bishop.elementAt(i));
		}
		// add the moves from rook to the queens moves
		for(int i = 0; i < rook.size(); i++) {
			moves.add(rook.elementAt(i));
		}
		
		return moves;
	}
	
	// knight moves algorithm
	public static Vector<Move> knight(Piece[][] board, int y, int x) {
		Vector moves = new Vector(); // store the possible moves in here
		

		
		// for white pieces moving
		if(board[y][x].isWhite) {
			// bottom options
			if(y+2 <= 7) {  // check if it can move down on the board
				if(x+1 <= 7) { // check if it can move to the right
					if(board[y+2][x+1] == null || (board[y+2][x+1] != null && !board[y+2][x+1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y+2, x+1, board)) {
								moves.add(new Move(y,x,y+2,x+1));
							}
						} else {
							moves.add(new Move(y,x,y+2,x+1)); 
						} 
					}
				}
				if(x-1 >= 0) { // check if it can move to the left
					if(board[y+2][x+1] == null || (board[y+2][x-1] != null && !board[y+2][x-1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y+2, x-1, board)) {
								moves.add(new Move(y,x,y+2,x-1));
							}
						} else {
							moves.add(new Move(y,x,y+2,x-1));
						} 
					}
				}
			}
			// top options
			if(y-2 >= 0) {  // check if it can move down on the board
				if(x+1 <= 7) { // check if it can move to the right
					if(board[y-2][x+1] == null || (board[y-2][x+1] != null && !board[y-2][x+1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y-2, x+1, board)) {
								moves.add(new Move(y,x,y-2,x+1));
							}
						} else {
							moves.add(new Move(y,x,y-2,x+1));
						} 
					}
				}
				if(x-1 >= 0) { // check if it can move to the left
					if(board[y-2][x-1] == null || (board[y-2][x-1] != null && !board[y-2][x-1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y-2, x-1, board)) {
								moves.add(new Move(y,x,y-2,x-1));
							}
						} else {
							moves.add(new Move(y,x,y-2,x-1));
						} 
					}
				}
			}
			// left options
			if(x-2 >= 0) {  // check if it can move down on the board
				if(y+1 <= 7) { // check if it can move to the right
					if(board[y+1][x-2] == null || (board[y+1][x-2] != null && !board[y+1][x-2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y+1, x-2, board)) {
								moves.add(new Move(y,x,y+1,x-2));
							}
						} else {
							moves.add(new Move(y,x,y+1,x-2));
						} 
					}
				}
				if(y-1 >= 0) { // check if it can move to the left
					if(board[y-1][x-2] == null || (board[y-1][x-2] != null && !board[y-1][x-2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y-1, x-2, board)) {
								moves.add(new Move(y,x,y-1,x-2));
							}
						} else {
							moves.add(new Move(y,x,y-1,x-2));
						} 
					}
				}
			}
			// right options
			if(x+2 <= 7) {  // check if it can move down on the board
				if(y+1 <= 7) { // check if it can move to the right
					if(board[y+1][x+2] == null || (board[y+1][x+2] != null && !board[y+1][x+2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y+1, x+2, board)) {
								moves.add(new Move(y,x,y+1,x+2));
							}
						} else {
							moves.add(new Move(y,x,y+1,x+2));
						} 
					}
				}
				if(y-1 >= 0) { // check if it can move to the left
					if(board[y-1][x+2] == null || (board[y-1][x+2] != null && !board[y-1][x+2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.whiteCheck(y, x, y-1, x+2, board)) {
								moves.add(new Move(y,x,y-1,x+2));
							}
						} else {
							moves.add(new Move(y,x,y-1,x+2));
						} 
					}
				}
			}
		} else {// for black pieces moving
			// bottom options
			if(y+2 <= 7) {  // check if it can move down on the board
				if(x+1 <= 7) { // check if it can move to the right
					if(board[y+2][x+1] == null || (board[y+2][x+1] != null && board[y+2][x+1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y+2, x+1, board)) {
								moves.add(new Move(y,x,y+2,x+1));
							}
						} else {
							moves.add(new Move(y,x,y+2,x+1)); 
						} 
					}
				}
				if(x-1 >= 0) { // check if it can move to the left
					if(board[y+2][x+1] == null || (board[y+2][x-1] != null && board[y+2][x-1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y+2, x-1, board)) {
								moves.add(new Move(y,x,y+2,x-1));
							}
						} else {
							moves.add(new Move(y,x,y+2,x-1));
						} 
					}
				}
			}
			// top options
			if(y-2 >= 0) {  // check if it can move down on the board
				if(x+1 <= 7) { // check if it can move to the right
					if(board[y-2][x+1] == null || (board[y-2][x+1] != null && board[y-2][x+1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y-2, x+1, board)) {
								moves.add(new Move(y,x,y-2,x+1));
							}
						} else {
							moves.add(new Move(y,x,y-2,x+1));
						} 
					}
				}
				if(x-1 >= 0) { // check if it can move to the left
					if(board[y-2][x-1] == null || (board[y-2][x-1] != null && board[y-2][x-1].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y-2, x-1, board)) {
								moves.add(new Move(y,x,y-2,x-1));
							}
						} else {
							moves.add(new Move(y,x,y-2,x-1));
						} 
					}
				}
			}
			// left options
			if(x-2 >= 0) {  // check if it can move down on the board
				if(y+1 <= 7) { // check if it can move to the right
					if(board[y+1][x-2] == null || (board[y+1][x-2] != null && board[y+1][x-2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y+1, x-2, board)) {
								moves.add(new Move(y,x,y+1,x-2));
							}
						} else {
							moves.add(new Move(y,x,y+1,x-2));
						} 
					}
				}
				if(y-1 >= 0) { // check if it can move to the left
					if(board[y-1][x-2] == null || (board[y-1][x-2] != null && board[y-1][x-2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y-1, x-2, board)) {
								moves.add(new Move(y,x,y-1,x-2));
							}
						} else {
							moves.add(new Move(y,x,y-1,x-2));
						}
					}
				}
			}
			// right options
			if(x+2 <= 7) {  // check if it can move down on the board
				if(y+1 <= 7) { // check if it can move to the right
					if(board[y+1][x+2] == null || (board[y+1][x+2] != null && board[y+1][x+2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y+1, x+2, board)) {
								moves.add(new Move(y,x,y+1,x+2));
							}
						} else {
							moves.add(new Move(y,x,y+1,x+2));
						}
					}
				}
				if(y-1 >= 0) { // check if it can move to the left
					if(board[y-1][x+2] == null || (board[y-1][x+2] != null && board[y-1][x+2].isWhite)) {
						if(Board.checkTrue)	{
							if(!Board.blackCheck(y, x, y-1, x+2, board)) {
								moves.add(new Move(y,x,y-1,x+2));
							}
						} else {
							moves.add(new Move(y,x,y-1,x+2));
						}
					}
				}
			}
		}

		return moves;
	}
	

}
