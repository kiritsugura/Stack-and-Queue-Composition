

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import java.util.Random;
import org.lwjgl.input.Keyboard;
/*Main GUI class that runs the Lab3 application.*/
public class SlickApplication extends BasicGame implements MouseListener,KeyListener{
    /*Queue that contains the currently active circle objects.*/
    protected static Queue<Circle> totalCircles;
    /*Queue that contains the action messages assocated with a particular action.*/
    protected static Queue<StringThing> stringActions;
    /*Stacks that conatin the ost recent actions that can be undone or redone.*/
    protected static Stack<ActionType> undoStack,redoStack;
    /*The current radius of the mousewheel circle, and the starting position of the StringThing message x and y values.*/
    protected static int mouseCircleRadius,stringX,stringY;
    /*Boolean that is used to determine if a circle is currently being dragged.*/
    protected boolean isDragging;
    /*Circle that is currently being dragged.*/
    protected Circle dragging;
    /*ActionType that is used to store the new x and y vaues when the dragging of a circle is done.*/
    protected ActionType dragged;
    /*Constructor for the GUI Object.*/
    public SlickApplication(String title){
        super(title);
    }
    /*Mouse listener method that is being used in order to create Circles.*/
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount){
        if(button==1){                    //right mouse button
            dragging=null;
            Random r=new Random();
            /*Sets the action time and creates the action message.*/
            stringActions.add(new StringThing(2,stringX,stringY,System.currentTimeMillis()));
            Circle newCir=new Circle(x-mouseCircleRadius,y-mouseCircleRadius,mouseCircleRadius,new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()));
            totalCircles.add(newCir);
            /*Creates an action to associate with the circle.*/
            ActionType circleCreate=new ActionType(x-mouseCircleRadius,y-mouseCircleRadius,mouseCircleRadius,newCir,true);
            undoStack.add(circleCreate);
            /*Resets the redo Stack, since an action previously performed then undo would be void if another action was performed.*/
            redoStack=new Stack();
        }
    }
    /*Mouse listener method that is being used in order to drag circles across the screen.*/
    @Override
    public void mouseDragged(int oldx,int oldy,int newx,int newy){
        /*If the left mouse button is down, the mouse(x,y) are in the circle,and another circle is not being dragged.*/
        if(Mouse.isButtonDown(0) && contains(newx,newy) && !isDragging){
            dragging=getClickedCircle(newx,newy);
            isDragging=true;
            /*Creates an actionType to assocaite with the dragging event.*/
            dragged=new ActionType(dragging.getX(),dragging.getY(),dragging.getRadius(),dragging,false); 
            /*Creates a message to associate with the event, and also stores the time the event took place.*/
            stringActions.add(new StringThing(3,stringX,stringY,System.currentTimeMillis()));
        }else if(Mouse.isButtonDown(0) && isDragging){
            /*Updates the coordinates of the circle based on the dragging.*/
            dragging.updatePos(newx-oldx,newy-oldy);           
        }
    }    
    /*Mouse listener method that is being used in order to adjust the radius of the circle.*/
    @Override
    public void mouseWheelMoved(int change){
        /*So the radius of the circle cannot be 0.*/
        if(change<0 && mouseCircleRadius<=5){
            mouseCircleRadius=5;
        }else if(change>0){
            mouseCircleRadius+=5;
        }else{
            mouseCircleRadius-=5;
        }
    }
    /*Key listener used in order to get input from the user via keyboard.*/
    @Override
    public void keyPressed(int key,char c){
        /*If the key is 44(z) and either of the control(crtl) keys are pressed.*/
        if(key==44 && (Keyboard.isKeyDown(29)||Keyboard.isKeyDown(157))){
            /*Action is being undone.*/
            if(!undoStack.isEmpty()){
                /*Creates a message for the event and sets the time for it.*/
                stringActions.add(new StringThing(1,stringX,stringY,System.currentTimeMillis()));
                ActionType undone=undoStack.pop();
                /*If the action was one of creation.*/
                if(undone.getWasCreated()){
                    redoStack.add(undone);
                    removeLastCircle();
                } 
                /*If the action was one of movement.*/
                else{
                   
                    undone.undoMove();
                    redoStack.add(undone);
                }
            }
        /*If the key is 21(Y) and either control(ctrl) key is being pressed.*/    
        }else if(key==21 && (Keyboard.isKeyDown(29)||Keyboard.isKeyDown(157))){
            /*Action is being redone.*/
            if(!redoStack.isEmpty()){
                /*Creates a new message and time associated with the event.*/
                stringActions.add(new StringThing(0,stringX,stringY,System.currentTimeMillis()));
                ActionType redone=redoStack.pop();
                /*If the action was one of creation*/
                if(redone.getWasCreated()){
                    undoStack.add(redone);
                    totalCircles.add(redone.getCircle());     
                }
                /*If the action was one of movement.*/
                else{
                    redone.redoMove();
                    undoStack.add(redone);
                }                               
            }
        }
        /*If key 1(or the escape key) was pressed then exit the window.*/
        else if(key==1){
            System.exit(0);
        }        
    }
    /*Initialization of varaibles that will be used throughout the program.*/
    @Override
    public void init(GameContainer gc) throws SlickException{
        stringX=0;
        stringY=460;
        dragging=null;
        totalCircles=new Queue();
        undoStack=new Stack();
        redoStack=new Stack();
        mouseCircleRadius=40;
        isDragging=false;        
        stringActions=new Queue();
        gc.getInput().addMouseListener(this);
        gc.getInput().addKeyListener(this);
    }
    /*Method that updates the status of the current GUI.*/
    @Override
    public void update(GameContainer gc, int i) throws SlickException{ 
        /*If the left mouse button is not down.*/
        if(!Mouse.isButtonDown(0)){
            /*If an object just finished being dragged,then the final x and y of the circle are recorded.*/
            if(!(dragged==null)){
                dragged.setNX(dragging.getX());
                dragged.setNY(dragging.getY());
                undoStack.add(dragged);
                dragged=null;
            }
            dragging=null;
            isDragging=false;
        }           
        /*If the message has been on the screen longer than 1.5 seconds, then it is removed.*/
        if(stringActions.getSize()!=0 && (System.currentTimeMillis()-stringActions.peek().getTime()>1500)){
            stringActions.remove();
        }
    }
    /*Method used to draw items to the GUI.*/
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException{
        /*Draws circles if the exist.*/
        if(!totalCircles.isEmpty()){
            Queue<Circle> drawQ=new Queue();
            /*Draws each circle by drawing them then transfering them to another queue.*/
            while(totalCircles.getSize()!=0){
                Circle drawn=totalCircles.remove();
                drawn.draw(g);
                drawQ.add(drawn);
            }
            /*Sets the drawn circles back to the circle Queue.*/
            totalCircles=drawQ;
        }
        /*Draws the event messages if any exist.*/
        if(stringActions.getSize()!=0){
            Queue<StringThing> drawing=new Queue();
            int y=0;
            /*Set the color to a transaparent white.*/
            g.setColor(new Color(1.0f,1.0f,1.0f,.4f));
            /*Draws each message, drecreasing the y vale in order to space them out on screen.*/
            while(stringActions.getSize()!=0){
                StringThing tempString=stringActions.remove();
                tempString.draw(g,y);
                y+=20;
                drawing.add(tempString);
            }
            stringActions=drawing;
        }        
        g.setColor(Color.white);
        /*Draws the cursor that is around the mouse.*/
        g.drawOval(Mouse.getX()-mouseCircleRadius,480-Mouse.getY()-mouseCircleRadius,2*mouseCircleRadius,2*mouseCircleRadius);        
    }
    /*Determines if the coordinates(x,y) are within any of the circles.
      Returns true if the point is contained in any circle, else otherwise.*/
    protected boolean contains(int x,int y){
        boolean isInside=false;
        Queue<Circle> testQ=new Queue();
        /*Loop until each circle is checked.*/
        while(totalCircles.getSize()!=0){
            Circle circle=totalCircles.remove();
            /*If the point is within a circle, change the return to true.*/
            if(circle.isInside(x, y)){
                isInside=true;
            }
            testQ.add(circle);
        }
        totalCircles=testQ;
        return isInside;
    }
    /*Returns the circle that the point(x,y) is contained within.
      @param:'x' and 'y' are the x and y values of the point that is being tested to see if they are within any circle.
      throws an exception if the point was not within any circle.*/
    protected Circle getClickedCircle(int x,int y) throws NoSuchElementException{
        Circle clicked=null;
        Queue<Circle> checkQ=new Queue();
        /*Loops through each element of the Queue.*/
        while(totalCircles.getSize()!=0){
            Circle circle=totalCircles.remove();
            if(circle.isInside(x, y)){
                clicked=circle;
            }
            checkQ.add(circle);
        }
        totalCircles=checkQ;
        if(clicked==null)
            throw new NoSuchElementException();
        return clicked;        
    }
    /*Removes and returns the last item of the Queue in order to make the ActionType Stacks function properly.
      While it does not act like a Queue is the regard, it is legal because no method is directly removing the item from the end.
      Throws an exception if the Queue contains no items.*/
    protected Circle removeLastCircle()throws NoSuchElementException{
        Circle last=null;
        Queue<Circle> checkQ=new Queue();
        /*Loop through to the last item.*/
        while(totalCircles.getSize()!=1){
            Circle circle=totalCircles.remove();
            checkQ.add(circle);
        }
        
        if(totalCircles.getSize()==0)
            throw new NoSuchElementException();
        /*Set the value of the last item to a variable that will be returned.*/
        last=totalCircles.remove();
        totalCircles=checkQ;
        return last;         
    }
    /*Main method for the GUI class.*/
    public static void main(String[] args){
        try{
            AppGameContainer appgc;
            SlickApplication application = new SlickApplication("Lab 3");
            appgc = new AppGameContainer(application);
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        }catch (SlickException ex){
            Logger.getLogger(SlickApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}    
