package practica.com.base.controller;

import java.io.*;
import java.util.*;

public class Practica {

    static class Node<E> {
        E data;
        Node<E> next;
        Node(E data) { this.data = data; }
    }

    static class LinkedList<E> {
        private Node<E> head;
        private int length = 0;

        public void add(E data) {
            Node<E> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
            } else {
                Node<E> current = head;
                while (current.next != null) current = current.next;
                current.next = newNode;
            }
            length++;
        }

        public E get(int index) {
            Node<E> current = head;
            int i = 0;
            while (current != null && i < index) {
                current = current.next;
                i++;
            }
            return current != null ? current.data : null;
        }

        public int getLength() {
            return length;
        }

        public Node<E> getHead() {
            return head;
        }

        public void loadFromFile(String filePath) throws IOException {
            head = null;
            length = 0;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("[,\\s]+");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        add((E) Integer.valueOf(part));
                    }
                }
            }
            br.close();
        }
    }

    public static List<Integer> detectarYGuardarRepetidos(LinkedList<Integer> list) {
        HashSet<Integer> unicos = new HashSet<>();
        List<Integer> repetidos = new ArrayList<>();
        Node<Integer> actual = list.getHead();
        while (actual != null) {
            int valor = actual.data;
            if (!unicos.add(valor)) {
                if (!repetidos.contains(valor)) {
                    repetidos.add(valor);
                }
            }
            actual = actual.next;
        }
        return repetidos;
    }

    public static List<Integer> detectarRepetidosArreglo(int[] arr) {
        HashSet<Integer> unicos = new HashSet<>();
        List<Integer> repetidos = new ArrayList<>();
        for (int valor : arr) {
            if (!unicos.add(valor)) {
                if (!repetidos.contains(valor)) {
                    repetidos.add(valor);
                }
            }
        }
        return repetidos;
    }

    public static int[] cargarArregloDesdeArchivo(String filePath) throws IOException {
        List<Integer> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("[,\\s]+");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    lista.add(Integer.valueOf(part));
                }
            }
        }
        br.close();
        int[] arr = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) arr[i] = lista.get(i);
        return arr;
    }

    public static void main(String[] args) {
        String filePath = "data.txt";
        long[] tiemposLista = new long[3];
        long[] tiemposArreglo = new long[3];

        for (int i = 1; i <= 3; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            long tiempoInicio = System.currentTimeMillis();
            try {
                list.loadFromFile(filePath);
            } catch (IOException e) {
                System.out.println("Error cargando el archivo: " + e.getMessage());
                return;
            }
            List<Integer> repetidosLista = detectarYGuardarRepetidos(list);
            long totalTiempo = System.currentTimeMillis() - tiempoInicio;
            tiemposLista[i - 1] = totalTiempo;
            System.out.println("Lista enlazada - Ejecución " + i + ":\t" + totalTiempo + " ms");
            System.out.println("Repetidos en lista enlazada: " + repetidosLista);
            System.out.println("Cantidad de elementos repetidos en lista enlazada: " + repetidosLista.size());
        }

        for (int i = 1; i <= 3; i++) {
            long tiempoInicio = System.currentTimeMillis();
            int[] arr;
            try {
                arr = cargarArregloDesdeArchivo(filePath);
            } catch (IOException e) {
                System.out.println("Error cargando el archivo: " + e.getMessage());
                return;
            }
            List<Integer> repetidosArr = detectarRepetidosArreglo(arr);
            long totalTiempo = System.currentTimeMillis() - tiempoInicio;
            tiemposArreglo[i - 1] = totalTiempo;
            System.out.println("Arreglo - Ejecución " + i + ":\t" + totalTiempo + " ms");
            System.out.println("Repetidos en arreglo: " + repetidosArr);
            System.out.println("Cantidad de elementos repetidos en arreglo: " + repetidosArr.size());
        }

        System.out.println("\nTabla comparativa de tiempos:");
        System.out.println("Ejecución\tLista enlazada (ms)\tArreglo (ms)");
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + "\t\t" + tiemposLista[i] + "\t\t\t" + tiemposArreglo[i]);
        }
    }
}