package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Artista_Banda;
import practica.com.base.models.Banda;
import practica.com.base.models.RolArtistaEnum;

import java.util.Date;

public class DaoArtistaBanda extends AdapterDao<Artista_Banda> {
    private Artista_Banda obj;

    public DaoArtistaBanda() {
        super(Artista_Banda.class);
        // TODO Auto-generated constructor stub
    }

    public Artista_Banda getObj() {
        if (obj == null)
            this.obj = new Artista_Banda();
        return this.obj;
    }

    public void setObj(Artista_Banda obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

        public static void main(String[] args) throws Exception {
        DaoArtistaBanda da = new DaoArtistaBanda();
    
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setRol(RolArtistaEnum.BATERISTA); // Usa el valor adecuado de tu enum
        da.getObj().setId_artista(1);
        da.getObj().setId_banda(1);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    
        da.setObj(null);
    
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setRol(RolArtistaEnum.GUITARRISTA); // Usa el valor adecuado de tu enum
        da.getObj().setId_artista(2);
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    }
}