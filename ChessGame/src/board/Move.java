package board;

import constants.Constants;
import units.Piece;

public class Move implements Constants  {

	public byte x1;
	public byte y1;
	public byte x2;
	public byte y2;
	
	int type;
	
	// get locations of the current piece to where it is moving
	public Move(byte a, byte b, byte c, byte d) {
		x1 = a;
		y1 = b;
		x2 = c;
		y2 = d;
	}
	
	public Move(int a1, int b1, int a2, int b2) {
		this((byte)a1, (byte)b1, (byte)a2, (byte)b2);
	}
	
	// move the piece
	public void movePiece(Piece[][] board) {
		board[x2][y2] = board[x1][y1]; 	// set the target location to the moving piece	
		board[x1][y1] = null;			// set the old location to null 
		type = board[x1][y1].type;
	}
	
	public String toString() {

		return TYPE[type] + COORD[x1]+y1 + COORD[x2]+y2;
	}
}
