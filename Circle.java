
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Circle {
    /*The x and y coordinates(respectivly) of the circle*/
    private int xCoor,yCoor;
    /*The radius of the circle and the current 'depth' compared to other circles.*/
    private final int radius,zCoor;
    /*The color of the circle.*/
    private final Color circleColor;
    /*The total number of circles created.*/
    private static int totalZ;
    /*Constructor for a circle object. 
      @param:'x' is the x-coordinate of the circle,'y' is the y-coordinate of the circle,'r' is the radius of the circle,'color' is the color of the circle.*/
    public Circle(int x,int y,int r,Color color){
        xCoor=x;
        yCoor=y;
        radius=r;
        zCoor=totalZ;
        totalZ++;
        circleColor=color;
    }
    /*Uses the distance formula in order to find if the point (x,y) is within the bounds of the circle.
      Returns true if the point (x,y) is within the circle.
      @param:'x' is the x value of the point being tested, and 'y' is the y value of the pint being tested.*/
    public boolean isInside(int x,int y){
        return radius>=Math.sqrt(Math.pow((xCoor+radius)-x, 2)+Math.pow((yCoor+radius)-y, 2));
    }
    /*Method for drawing the current circle object.
      @param:a graphics object respoisible for drawing the circle. */
    public void draw(Graphics g){
        g.setColor(circleColor);
        g.fillOval(xCoor, yCoor, radius*2, radius*2);
    }
    /*Method that is used in order to update the position of the circle while it is being dragged.
      @param:'xIncrease' is the amount to increase xCoor by, and 'yIncrease' is the amount to increase yCoor by.*/
    public void updatePos(int xIncrease,int yIncrease){
        xCoor+=xIncrease;
        yCoor+=yIncrease;
    }
    /*Returns the radius of the current circle.*/
    public int getRadius(){
        return radius;
    }
    /*Returns the x value of the current circle.*/
    public int getX(){
        return xCoor;
    }
    /*Returns the y value of the current circle.*/
    public int getY(){
        return yCoor;
    }
    /*Sets the x value to a new value.
      @param:'nx' is the new value that xCoor is going to be set to.*/
    public void setX(int nx){
        xCoor=nx;
    }
    /*Sets the y value to a new value.
      @param:'ny' is the new value that yCoor is going to be set to.*/
    public void setY(int ny){
        yCoor=ny;
    }
}
