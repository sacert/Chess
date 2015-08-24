package units;

import player.Player;
import constants.Constants;;

public class Piece implements Constants {
	
	public byte type;	// for the type of piece
	public boolean isWhite;
	public boolean canCastle;
	
	public Piece(byte type, boolean white) {
		isWhite = white;
		this.type = type;
		if(type == ROOK || type == KING){
			this.canCastle = true;
		} else {
			this.canCastle = false;
		}
	}
	
	
	
//	
//	public void setType(byte type) {
//		this.type = type;
//	}
//	private Player player;
//	
//	public Piece(Player player) {
//		this.player = player;
//	}
//	
//	public static boolean isMovePossible(String start, String destination){
//		return false; // each class must implement this, otherwise it returns false.
//	}
//	
//	public abstract String allPossibleMoves(String[][] chessBoard);
//
//	public Player getPlayer() {
//		return player;
//	}
	

}
