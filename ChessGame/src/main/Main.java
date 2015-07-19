package main;

public class Main {

    // create the chess board as an array of strings
   static String chessBoard[][] = {
            {"r","k","b","q","a","b","k","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","k","B","Q","A","B","K","R"}};
   
   static int kingPositionC, kingPositionL;
   
    public static void main(String[] args) {
    	
    	System.out.println(possibleMoves());
        displayBoard(chessBoard);
        makeMove("5040 ");
        displayBoard(chessBoard);
    }
    
    public static void makeMove(String move) {
    	// P = pawn promotion
    	if(move.charAt(4) != 'P') {
    		chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
    		chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
    	} else {
    		
    	}
    }
    
    public static void makeUndo(String move) {
    	
    }

    public static String possibleMoves(){
        String list = "";
        for(int i = 0; i < 64; i++) {
            switch(chessBoard[i/8][i%8]){
                case "P": list +=possibleP(i);
                    break;
                case "R": list +=possibleR(i);
                    break;
                case "K": list +=possibleK(i);
                    break;
                case "B": list +=possibleB(i);
                    break;
                case "Q": list +=possibleQ(i);
                    break;
                case "A": list +=possibleA(i);
                    break;
            }
        }
        return list;
    }

    public static String possibleP(int i) {
        String list = "";
        return list;
    }

    public static String possibleK(int i) {
        String list = "";
        return list;
    }

    public static String possibleR(int i) {
        String list = "";
        return list;
    }

    public static String possibleB(int i) {
        String list = "";
        return list;
    }

    public static String possibleQ(int i) {
        String list = "";
        return list;
    }
    
    // x0,y0,x1,y1
    public static String possibleA(int i) {
        String list = "", oldPiece;
        int r = i/8, c = i%8;
        for(int j= 0; j < 9; j++) {
        	if( j != 4) {
        		try {
	        		if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) {
	        			oldPiece = chessBoard[r-1+j/3][c-1+j%3];
	        			chessBoard[r][c] = "";
	        			chessBoard[r-1+j/3][c-1+j%3] = "A";
	        			kingPositionC = i+(j/3) * 8 + j%3 - 9;
	        			int kingTemp = kingPositionC;
	        			if(kingsSafe()) {
	        				System.out.println(r + " " + c + " " +  (r-1+j/3) + " " + (c-1+j%3) + " " + oldPiece);
	        				list = list + r + c + (r-1+j/3) + (c-1+j%3) + oldPiece;
	        			}
	        			chessBoard[r][c] = "A";
	        			chessBoard[r-1+j/3][c-1+j%3] = oldPiece;
	        			kingPositionC = kingTemp;
	        		}
        		} catch(Exception e) {}
        	}
        }
        return list;
    }
    
    public static boolean kingsSafe() {
    	return true;
    }

    // display the board through a simple output
    public static void displayBoard(String[][] chessBoard){
        for(int i = 0; i < 8; i++) {
        	System.out.print("[");
            for(int j = 0; j < 8; j++) {
            	if(j != 7) {
            		System.out.print(chessBoard[i][j] + ", ");
            	} else {
            		System.out.print(chessBoard[i][j]);
            	}
            }
            System.out.println("]");
        }
        System.out.println("");
    }
}
