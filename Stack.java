
import java.util.NoSuchElementException;
/*A standard Stack of type E Object that makes use of the LinkedList class in order to create a Stack.
  Observes the Standard "first-in-last-out" behavior of a Stack.*/
public class Stack<E>{
    /*LinkedList that stores the values of the Stack.*/
    protected LinkedList<E> stackVals;
    /*Default Constructor that initializes the superclass varibles to default values.*/
    public Stack(){
       stackVals=new LinkedList();
    }
    /*Standard add operation that "pushes" the new object(addedObj) of type E on the end of the Stack.
      @param:addedObj of type E is the generic object that will be added to the end of the Stack.*/
    public void add(E addedObj){
        stackVals.addToEnd(addedObj);
    }
    /*Returns true if the Stack is empty,else returns false.*/
    public boolean isEmpty(){
        return stackVals.getSize()==0;
    }
    /*Returns the size of the Stack.*/
    public int getSize(){
        return stackVals.getSize();
    }
    /*Standard remove method for a Stack object. 
      Removes the last value from the Stack and then returns the value that was just removed.*/
    public E pop() throws NoSuchElementException{
        return stackVals.removeTail();
    }
    /*Returns the last item on the Stack.*/
    public E peek(){
        return stackVals.peekS();
    }
}
