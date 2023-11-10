package br.com.ezschedule.apischedule.observer;

public class PileObject<T> {
    private T[] pile;
    private int top;

    public PileObject(int capacity) {
        pile = (T[]) new Object[capacity];
        top = -1;
    }

    public Boolean isEmpty() {
        return top == -1;
    }
    public Boolean isFull() {
        return top == pile.length - 1;
    }

    public void push(T info) {
        if (!isFull()) {
            pile[++top] = info;
        }
        else {
            throw new IllegalStateException("model.Pilha cheia!");
        }
    }

    public T pop() {
        if(!isEmpty()){
            return pile[top--];
        }
        return null;
    }

    public T peek() {
        if(!isEmpty()){
            return pile[top];
        }
        return null;
    }

    public void exibe() {
        if(isEmpty()){
            System.out.println("model.Pilha vazia");
        }
        else {
            for (int i = 0; i < top; i++) {
                System.out.print(" " + pile[i]);
            }
            System.out.println();
        }
    }

    public int getTop() {
        return top;
    }
}