package board;

import constants.Constants;
import units.Piece;

public class Move implements Constants  {

	public byte x1;
	public byte y1;
	public byte x2;
	public byte y2;
	
	public Piece p = null;
	
	int type;
	
	// get locations of the current piece to where it is moving
	public Move(byte a, byte b, byte c, byte d) {
		y1 = a;
		x1 = b;
		y2 = c;
		x2 = d;
	}
	
	public Move(int a1, int b1, int a2, int b2) {
		this((byte)a1, (byte)b1, (byte)a2, (byte)b2);
	}
	
	// move the piece
	public void movePiece(Piece[][] board) {
		type = board[y1][x1].type;
		board[y2][x2] = board[y1][x1]; 	// set the target location to the moving piece	
		board[y1][x1] = null;			// set the old location to null 
	}
	
	public String toString() {

		return TYPE[type] + COORD[x1]+y1 + COORD[x2]+y2;
	}
}
