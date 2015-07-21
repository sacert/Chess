package units;

import player.Player;

public abstract class Piece {
	
	private Player player;
	
	public Piece(Player player) {
		this.player = player;
	}
	
	public static boolean isMovePossible(String start, String destination){
		return false; // each class must implement this, otherwise it returns false.
	}
	
	public abstract String allPossibleMoves(String[][] chessBoard);

	public Player getPlayer() {
		return player;
	}
	

}
