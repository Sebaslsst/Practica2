package practica.com.base.controller;

import java.io.IOException;
import java.util.List;
import practica.com.base.controller.LinkedList;
import practica.com.base.controller.repetidos;
import java.util.HashSet;




public class Main {
    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList<>();
        repetidos repetidosObj = new repetidos();
        String filePath = "data.txt";

        //llamar al arreglo

        

        long[] tiempos = new long[3];

        for (int i = 1; i <= 3; i++) {
            long tiempoInicio = System.currentTimeMillis();


            //repetidos obj = new repetidos();
            //obj.llenarDesdeArchivo(filePath);
            //obj.imprimirEstructuras();
            try{
                list.loadFromFile(filePath); // Ensure the file contains integers
                System.out.println("Datos cargados desde el archivo.");
                

                /*list.add("Nuevo dato");
                System.out.println("Nuevo dato agregado.");*/
                

                //list.saveToFile(filePath);
                //System.out.println("Datos guardados en el archivo.");



            }
            catch (IOException e){
                System.out.println("Error cargando el archivo " + e.getMessage());
            }


            long totalTiempo = System.currentTimeMillis() - tiempoInicio;
            tiempos[i - 1] = totalTiempo;
            System.out.println("En Ejecución " + i + ":\t" + totalTiempo + " ms");
        }

        // Detectar y guardar elementos repetidos
        List<Integer> elementosRepetidos = repetidosObj.detectarYGuardarRepetidos(list);
        System.out.println("Se repiten: " + elementosRepetidos);
        System.out.println("Total de elementos repetidos: " + elementosRepetidos.size());

        // Mover la tabla comparativa al final
        System.out.println("\nTabla comparativa de tiempos:");
        System.out.println("Ejecución\tTiempo (ms)");
        for (int i = 0; i < tiempos.length; i++) {
            System.out.println((i + 1) + "\t\t" + tiempos[i] + " ms");
        }
    }
}