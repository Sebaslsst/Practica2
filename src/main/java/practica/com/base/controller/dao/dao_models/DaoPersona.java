package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Persona;

public class DaoPersona extends AdapterDao<Persona> {
    private Persona obj;

    public DaoPersona() {
        super(Persona.class);
        // TODO Auto-generated constructor stub
    }

    public Persona getObj() {
        if (obj == null)
            this.obj = new Persona();
        return this.obj;
    }

    public void setObj(Persona obj) {
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
        DaoPersona da = new DaoPersona();
    
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setUsuario("Leonardo");
        da.getObj().setEdad(25);
        da.getObj().setId_cuenta(1001);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    
        da.setObj(null);
    
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setUsuario("Efren");
        da.getObj().setEdad(30);
        da.getObj().setId_cuenta(1002);
        if (da.save())
            System.out.println("GUARDADO: " + da.getObj());
        else
            System.out.println("Hubo un error");
    }

}
