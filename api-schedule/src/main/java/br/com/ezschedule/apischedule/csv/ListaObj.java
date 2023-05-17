package br.com.ezschedule.apischedule.csv;
public class ListaObj <T> {

        private T[] vetor;
        private int nroElem;

        public ListaObj(int tamanho) {
            vetor = (T[]) new Object[tamanho];
            nroElem = 0;
        }

        public void adiciona(T elemento) {
            if (nroElem >= vetor.length) {
                System.out.println("Lista está cheia");
            }
            else {
                vetor[nroElem++] = elemento;
            }
        }
        public int busca(T elementoBuscado) {
            for (int i = 0; i < nroElem; i++) {
                if (vetor[i].equals(elementoBuscado)) {
                }
            }
            return -1;
        }

        public boolean removePeloIndice (int indice) {
            if (indice < 0 || indice >= nroElem) {
                System.out.println("\nIndice invalido!");
                return false;
            }

            for (int i = indice; i < nroElem-1; i++) {
                vetor[i] = vetor[i+1];
            }

            nroElem--;
            return true;
        }

        public boolean removeElemento(T elementoARemover) {
            return removePeloIndice(busca(elementoARemover));
        }

        public int getTamanho() {
            return nroElem;
        }

        public T getElemento(int indice) {
            if (indice < 0 || indice >= nroElem) {
                return null;
            }
            else {
                return vetor[indice];
            }
        }

        public void limpa() {
            nroElem = 0;
        };

}
