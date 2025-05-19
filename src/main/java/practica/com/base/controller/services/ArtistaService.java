package practica.com.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import practica.com.base.controller.dao.dao_models.DaoArtista;
import practica.com.base.models.Artista;
import practica.com.base.models.RolArtistaEnum;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class ArtistaService {
    private DaoArtista da;
    public ArtistaService() {
        da = new DaoArtista();
    }

    public void createArtista(@NotEmpty String nombre,@NotEmpty String nacionalidad) throws Exception{
        da.getObj().setNacionalidad(nacionalidad);
        da.getObj().setNombres(nombre);
        if(!da.save())
            throw new  Exception("No se pudo guardar los datos de artista");
    }

   
    public void updateArtista(@NotEmpty Integer id, @NotEmpty String nombre,@NotEmpty String nacionalidad) throws Exception {
    Artista artista = da.listAll().get(id);
    if (artista != null) {
        artista.setNombres(nombre);
        artista.setNacionalidad(nacionalidad);
        da.update(id); // o el método que corresponda
    } else {
        throw new Exception("Artista no encontrado");
    }
}

    public List<Artista> list(Pageable pageable) {        
        return Arrays.asList(da.listAll().toArray());
    }
    public List<Artista> listAll() {  
       // System.out.println("**********Entro aqui");  
        //System.out.println("lengthy "+Arrays.asList(da.listAll().toArray()).size());    
        return (List<Artista>)Arrays.asList(da.listAll().toArray());
    }

    public List<String> listCountry() {
        List<String> nacionalidades = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            nacionalidades.add(locale.getDisplayCountry());
           // System.out.println("Country Code: " + locale.getCountry() + ", Country Name: " + locale.getDisplayCountry());
        }
        
        return nacionalidades;
    }

    public List<String> listRolArtista() {
        List<String> lista = new ArrayList<>();
        for(RolArtistaEnum r: RolArtistaEnum.values()) {
            lista.add(r.toString());
        }        
        return lista;
    }
}