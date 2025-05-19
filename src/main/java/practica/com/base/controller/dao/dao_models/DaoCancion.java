package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Cancion;
import practica.com.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;

    public DaoCancion() {
        super(Cancion.class);
        // TODO Auto-generated constructor stub
    }

    public Cancion getObj() {
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {
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
            DaoCancion da = new DaoCancion();
        
            da.getObj().setId(da.listAll().getLength() + 1);
            da.getObj().setNombre("Canción 1");
            da.getObj().setId_genero(1);
            da.getObj().setDuracion(210);
            da.getObj().setUrl("https://youtu.be/UfcDJNElrl4?feature=shared");
            da.getObj().setTipo(TipoArchivoEnum.FISICO);
            da.getObj().setId_album(1001);
            if (da.save())
                System.out.println("GUARDADO: " + da.getObj());
            else
                System.out.println("Hubo un error");
        
            da.setObj(null);
        
            da.getObj().setId(da.listAll().getLength() + 1);
            da.getObj().setNombre("Canción 2");
            da.getObj().setId_genero(2);
            da.getObj().setDuracion(180);
            da.getObj().setUrl("https://youtu.be/fJ9rUzIMcZQ?feature=shared");
            da.getObj().setTipo(TipoArchivoEnum.VIRTUAL);
            da.getObj().setId_album(1002);
            if (da.save())
                System.out.println("GUARDADO: " + da.getObj());
            else
                System.out.println("Hubo un error");
        }

}
