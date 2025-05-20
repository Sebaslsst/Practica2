package practica.com.base.controller.dao.dao_models;

import practica.com.base.controller.dao.AdapterDao;
import practica.com.base.models.Album;

public class DaoAlbum extends AdapterDao<Album> {
    private Album obj;

    public DaoAlbum() {
        super(Album.class);
        // TODO Auto-generated constructor stub
    }

    public Album getObj() {
        if (obj == null)
            this.obj = new Album();
        return this.obj;
    }

    public void setObj(Album obj) {
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
        DaoAlbum da = new DaoAlbum();

        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Inoculado");
        da.getObj().setFecha(new java.util.Date());
        da.getObj().setId_banda(1);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");

        da.setObj(null);

        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("The KAISEN");
        da.getObj().setFecha(new java.util.Date());
        da.getObj().setId_banda(2);
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }

}
