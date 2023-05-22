package br.com.ezschedule.apischedule.observer;

public class PilhaObj<T> {
    private T[] pilha;
    private int topo;

    public PilhaObj(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    public Boolean isEmpty() {
        return topo == -1;
    }
    public Boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        }
        else {
            throw new IllegalStateException("model.Pilha cheia!");
        }
    }

    public T pop() {
        if(!isEmpty()){
            return pilha[topo--];
        }
        return null;
    }

    public T peek() {
        if(!isEmpty()){
            return pilha[topo];
        }
        return null;
    }

    public void exibe() {
        if(isEmpty()){
            System.out.println("model.Pilha vazia");
        }
        else {
            for (int i = 0; i < topo; i++) {
                System.out.print(" " + pilha[i]);
            }
            System.out.println();
        }
    }

    public int getTopo() {
        return topo;
    }
}