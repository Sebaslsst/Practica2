package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;

    public DaoGenero() {
        super(Genero.class);
        // TODO Auto-generated constructor stub
    }

    public Genero getObj() {
        if (obj == null)
            this.obj = new Genero();
        return this.obj;
    }

    public void setObj(Genero obj) {
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
        DaoGenero da = new DaoGenero();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Rock");
        da.getObj().setId_cancion(1);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Pop");
        da.getObj().setId_cancion(2);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Salsa");
        da.getObj().setId_cancion(3);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");    
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Baladas");
        da.getObj().setId_cancion(4);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");    
    }
}
