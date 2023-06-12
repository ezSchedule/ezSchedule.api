package br.com.ezschedule.apischedule.csv;
public class ListObject<T> {

        private T[] vector;
        private int numberElement;

        public ListObject(int length) {
            vector = (T[]) new Object[length];
            numberElement = 0;
        }

        public void add(T elemento) {
            if (numberElement >= vector.length) {
                System.out.println("List is full!");
            }
            else {
                vector[numberElement++] = elemento;
            }
        }
        public int search(T elementoBuscado) {
            for (int i = 0; i < numberElement; i++) {
                if (vector[i].equals(elementoBuscado)) {
                }
            }
            return -1;
        }

        public boolean removeByIndex(int indice) {
            if (indice < 0 || indice >= numberElement) {
                System.out.println("\nIndex not exist!");
                return false;
            }

            for (int i = indice; i < numberElement -1; i++) {
                vector[i] = vector[i+1];
            }

            numberElement--;
            return true;
        }

        public boolean removeElemento(T elementoARemover) {
            return removeByIndex(search(elementoARemover));
        }

        public int getLength() {
            return numberElement;
        }

        public T getElement(int indice) {
            if (indice < 0 || indice >= numberElement) {
                return null;
            }
            else {
                return vector[indice];
            }
        }

        public void clean() {
            numberElement = 0;
        };

}

