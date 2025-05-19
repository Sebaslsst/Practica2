package practica.com.base.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileNotFoundException;

import com.fasterxml.jackson.databind.ser.std.EnumSerializer;

public class LinkedList <E>{
    private Node <E> head;
    private Node <E> last;
    private Integer length;

    public Integer getLength() {
        return this.length;
    }

    public LinkedList(){
        head=null;
        last=null;
        length=0;
    }

    public Boolean isEmpty(){
        return head == null || length ==0;
    }

    public void loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    this.add((E) Integer.valueOf(line.trim())); // Convertir la línea a Integer
                } catch (NumberFormatException e) {
                    System.err.println("Error el archivo tiene valores no númericos " + line);
                }
            }
        }
    }

    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Node<E> current = head;
            while (current != null) {
                writer.write(current.getData().toString());
                writer.newLine();
                current = current.getNext();
            }
        }
    }

    public void reloadFromFile(String filePath) throws IOException {
        this.clear();
        this.loadFromFile(filePath);
    }

    public Node<E> getHead() {
        return head;
    }

    private Node<E> getNode(Integer pos) {
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Lista vacìa");
            //System.out.println("Lista vacia");
            //return null;
        } else if(pos < 0 || pos >= length){
            //System.out.println("Fuera de rango");
            //return null;
            throw new ArrayIndexOutOfBoundsException("Fuera de rango..");
        } else if(pos==0){
            return head;
        }else if ((length.intValue()-1) == pos.intValue()){
            return last;
        }else{
            Node<E> search = head;
            Integer cont = 0;
            while(cont < pos){
                cont++;
                search = search.getNext();
            }
            return search; 
        }
    }
           
    private E getDataFirst(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Lista vacìa");
        }else{
            return head.getData();
        }
    }

    private E getDataLast(){
        if(isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Lista vacìa");
        }else{
            return last.getData();
        }
    }

    private E get(Integer pos) throws ArrayIndexOutOfBoundsException{
    return getNode(pos).getData();
    }   


    private void addFirst(E data){
        if(isEmpty()){
            Node<E> aux=new Node<>(data);
            head =aux;
            last=aux;
        }else{
            Node<E> head_old=head;
            Node<E> aux = new Node<>(data, head_old);
            aux.setNext(head_old);
            head=aux;
        }
        length++;
    }

    private void addLast(E data){
        if(isEmpty()){
            addFirst(data);
        }else{
            Node<E> aux=new Node<>(data);
            last.setNext(aux);
            last=aux;
            length++;
        }
    }

    public void add(E data, Integer pos) throws Exception{
        if(pos==0){
            addFirst(data);
        }else if(length.intValue()==pos.intValue()){
           addLast(data);

        }else{
            Node<E> search_preview =getNode(pos -1);
            Node<E> search =getNode(pos);

            Node<E> aux= new Node<>(data, search);
            search_preview.setNext(aux);
            length++;
        }

    }

    public void add(E data){
        addLast(data);
    }

    public String print(){
        if(isEmpty())
            return "Esta vacia";
        else{
            StringBuilder resp= new StringBuilder();
            Node<E> help=head;
            while(help != null){
                resp.append(help.getData()).append(" - ");
                help=help.getNext();
            }
            resp.append("\n");
            return resp.toString();
        }
    }

    public void update(E data, Integer pos){
        getNode(pos).setData(data);
    }

    public E[] toArray(){
        Class clazz=null;
        E[] matriz=null;
        if(this.length>0){
            clazz=head.getData().getClass();
            matriz=(E[]) java.lang.reflect.Array.newInstance(clazz, this.length);
            Node<E> aux=head;
            for(int i  = 0; i<length;i++){
                matriz[i]=aux.getData();
                aux=aux.getNext();
            }
        }
        return matriz;
    }

    public LinkedList<E> toList(E[] matriz){
        clear();
        for(int i =0; i<length; i++){
            this.add(matriz[i]);
        }
        return this;
    }
    
    public void deleteHead(){
        head=head.getNext();
        length--;

        if (head == null) { 
            last = null;
        }
    }

    public void deleteLast(){
        if (length == 1) {
            clear(); 
            length--;
            return;
        }

        Node<E> previous_Last =getNode((length.intValue())-2);
        previous_Last.setNext(null);
        last=previous_Last;
        length--;

        if (last == null) { 
            head = null;
        }
    }

    public void delete(Integer pos){
        if(pos < 0 || pos >= length){
            throw new ArrayIndexOutOfBoundsException("fuera de rango...");
        }
        if(pos==0){
            deleteHead();
        }else if(pos==length-1){
            deleteLast();
        }else{
            Node<E> previous =getNode(pos -1);
            Node<E> search =getNode(pos);

            previous.setNext(search.getNext());
            length--;
        }
        
    }

    public void clear(){
        head=null;
        last=null;
        length=0;
    }
}