package practica.com.base.service;


import java.util.Arrays;
import java.util.List;

import practica.com.base.controller.dao.dao_models.DaoExpresion;
import practica.com.base.models.Expresion;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable

@AnonymousAllowed
public class ExpresionService {
    private DaoExpresion de;
    public ExpresionService(){
        de = new DaoExpresion();
    }
    public void create(@NotEmpty String expresion) throws Exception{
        de.getObj().setExpresion(expresion);;
        
        if(!de.save())
            throw new  Exception("No se pudo guardar los datos de artista");
    }

    
    public List<Expresion> listAll() {  
       // System.out.println("**********Entro aqui");  
        //System.out.println("lengthy "+Arrays.asList(da.listAll().toArray()).size());    
        return (List<Expresion>)Arrays.asList(de.listAll().toArray());
    }
}
