package org.example;

public class Pilha {

    // 01) Atributos
    private int[] pilha;
    private int topo;

    // 02) Construtor
    public Pilha(int capacidade) {
        this.pilha = new int[capacidade];
        this.topo = -1;
    }

    // 03) MÃ©todo isEmpty
    public Boolean isEmpty() {
        if(topo == -1){
            return true;
        }
        return false;
    }

    // 04) MÃ©todo isFull
    public Boolean isFull() {
        if(topo == pilha.length -1){
            return true;
        }
        return false;
    }

    // 05) MÃ©todo push
    public void push(int info) {
        if(isFull()){
            throw new IllegalStateException("Pilha cheia!");
        }

        pilha[++topo] = info;
    }

    // 06) MÃ©todo pop
    public int pop() {
        if(isEmpty()){
            return -1;
        }
        return pilha[topo--];
    }

    // 07) MÃ©todo peek
    public int peek() {
        if(isEmpty()){
            return -1;
        }
        return pilha[topo];
    }

    // 08) MÃ©todo exibe
    public void exibe() {
        if(isEmpty()){
            System.out.println("Pilha Vazia!");
        }

        for(int p : pilha){
            System.out.println(p);
        }
    }


    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }
}