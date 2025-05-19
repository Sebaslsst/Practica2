package practica.com.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import practica.com.base.controller.dao.dao_models.DaoCancion;
import practica.com.base.models.Cancion;
import practica.com.base.models.TipoArchivoEnum;

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

    public void createCancion(@NotEmpty String nombre, @NotEmpty Integer id_genero, @NotEmpty Integer duracion, @NotEmpty String url, 
    @NotEmpty TipoArchivoEnum tipo, @NotEmpty Integer id_album) throws Exception {
        dao.getObj().setNombre(nombre);
        dao.getObj().setId_genero(id_genero);
        dao.getObj().setDuracion(duracion);
        dao.getObj().setUrl(url);
        dao.getObj().setTipo(tipo);
        dao.getObj().setId_album(id_album);
        dao.getObj().setId(dao.listAll().getLength() + 1);
        if (!dao.save())
            throw new Exception("No se pudo guardar la canción");
    }

    public void updateCancion(@NotEmpty Integer id, @NotEmpty String nombre, @NotEmpty Integer id_genero, @NotEmpty Integer duracion, @NotEmpty String url, 
    @NotEmpty TipoArchivoEnum tipo, @NotEmpty Integer id_album) throws Exception {
        dao.setObj(dao.listAll().get(id));
        dao.getObj().setNombre(nombre);
        dao.getObj().setId_genero(id_genero);
        dao.getObj().setDuracion(duracion);
        dao.getObj().setUrl(url);
        dao.getObj().setTipo(tipo);
        dao.getObj().setId_album(id_album);
        if (!dao.update(id))
            throw new Exception("No se pudo modificar la canción");
    }

    public List<Cancion> list(Pageable pageable) {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<Cancion> listAll() {
        return Arrays.asList(dao.listAll().toArray());
    }

    public List<String> listTiposArchivo() {
        List<String> tipos = new ArrayList<>();
        for (TipoArchivoEnum tipo : TipoArchivoEnum.values()) {
            tipos.add(tipo.toString());
        }
        return tipos;
    }
}