package practica.com.base.controller;

import practica.com.base.controller.LinkedList;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class repetidos {



    public List<Integer> detectarYGuardarRepetidos(LinkedList<Integer> list) {
        HashSet<Integer> elementosUnicos = new HashSet<>();
        List<Integer> elementosRepetidos = new ArrayList<>();

        Node<Integer> actual = list.getHead(); // Usar el método getHead() para acceder al nodo inicial
        while (actual != null) {
            int valor = actual.getData();
            if (!elementosUnicos.add(valor)) {
                if (!elementosRepetidos.contains(valor)) {
                    elementosRepetidos.add(valor);
                }
            }
            actual = actual.getNext();
        }

        return elementosRepetidos; // Retornar la lista de elementos repetidos
    }

}