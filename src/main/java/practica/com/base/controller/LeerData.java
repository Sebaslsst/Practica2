package practica.com.base.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeerData {
    

    public int[] extractData(String filePath) {
        int totalNumbers = countNumbers(filePath); // Contar cuántos números hay en el archivo
        if (totalNumbers == 0) {
            System.out.println("El archivo está vacío o no contiene números válidos.");
            return new int[0];
        }

        int[] numbers = new int[totalNumbers];
        int index = 0;

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // leemos el archivo 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    String[] parts = line.split(",");
                    for (String part : parts) {
                        // convertimos cada parte a un numero entero y almacenarlo en el arreglo
                        numbers[index++] = Integer.parseInt(part.trim());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Línea o parte no válida ignorada: " + line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + filePath);
        }

        return numbers;
    }

    // contamos cuantos datos hay en el archivo
    private int countNumbers(String filePath) {
        int count = 0;
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // leemos el archivo
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); 
                count += parts.length; // contamos cuántos numeros hay en la línea
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + filePath);
        }
        return count;
    }

    public void processFile(String filePath) {
        // extraemos los números del archivo
        int[] numbers = extractData(filePath);
    
        // verificar si se encontraron numeros
        if (numbers.length == 0) {
            System.out.println("No se encontraron números válidos en el archivo.");
            return;
        }

        // Mostramos los números extraídos
        System.out.println("Números encontrados en el archivo:");
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}