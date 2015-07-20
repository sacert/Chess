package main;

public class Main {

    // create the chess board as an array of strings
   static String chessBoard[][] = {
            {"r","k","b","q","a","b","k","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," ","B"," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"K"," "," "," ","K","K","K","K"},
            {"R","K","K","K","K","B","K","R"}};
   
   static int kingPositionC, kingPositionL;
   
    public static void main(String[] args) {
    	
    	System.out.println(possibleMoves());
        displayBoard(chessBoard);
        makeMove("6040 ");
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
        int r = i/8, c = i%8;
        
        // for all possible moves, move up one unity (two if starting), 'attack' on the top right or top left position
        for(int j= 0; j < 3; j++) {
        	
        	// possibility for when just moving forward
        	if(j == 1) {
        		
        		// at the starting position of the white pawns
        		if(r == 6) {
        			try {
        				
        				// may move two spaces forward
		        		if(" ".equals(chessBoard[r-2+j/3][c-1+j%3])){
	        				list = list + r + c + (r-2+j/3) + (c-1+j%3) + " ";
		        		}
		        		
		        		// or one space forward
		        		if(" ".equals(chessBoard[r-1+j/3][c-1+j%3])){
	        				list = list + r + c + (r-1+j/3) + (c-1+j%3) + " ";
        				}
        			} catch(Exception e) {}
        		}
        		else { // when not in starting position
        			try {
        				
        				// one space forward
        				if(" ".equals(chessBoard[r-1+j/3][c-1+j%3])){
	        				list = list + r + c + (r-1+j/3) + (c-1+j%3) + " ";
        				}
	        		} catch(Exception e) {}
        		}
        	}
        	else { // j = 0 or 2 ('attacking')
        		try {
        			
        			// if black piece, take it
	        		if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0))) {
	        			list = list + r + c + (r-1+j/3) + (c-1+j%3) + " ";
	        		}
        		} catch(Exception e) {}
        	}
        }
        return list;
    }

    public static String possibleK(int i) {
        String list = "";
        return list;
    }
    
    // can change the for loop to be two-dimensional to make it look nicer 
    public static String possibleR(int i) {
        String list = "";
        int r = i/8, c = i%8;
        int temp = 1;
        for(int j = -1; j <= 1; j++) {
    		try {
    			// get all movable locations in the x axis that have nothing on them
    			while(" ".equals(chessBoard[r+temp*j][c])) {
        			list = list + r + c + (r+temp*j) + (c) + " ";
    				temp++;
    			}
    			// if there is a black piece
    			if(Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))) {
    				list = list + r + c + (r+temp*j) + (c) + " ";
    			}
    			temp = 1;
    			// get all movable locations in the y axis that have nothing on them
    			while(" ".equals(chessBoard[r][c+temp*j])) {
        			list = list + r + c + (r) + (c+temp*j) + " ";
    				temp++;
    			}
    			// if there is a black piece
    			if(Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))) {
    				list = list + r + c + (r) + (c+temp*j) + " ";
    			}
    			
    		} catch(Exception e) {} 
    		// reset temp for change in direction
    		temp = 1;
    	}
        return list;
    }

    public static String possibleB(int i) {
    	String list = "";
        int r = i/8, c = i%8;
        int temp = 1;
        for(int j = -1; j <= 1; j++) {
    		try {
    			if( j != 0) {
	    			// get all movable locations in the left angle
	    			while(" ".equals(chessBoard[r+temp*j][c+temp*j])) {
	        			list = list + r + c + (r+temp*j) + (c+temp*j) + " ";
	    				temp++;
	    			}
	    			// if there is a black piece
	    			if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*j].charAt(0))) {
	    				list = list + r + c + (r+temp*j) + (c+temp*j) + " ";
	    			}
	    			temp = 1;
	    			// get all movable locations in the top right angle
	    			while(" ".equals(chessBoard[r-temp*j][c+temp*j])) {
	        			list = list + r + c + (r-temp*j) + (c+temp*j) + " ";
	    				temp++;
	    			}
	    			// if there is a black piece
	    			if(Character.isLowerCase(chessBoard[r-temp*j][c+temp*j].charAt(0))) {
	    				list = list + r + c + (r-temp*j) + (c+temp*j) + " ";
	    			}
	    			temp = 1;
	    			// get all movable locations in the bottom right angle
	    			while(" ".equals(chessBoard[r+temp*j][c-temp*j])) {
	        			list = list + r + c + (r+temp*j) + (c-temp*j) + " ";
	    				temp++;
	    			}
	    			// if there is a black piece
	    			if(Character.isLowerCase(chessBoard[r+temp*j][c-temp*j].charAt(0))) {
	    				list = list + r + c + (r+temp*j) + (c-temp*j) + " ";
	    			}
    			}
    		} catch(Exception e) {} 
    		// reset temp for change in direction
    		temp = 1;
    	}
        return list;
    }

    public static String possibleQ(int i) {
    	String list = "", oldPiece;
        int r = i/8, c = i%8;
        int temp = 1;
        for(int j = -1; j <= 1; j++) {
        	for(int k = -1; k <= 1; k++) {
        		try {
        			while(" ".equals(chessBoard[r+temp*j][c+temp*k])) {
        				oldPiece = chessBoard[r+temp*j][c+temp*k];
        				chessBoard[r][c] = " ";
        				chessBoard[r+temp*j][c+temp*k] = "Q";
        				if(kingsSafe()) {
	        				//System.out.println(r + " " + c + " " +  (r-1+j/3) + " " + (c-1+j%3) + " " + oldPiece);
	        				list = list + r + c + (r+temp*j) + (c+temp*k) + " ";
	        			}
        				chessBoard[r][c] = "Q";
        				chessBoard[r+temp*j][c+temp*k] = oldPiece;
        				temp++;
        			}
        			if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
        				oldPiece = chessBoard[r+temp*j][c+temp*k];
        				chessBoard[r][c] = " ";
        				chessBoard[r+temp*j][c+temp*k] = "Q";
        				if(kingsSafe()) {
	        				//System.out.println(r + " " + c + " " +  (r-1+j/3) + " " + (c-1+j%3) + " " + oldPiece);
	        				list = list + r + c + (r+temp*j) + (c+temp*k) + " ";
	        			}
        				chessBoard[r][c] = "Q";
        				chessBoard[r+temp*j][c+temp*k] = oldPiece;
        			}
        		} catch(Exception e) {} 
        		temp = 1;
        	}
        }
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
	        			// dont think these do anything
	        			//chessBoard[r][c] = "";
	        			//chessBoard[r-1+j/3][c-1+j%3] = "A";
	        			kingPositionC = i+(j/3) * 8 + j%3 - 9;
	        			int kingTemp = kingPositionC;
	        			if(kingsSafe()) {
	        				//System.out.println(r + " " + c + " " +  (r-1+j/3) + " " + (c-1+j%3) + " " + oldPiece);
	        				list = list + r + c + (r-1+j/3) + (c-1+j%3) + oldPiece;
	        			}
	        			//chessBoard[r][c] = "A";
	        			//chessBoard[r-1+j/3][c-1+j%3] = oldPiece;
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
