package app;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {

  private int size;
  private Node<T> head = null;

  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) {

      Node <T> newNode;
      Node <T> previousNode = this.head;
      if(elem == null){
        throw new NullPointerException();
      
      }
      else if(isEmpty()){
        newNode = new Node<T> (elem, null);
        head = newNode;
        size = size + 1;
      
      }
      else{
        newNode = new Node<T> (elem, previousNode);
        head = newNode;
        size = size+1;
      }
  }

  @Override
  public void insertLast(T elem) {
      
      Node <T> newNode;
      if(elem == null){
        throw new NullPointerException();
      }
      
      else if(isEmpty()){
        newNode = new Node <T> (elem,null);
        head = newNode;
        size = size + 1;
  
      }
      else{
        appendTail(elem, head, head); 
      }
  }

  @Override
  public void insertAt(int index, T elem) {

      Node<T> currentNode = this.head;
      Node<T> prevNode = this.head;
      if(elem.equals(null)){
        throw new NullPointerException();
      
      }
      else if(index>size() || index<0){
        throw new IndexOutOfBoundsException();
      
      }
      else if(index == size){
        insertLast(elem);
      
      }
      else{
        insert(index,0, elem, currentNode, prevNode);
      }  

  }

  @Override
  public T removeFirst() {
    T removedItem = null;

    if(size()==0){
      throw new IllegalStateException();
    
    }
    else{
      removedItem = head.getData();
      Node <T> newHead = head.getNext();
      head = newHead;
      size = size - 1;
    }
    return removedItem;
  }

  @Override
  public T removeLast() {
      T removedItem = null;

      if(size() == 0){
        throw new IllegalStateException();
      }
      else{
        removedItem = removeTail(this.head, this.head);
      }
      return removedItem;
  }

  @Override
  public T removeAt(int i) {
      T removedItem = null;

      if(i >= size() || i < 0 ){
        throw new IndexOutOfBoundsException();
      
      }
      else{
        removedItem = removed(i, 0, head, head.getNext(), head);
      }
      return removedItem;
  }

  @Override
  public T getFirst() {
      T item = null;
    
      if(size() == 0){
        throw new IllegalStateException();
      
      }
      else{
        item = this.head.getData();
      }
      return item;
  }

  @Override
  public T getLast() {
      T item = null;
   
      if(size() == 0){
        throw new IllegalStateException();
      
      }
      else{
        item = getElement(size()-1, head, 0);
      }
      return item;
  }

  @Override
  public T get(int i) {
      T item = null;

      if(i >= size() || i < 0){
        throw new IndexOutOfBoundsException();
      
      }
      else{
        item = getElement(i, this.head, 0);
      }
      return item;
  }

  @Override
  public void remove(T elem) {
 
      if(elem == null){
        throw new NullPointerException();
      
      }
      else{
        int index = indexOf(elem);
        if(index == -1){
          throw new ItemNotFoundException();
        }
        else{  
           removed(index, 0, head, head.getNext(),head);
        }  
      } 
  }

  @Override
  public int indexOf(T elem) {
      int index = -1;
      
      if(elem.equals(null)){
        throw new NullPointerException();
      
      }
      else if(isEmpty()){
        return -1;
      
      }
      else{
        index = getIndex(elem, 0, this.head);
      }
      return index;
  }


  @Override
  public boolean isEmpty() {
      boolean empty = false;
     
      if(size() == 0){
        empty = true;
      }
      return empty;
  }


  public Iterator<T> iterator() {
    Iterator<T> iter = new LinkedNodeIterator<T>(this.head);;
 

    return iter;
  }


  private void appendTail(T data, Node<T> curNode, Node<T> prevNode){
    Iterator<T> iter = iterator();
    Node<T> nodeNew;
    if(curNode == null){
      nodeNew = new Node<T> (data,null);
      prevNode.setNext(nodeNew);
      size=size+1;

    }
    else{
      if(iter.hasNext()){
        prevNode = curNode;
        curNode =curNode.getNext();
        appendTail(data, curNode, prevNode);
      }
    }
  }

  private int getIndex(T elem, int index, Node<T> node){ 
    if (node == null) {
      return -1;
    }
    T currData = node.getData();

    if (elem.equals(currData)){
      return index;
    }
    return getIndex(elem, index + 1,node.getNext());
  }

  private void insert(int index, int num, T data, Node<T> cur, Node<T> pre ){
    Iterator<T> traverser = iterator();
    if(index == 0){
      insertFirst(data);
      
    }
    else if(index == num){
      Node<T> newNode = new Node<T>(data,cur);
      pre.setNext(newNode);
      size = size + 1;  
      
    }
    else{
      if(traverser.hasNext()){
        pre = cur;
        insert(index,(num + 1), data, cur.getNext(),pre);
      }  
    }
  }

  private T removeTail(Node<T> curr, Node<T> pre){
    T elem= null;
    Iterator<T> iterate = iterator();
    if(curr.getNext() == null){
      pre.setNext(null);
      size = size - 1;
      return curr.getData();
    }
    else{
      if(iterate.hasNext()){
        pre = curr;
        elem = removeTail(curr.getNext(), pre);
      }
    }
    return elem;
  }

  private T getElement(int index, Node<T> curNode, int num){
    Iterator<T> iterate = iterator();
    T element = null;
    
    if(num == index){
      return curNode.getData();
    }
    else{
      if(iterate.hasNext()){
       element= getElement(index, curNode.getNext(),(num + 1));
      }
      
    }
    return element;
  }

  private T removed(int index, int num, Node<T> curNode, Node<T> sucNode, Node<T>prevNode){
    Iterator<T> traverseIterator = iterator();
    T elem = null;
    if(index == 0){
      elem = removeFirst();
    }
    else if(index == (size()-1)){
      elem = removeLast();
    }
    else if(num == index){
       prevNode.setNext(sucNode);
       size = size-1;
       return curNode.getData();
       
    }
    else{
      if(traverseIterator.hasNext()){
        prevNode = curNode;
        curNode = sucNode;
        elem = removed(index, (num+1), curNode, sucNode.getNext(), prevNode);
      }
    }
    return elem;
  }  
}
