
import java.util.NoSuchElementException;
/*A standard Queue of type E Object that makes use of the LinkedList class in order to create a Queue.
  Observes the standard "first-in-first-out" Queue behavior.*/
public class Queue<E>{
    /*LinkedList that stores the values of the Queue.*/
    protected LinkedList<E> queueVals;
    /*Default Constructor for a Queue object that initializes the varibles in superclass LinkedList to default values.*/
    public Queue(){
        queueVals=new LinkedList();
    }
    /*A standard add method that adds an item to the end of the queue.
      @param:addedObj of type E is the generic object that will be added to the end of the Queue.*/
    public void add(E addedObj){
        queueVals.addToEnd(addedObj);
    }
    /*Returns true if the Queue is empty, else returns false.*/
    public boolean isEmpty(){
        return queueVals.getSize()==0;
    }    
    /*Returns the size of the Queue.*/
    public int getSize(){
        return queueVals.getSize();
    }
    /*A standard remove method that removes the first item of the Queue.
      Returns the first item of the Queue once it is removed.*/
    public E remove() throws NoSuchElementException{
        return queueVals.removeHead();
    }
    /*Returns the value of the first item on the Queue.*/
    public E peek(){
        return queueVals.peekQ();
    }    
}
