package br.com.ezschedule.apischedule.observer;

public class RowObject<T> {

    // Atributos
    private int length;
    private T[] row;

    // Construtor
    public RowObject(int capacity) {
        row = (T[]) new Object[capacity];
        length = 0;
    }

    // Métodos

    // Retorna true se a fila está vazia e false coso contrário
    public boolean isEmpty() {
        return length == 0;
    }

    // Retorna true se a fila está cheia e false caso contrário
    public boolean isFull() {
        return length == row.length;
    }

    // Se a fila não estiver cheia, insere info na fila
    // Se a fila estiver cheia, deve lançar IllegalStateException
    public void insert(T info) {
        if (isFull()) {
            throw new IllegalStateException("Row is full!");
        }
        else {
            row[length++] = info;
        }
    }

    // Retorna o primeiro da fila
    public T peek() {
        return row[0];
    }

    // Remove e retorna o primerio elemento da fila
    // Antes de retorna, se a fila não estiver vazia, deve fazer a fila "andar"
    public T poll() {
        T primeiro = row[0];

        if (!isEmpty()) {
            for (int i = 0; i < length - 1; i++) {
                row[i] = row[i+1];
            }
            row[length -1] = null;
            length--;
        }

        return primeiro;
    }

    // Exibe os elementos da fila
    public void exibe() {
        if (isEmpty()) {
            System.out.println("Row empty!");
        }
        else {
            System.out.println("Content row:");
            for (int i = 0; i < length; i++) {
                System.out.println(row[i]);
            }
        }
    }

    // Retorna o vetor fila
    public T[] getRow() {
        return row;
    }

    public int getLength(){
        return length;
    }
}