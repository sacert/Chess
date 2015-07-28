package board;

public class Point {

	private int y;
	private int x;
	
	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	// a8 -> 0,0. and a1 -> 0,7, h1 -> 7,7
   public static Point convertStringToPoint(String input){
	   final int middleNum = 4;
	   int xCoord;
	   int yCoord = input.charAt(0) - 97;
	   if(input.charAt(1) - 48 <= middleNum){
		   xCoord = Math.abs(input.charAt(1) - 48 - middleNum) + middleNum;
	   } else {
		   xCoord = Math.abs(input.charAt(1) - 48 - middleNum) - middleNum;
	   }
	   xCoord = Math.abs(xCoord);
	   // System.out.println("      DEBUG: " + input + " translates to " + " [" + yCoord + "," + xCoord + "]");
	   // return (new Point(yCoord, xCoord));
	   
	   System.out.println("      DEBUG: " + input + " translates to " + " [" + xCoord + "," + yCoord + "]");
	   return (new Point(xCoord, yCoord));
   }
   
   // 0,0 -> a8, 0,7 -> a1, 7,7 -> h1
   public static String convertPointToString(Point p){
	   String output = "";
	   int tempY = p.getX() + 97;
	   output += Character.toString((char)tempY);

	   final int middleNum = 4;
	   int tempX;
	   if(p.getY() <= middleNum){
		   tempX = Math.abs(p.getY() - middleNum) + middleNum;
	   } else {
		   tempX = Math.abs(p.getY() - middleNum) - middleNum;
	   }
	   output += Math.abs(tempX);
	   

	   return output;
   }

}
