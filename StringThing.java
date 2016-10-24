
import org.newdawn.slick.Graphics;
/*Class that stores the messages that appear on-screen when an action occurs.*/
public class StringThing{ 
    /*The message of the action that occured*/
    private String message;
    /*The x and y coordinates(respectivly) of the message that will be drawn.*/
    private int xCoor,yCoor;
    /*The time since the action occured. Checked against the current time in order to dispose of the message after 1.5 seconds.*/
    private double time;
    /*Constructor for the StringThing object.
      @param:'i' is the value passed into the function in order to determine the message. 'x' and 'y' are the x and y coordinates respectivly,
       'tim' is the time when the action occured.*/
    public StringThing(int i,int x,int y,double tim){
        if(i==0)    
            message="Redone";
        else if(i==1)    
            message="Undone";
        else if(i==2)    
            message="Circle created";
        else
            message="Circle Moved";
        xCoor=x;
        yCoor=y;
        time=tim;
    }
    /*Returns the message currently stores in this object.*/
    public String getMessage(){
        return message;
    }
    /*Returns the time that the action took place.*/
    public double getTime(){
        return time;
    }
    /*Method that draws the message.
      @param:'g' is the graphics object used to draw the string,while 'y' is the change in y based on the number 'StringThing' instances.*/
    public void draw(Graphics g,int y){
         g.drawString(message,xCoor, yCoor-y);
    }
        
}
