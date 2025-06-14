package practica.com.base.controller;

import practica.com.base.controller.datastruct.list.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Practica {

    public static LinkedList<Integer> cargarListaDesdeArchivo(String filePath) {
        LinkedList<Integer> lista = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("[,\\s]+");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        lista.add(Integer.valueOf(part));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error cargando el archivo: " + e.getMessage());
        }
        return lista;
    }

    public static LinkedList<Integer> detectarRepetidosArregloOrdenado(int[] arr) {
        LinkedList<Integer> repetidos = new LinkedList<>();
        if (arr.length == 0)
            return repetidos;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                if (repetidos.getLength() == 0 || !repetidos.get(repetidos.getLength() - 1).equals(arr[i])) {
                    repetidos.add(arr[i]);
                }
            }
        }
        return repetidos;
    }

    public static void quickSort(int[] arr, int inicio, int fin) {
        if (inicio >= fin)
            return;
        int pivote = arr[inicio];
        int izq = inicio + 1;
        int der = fin;
        while (izq <= der) {
            while (izq <= fin && arr[izq] < pivote)
                izq++;
            while (der > inicio && arr[der] >= pivote)
                der--;
            if (izq < der) {
                int temp = arr[izq];
                arr[izq] = arr[der];
                arr[der] = temp;
            }
        }
        if (der > inicio) {
            int temp = arr[inicio];
            arr[inicio] = arr[der];
            arr[der] = temp;
        }
        quickSort(arr, inicio, der - 1);
        quickSort(arr, der + 1, fin);
    }

    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "data.txt";
        long tiempoLista, tiempoArreglo, tiempoQuick, tiempoShell;

        long tiempoInicioLista = System.currentTimeMillis();
        LinkedList<Integer> lista = cargarListaDesdeArchivo(filePath);
        int[] arrLista = new int[lista.getLength()];
        for (int k = 0; k < lista.getLength(); k++)
            arrLista[k] = lista.get(k);
        quickSort(arrLista, 0, arrLista.length - 1);
        LinkedList<Integer> repetidosLista = detectarRepetidosArregloOrdenado(arrLista);
        tiempoLista = System.currentTimeMillis() - tiempoInicioLista;
        System.out.println("Lista enlazada:");
        System.out.print("Valores repetidos detectados: ");
        for (int j = 0; j < repetidosLista.getLength(); j++) {
            System.out.print(repetidosLista.get(j));
            if (j < repetidosLista.getLength() - 1)
                System.out.print(", ");
        }
        System.out.println();
        System.out.println("Cantidad de elementos repetidos: " + repetidosLista.getLength());
        System.out.println("Tiempo de ejecución: " + tiempoLista + " ms\n");

        long tiempoInicioArr = System.currentTimeMillis();
        LinkedList<Integer> listaArr = cargarListaDesdeArchivo(filePath);
        int[] arr = new int[listaArr.getLength()];
        for (int k = 0; k < listaArr.getLength(); k++)
            arr[k] = listaArr.get(k);
        quickSort(arr, 0, arr.length - 1);
        LinkedList<Integer> repetidosArr = detectarRepetidosArregloOrdenado(arr);
        tiempoArreglo = System.currentTimeMillis() - tiempoInicioArr;
        System.out.println("Arreglo:");
        System.out.print("Valores repetidos detectados: ");
        for (int j = 0; j < repetidosArr.getLength(); j++) {
            System.out.print(repetidosArr.get(j));
            if (j < repetidosArr.getLength() - 1)
                System.out.print(", ");
        }
        System.out.println();
        System.out.println("Cantidad de elementos repetidos: " + repetidosArr.getLength());
        System.out.println("Tiempo de ejecución: " + tiempoArreglo + " ms\n");

        LinkedList<Integer> listaQ = cargarListaDesdeArchivo(filePath);
        int[] arrQS = new int[listaQ.getLength()];
        for (int j = 0; j < listaQ.getLength(); j++)
            arrQS[j] = listaQ.get(j);
        long t1 = System.currentTimeMillis();
        quickSort(arrQS, 0, arrQS.length - 1);
        long t2 = System.currentTimeMillis();
        tiempoQuick = t2 - t1;

        LinkedList<Integer> listaS = cargarListaDesdeArchivo(filePath);
        int[] arrShell = new int[listaS.getLength()];
        for (int j = 0; j < listaS.getLength(); j++)
            arrShell[j] = listaS.get(j);
        long t3 = System.currentTimeMillis();
        shellSort(arrShell);
        long t4 = System.currentTimeMillis();
        tiempoShell = t4 - t3;

        System.out.println("\nTabla de tiempos:");
        System.out.println("QuickSort (ms)\tShellSort (ms)");
        System.out.println(tiempoQuick + "\t\t" + tiempoShell);
    }
}