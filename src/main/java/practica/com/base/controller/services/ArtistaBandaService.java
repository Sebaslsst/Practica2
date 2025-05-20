package practica.com.base.controller.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import practica.com.base.controller.dao.dao_models.DaoArtista;
import practica.com.base.controller.dao.dao_models.DaoArtistaBanda;
import practica.com.base.controller.dao.dao_models.DaoBanda;
import practica.com.base.models.Artista;
import practica.com.base.models.Artista_Banda;
import practica.com.base.models.Banda;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;


@BrowserCallable
@AnonymousAllowed
public class ArtistaBandaService {
    private DaoArtistaBanda db;
    public ArtistaBandaService(){
        db = new DaoArtistaBanda();
    }
    public List<HashMap> listAll(){
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()) {
            Artista_Banda [] arreglo = db.listAll().toArray();
            DaoArtista da = new DaoArtista();
            DaoBanda db = new DaoBanda();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString(i));
                aux.put("rol", arreglo[i].getRol().toString());
                aux.put("artista", da.listAll().get(arreglo[i].getId_artista() -1).getNombres());
                aux.put("banda", db.listAll().get(arreglo[i].getId_banda() -1).getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }
    
}