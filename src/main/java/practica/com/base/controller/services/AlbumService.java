package practica.com.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import practica.com.base.controller.dao.dao_models.DaoAlbum;
import practica.com.base.models.Album;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class AlbumService {
    private DaoAlbum dao;

    public AlbumService() {
        dao = new DaoAlbum();
    }

    public void createAlbum(@NotEmpty String nombre, @NonNull Date fecha, @NotEmpty Integer id_banda) throws Exception {
        if (nombre.trim().length() > 0 && fecha != null && id_banda != null) {
            dao.getObj().setNombre(nombre);
            dao.getObj().setFecha(fecha);
            dao.getObj().setId_banda(id_banda);
            dao.getObj().setId(dao.listAll().getLength() + 1);
            if (!dao.save())
                throw new Exception("No se pudo guardar el álbum");
        }
    }

    public void updateAlbum(@NotEmpty Integer id, @NotEmpty String nombre, @NonNull Date fecha, @NotEmpty Integer id_banda) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && fecha != null && id_banda != null) {
            dao.setObj(dao.listAll().get(id - 1));
            dao.getObj().setNombre(nombre);
            dao.getObj().setFecha(fecha);
            dao.getObj().setId_banda(id_banda);
            if (!dao.update(id - 1))
                throw new Exception("No se pudo modificar el álbum");
        }
    }

    public List<Album> listAllAlbum() {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<HashMap<String, String>> listAlbum() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        if (!dao.listAll().isEmpty()) {
            Album[] arreglo = dao.listAll().toArray();
            for (Album album : arreglo) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", album.getId() != null ? album.getId().toString() : "");
                aux.put("nombre", album.getNombre() != null ? album.getNombre() : "");
                aux.put("fecha", album.getFecha() != null ? album.getFecha().toString() : "");
                aux.put("id_banda", album.getId_banda() != null ? album.getId_banda().toString() : "");
                lista.add(aux);
            }
        }
        return lista;
    }
}