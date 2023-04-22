package br.com.ezschedule.apischedule.model;

public class ObjectList<T> {

    private T[] vetor;
    private Integer nElement;

    public ObjectList(int size) {
        this.vetor = (T[]) new Object[size];
        this.nElement = 0;
    }

    public int getSize() {
        return nElement;
    }

    public T getByIndex(int i) {
        if (i >= 0 && i < nElement) {
            return vetor[i];
        }
        return null;
    }

    public T addObject(T object) {
        if (object != null) {
            vetor[nElement++] = object;
            return vetor[nElement];
        }
        return null;
    }

    public int search(T searchElement) {
        for (int i = 0; i < nElement; i++) {
            if (searchElement == vetor[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeByIndex(int id) {
        boolean result = false;
        if (id < vetor.length && id > 0) {
            for (int i = id; i < nElement - 1; i++) {
                vetor[i] = vetor[i + 1];
            }
            nElement--;
        }
        return result;
    }

    public boolean removeElemento(T element) {
        for (int i = 0; i < nElement; i++) {
            if (vetor[i] == element) {
                removeByIndex(i);
            }
        }
        return false;
    }

    public boolean substitute(T oldValue, T newValue) {
        for (int i = 0; i < nElement; i++) {
            if (vetor[i] == oldValue) {
                vetor[i] = newValue;
                return true;
            }
        }
        return false;
    }

    public boolean substituteByIndex(int i, T newValue) {

        if(i >= 0 && i < nElement) {
            vetor[i] = newValue;
            return true;
        }
        return false;
    }


}
