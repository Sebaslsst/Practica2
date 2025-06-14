package practica.com.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import practica.com.base.controller.dao.dao_models.DaoAlbum;
import practica.com.base.controller.dao.dao_models.DaoCancion;
import practica.com.base.controller.dao.dao_models.DaoGenero;
import practica.com.base.models.Album;
import practica.com.base.models.Cancion;
import practica.com.base.models.Genero;
import practica.com.base.models.TipoArchivoEnum;
import practica.com.base.controller.datastruct.list.LinkedList;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed
@Service
public class CancionService {
    private DaoCancion dao;

    public CancionService() {
        dao = new DaoCancion();
    }

    public void createCancion(@NotEmpty String nombre, Integer id_genero, Integer duracion, @NotEmpty String url,
            @NotEmpty String tipo, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && id_genero > 0 && url.trim().length() > 0 && tipo != null && duracion > 0
                && id_album > 0) {
            dao.getObj().setNombre(nombre);
            dao.getObj().setId_genero(id_genero);
            dao.getObj().setDuracion(duracion);
            dao.getObj().setUrl(url);
            dao.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            dao.getObj().setId_album(id_album);
            dao.getObj().setId(dao.listAll().getLength() + 1);
            if (!dao.save())
                throw new Exception("No se pudo guardar la canción");
        }
    }

    public void updateCancion(Integer id, @NotEmpty String nombre, Integer id_genero, Integer duracion,
            @NotEmpty String url,
            @NotEmpty String tipo, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && id_genero > 0 && url.trim().length() > 0 && tipo != null && duracion > 0
                && id_album > 0) {
            Cancion cancionToUpdate = null;
            for (Cancion c : dao.listAll().toArray()) {
                if (c.getId().equals(id)) {
                    cancionToUpdate = c;
                    break;
                }
            }
            if (cancionToUpdate == null) {
                throw new Exception("No se encontró la canción a modificar");
            }
            dao.setObj(cancionToUpdate);
            dao.getObj().setNombre(nombre);
            dao.getObj().setId_genero(id_genero);
            dao.getObj().setDuracion(duracion);
            dao.getObj().setUrl(url);
            dao.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            dao.getObj().setId_album(id_album);
            int indexToUpdate = -1;
            Cancion[] cancionesArray = dao.listAll().toArray();
            for (int i = 0; i < cancionesArray.length; i++) {
                if (cancionesArray[i].getId().equals(cancionToUpdate.getId())) {
                    indexToUpdate = i;
                    break;
                }
            }
            if (indexToUpdate == -1 || !dao.update(indexToUpdate))
                throw new Exception("No se pudo modificar la canción");
        }
    }

    public List<Cancion> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<Cancion> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }

    /**
     * Lista canciones ordenadas por el atributo indicado usando QuickSort en el
     * DAO.
     * 
     * @param atributo   nombre del atributo de la canción (por ejemplo: "nombre",
     *                   "duracion")
     * @param ascendente true para ascendente, false para descendente
     * @return lista ordenada de canciones como HashMap
     */
    public List<HashMap<String, String>> listOrdenadoPor(String atributo, boolean ascendente) throws Exception {
        LinkedList<HashMap<String, String>> lista = dao.orderByAttribute(atributo, ascendente);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public List<String> listTiposArchivo() {
        List<String> tipos = new ArrayList<>();
        for (TipoArchivoEnum tipo : TipoArchivoEnum.values()) {
            tipos.add(tipo.toString());
        }
        return tipos;
    }

    public List<HashMap> listaAlbumCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if (!da.listAll().isEmpty()) {
            Album[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString());
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if (!da.listAll().isEmpty()) {
            Genero[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString());
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listCancion() {
        List<HashMap> lista = new ArrayList<>();
        if (!dao.listAll().isEmpty()) {
            Cancion[] arreglo = dao.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString());
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() - 1).getNombre());
                aux.put("id_genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() - 1).getId().toString());
                aux.put("album", new DaoAlbum().listAll().get(arreglo[i].getId_album() - 1).getNombre());
                aux.put("id_album", new DaoAlbum().listAll().get(arreglo[i].getId_album() - 1).getId().toString());
                aux.put("duracion", arreglo[i].getDuracion().toString());
                aux.put("tipo", arreglo[i].getTipo().toString());
                aux.put("url", arreglo[i].getUrl());
                lista.add(aux);
            }
        }
        return lista;
    }

    /**
     * Busca canciones por atributo y texto, usando búsqueda lineal o binaria.
     * 
     * @param attribute nombre del atributo
     * @param text      texto a buscar
     * @param type      tipo de búsqueda: 1=empieza con, 2=termina con, 3=binaria
     *                  exacta, otro=contiene
     * @return lista de resultados como HashMap
     */
    public List<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = dao.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }
}