

/*Class that stores information events in order to allow for redo and undo operations.*/
public class ActionType{
    /*Used in order to differentiate if the event was a creation or change in location.*/
    private final boolean wasCreated;
    /*'oldX' and 'oldY' are the y coordinates of the circle when the event occurs. 'newX' and 'newY' are the coordinates after(if the circle was moved.)
      radius is the radius of the circle circleObj.*/
    private int oldX,oldY,newX,newY,radius;
    /*The circle associated with the current event.*/
    private Circle circleObj; 
    /*Constructor for the ActionType class.
      @param:'x' and 'y' are the x and y coordinates respectivly,rad is the circle radius,crl is the circle associated with the event,
       and isC is the type of event(either created or moved).*/
    ActionType(int x,int y,int rad,Circle crl,boolean isC){
        oldX=x;
        oldY=y;
        radius=rad;
        circleObj=crl;
        wasCreated=isC;
    }
    /*Returns the circle object associated with the current event.*/
    public Circle getCircle(){
        return circleObj;
    }
    /*Returns wheather the circle event was one of creation or movement.*/
    public boolean getWasCreated(){
        return wasCreated;
    }
    /*Sets the newX value to the circle X location when the circle is done moving.
      @param:'nx' is the new x value assocaited with the circle.*/
    public void setNX(int nx){
        newX=nx;
    }
    /*Sets the newY value to the circle Y location when the circle is done moving.
      @param:'ny' is the new y value assocaited with the circle.*/    
    public void setNY(int ny){
        newY=ny;
    }
    /*'Reverts the circle to the previous position it was at.'*/
    public void undoMove(){
        circleObj.setX(oldX);
        circleObj.setY(oldY);        
    }
    /*Sets the circle to the next position given by the redo Stack.*/
    public void redoMove(){
        circleObj.setX(newX);
        circleObj.setY(newY);
    }
}