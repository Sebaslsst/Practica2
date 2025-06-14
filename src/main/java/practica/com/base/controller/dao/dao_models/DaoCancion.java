package practica.com.base.controller.dao.dao_models;

import java.util.HashMap;
import practica.com.base.controller.datastruct.list.LinkedList;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Cancion;
import practica.com.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;

    public DaoCancion() {
        super(Cancion.class);
    }

    public Cancion getObj() {
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LinkedList<HashMap<String, String>> orderByAttribute(String attribute, boolean asc) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            if (asc) {
                quickSortHashMapASC(arr, 0, arr.length - 1, attribute);
            } else {
                quickSortHashMapDES(arr, 0, arr.length - 1, attribute);
            }
            lista.clear();
            for (HashMap<String, String> m : arr) {
                lista.add(m);
            }
        }
        return lista;
    }

    private void quickSortHashMapASC(HashMap<String, String>[] arr, int inicio, int fin, String attribute) {
        if (inicio >= fin) return;
        HashMap<String, String> pivote = arr[inicio];
        int izq = inicio + 1;
        int der = fin;
        while (izq <= der) {
            while (izq <= fin && arr[izq].get(attribute).toString().toLowerCase()
                    .compareTo(pivote.get(attribute).toString().toLowerCase()) < 0) {
                izq++;
            }
            while (der > inicio && arr[der].get(attribute).toString().toLowerCase()
                    .compareTo(pivote.get(attribute).toString().toLowerCase()) >= 0) {
                der--;
            }
            if (izq < der) {
                HashMap<String, String> temp = arr[izq];
                arr[izq] = arr[der];
                arr[der] = temp;
            }
        }
        if (der > inicio) {
            HashMap<String, String> temp = arr[inicio];
            arr[inicio] = arr[der];
            arr[der] = temp;
        }
        quickSortHashMapASC(arr, inicio, der - 1, attribute);
        quickSortHashMapASC(arr, der + 1, fin, attribute);
    }


    private void quickSortHashMapDES(HashMap<String, String>[] arr, int inicio, int fin, String attribute) {
        if (inicio >= fin) return;
        HashMap<String, String> pivote = arr[inicio];
        int izq = inicio + 1;
        int der = fin;
        while (izq <= der) {
            while (izq <= fin && arr[izq].get(attribute).toString().toLowerCase()
                    .compareTo(pivote.get(attribute).toString().toLowerCase()) > 0) {
                izq++;
            }
            while (der > inicio && arr[der].get(attribute).toString().toLowerCase()
                    .compareTo(pivote.get(attribute).toString().toLowerCase()) <= 0) {
                der--;
            }
            if (izq < der) {
                HashMap<String, String> temp = arr[izq];
                arr[izq] = arr[der];
                arr[der] = temp;
            }
        }
        if (der > inicio) {
            HashMap<String, String> temp = arr[inicio];
            arr[inicio] = arr[der];
            arr[der] = temp;
        }
        quickSortHashMapDES(arr, inicio, der - 1, attribute);
        quickSortHashMapDES(arr, der + 1, fin, attribute);
    }

    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arr = lista.toArray();
            switch (type) {
                case 1: // Empieza con
                    for (HashMap<String, String> m : arr) {
                        if (m.get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }
                    break;
                case 2: // Termina con
                    for (HashMap<String, String> m : arr) {
                        if (m.get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }
                    break;
                case 3: // Búsqueda binaria exacta (requiere orden previo)
                    quickSortHashMapASC(arr, 0, arr.length - 1, attribute);
                    int idx = binarySearchHashMap(arr, attribute, text.toLowerCase());
                    if (idx != -1) {
                        resp.add(arr[idx]);
                    }
                    break;
                default: // Contiene
                    for (HashMap<String, String> m : arr) {
                        if (m.get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(m);
                        }
                    }
                    break;
            }
        }
        return resp;
    }


    private int binarySearchHashMap(HashMap<String, String>[] arr, String attribute, String text) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            String val = arr[mid].get(attribute).toString().toLowerCase();
            int cmp = val.compareTo(text);
            if (cmp == 0) return mid;
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<Cancion> canciones = listAll();
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!canciones.isEmpty()) {
            for (Cancion cancion : canciones.toArray()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(cancion.getId()));
                map.put("nombre", cancion.getNombre());
                map.put("id_genero", String.valueOf(cancion.getId_genero()));
                map.put("genero", new DaoGenero().listAll().get(cancion.getId_genero() - 1).getNombre());
                map.put("duracion", String.valueOf(cancion.getDuracion()));
                map.put("url", cancion.getUrl());
                map.put("tipo", cancion.getTipo() != null ? cancion.getTipo().toString() : "");
                map.put("id_album", String.valueOf(cancion.getId_album()));
                map.put("album", new DaoAlbum().listAll().get(cancion.getId_album() - 1).getNombre());
                lista.add(map);
            }
        }
        return lista;
    }

    public static void main(String[] args) {
        DaoCancion da = new DaoCancion();

        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Canción 1");
        da.getObj().setId_genero(1);
        da.getObj().setDuracion(210);
        da.getObj().setUrl("https://youtu.be/UfcDJNElrl4?feature=shared");
        da.getObj().setTipo(TipoArchivoEnum.FISICO);
        da.getObj().setId_album(1001);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");

        da.setObj(null);

        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Canción 2");
        da.getObj().setId_genero(2);
        da.getObj().setDuracion(180);
        da.getObj().setUrl("https://youtu.be/fJ9rUzIMcZQ?feature=shared");
        da.getObj().setTipo(TipoArchivoEnum.VIRTUAL);
        da.getObj().setId_album(1002);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    }
}