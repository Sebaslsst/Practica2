package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Artista;

public class DaoArtista extends AdapterDao<Artista> {
    private Artista obj;

    public DaoArtista() {
        super(Artista.class);
        // TODO Auto-generated constructor stub
    }

    public Artista getObj() {
        if (obj == null)
            this.obj = new Artista();
        return this.obj;
    }

    public void setObj(Artista obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
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

    public static void main(String[] args) {
        DaoArtista da = new DaoArtista();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNacionalidad("Ecuatoriana");
        da.getObj().setNombres("Leonardo");
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNacionalidad("Ecuatoriana");
        da.getObj().setNombres("Sebastian");
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }

}
