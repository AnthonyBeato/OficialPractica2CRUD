package org.example.servicios;

import org.example.encapsulacion.Producto;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;

public class ServiciosProducto {
    private static ServiciosProducto instancia;

    private List<Producto> listaProductos = new ArrayList<>();

    private ServiciosProducto(){
        //Listado de productos preinsertados
        listaProductos.add(new Producto( UUID.randomUUID().toString(),"Ram 8GB",1500, 1));
        listaProductos.add(new Producto(UUID.randomUUID().toString(),"Laptop Asus",5000, 1));
        listaProductos.add(new Producto(UUID.randomUUID().toString(), "Celular",7000, 1));
    }

    //Creación de producto
    public Producto createProducto(Producto producto){
        Producto tempProducto = getProductByID(producto.getIdProducto());
        if (tempProducto != null){
            return null;
        }
        System.out.println("Producto registrado :)");
        listaProductos.add(producto);
        return producto;
    }

    //Modificar producto
    public Producto updateProducto(Producto producto){
        String identificador = producto.getIdProducto();
        Producto tempProducto = getProductByID(identificador);
        System.out.println("Llego a updateProducto");
        System.out.println("EL id producto pasado por parametro "+ producto.getIdProducto());
        System.out.println("El id de tempProducto"+ tempProducto.getIdProducto().toString());

        //System.out.println("Dentro de updateProducto, el id es:" +tempProducto.getIdProducto());
        if(tempProducto == null){
            System.out.println("Error, el producto que elegiste no es existente");
            return null;
        }
        System.out.println("Se modifico el producto");
        tempProducto.update(producto);
        return producto;

    }

    //Eliminar producto
    public boolean deleteProducto(String identificador){
        Producto tempProducto = getProductByID(identificador);
        if(tempProducto == null){
            System.out.println("Producto no existente");
            return false;
        }
        System.out.println("Producto eliminado :)");
        listaProductos.remove(tempProducto);
        return true;
    }

    //Actualizar cantidad de productos
    public void updateCantidadProducto(String identificador, int cantidad){
        Producto tempProducto = getProductByID(identificador);
        if(tempProducto == null){
            System.out.println("Producto no existente :/");
        }else{
            System.out.println("Cantidad cambiada con exito!");
            tempProducto.updateCantidad(cantidad, tempProducto.getCantidad());
        }
    }

    public static ServiciosProducto getInstancia() {
        if(instancia == null){
            instancia = new ServiciosProducto();
        }

        return instancia;
    }

    public static void setInstancia(ServiciosProducto instancia) {
        ServiciosProducto.instancia = instancia;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Producto getProductByID(String id){

        for (Producto producto : listaProductos){
            System.out.println(id + "  -  " + producto.getIdProducto());
            if (producto.getIdProducto().equals(id)){
                System.out.println("Llego el producto por ID: "+ producto.getIdProducto());
                return producto;
            }
        }
        System.out.println("FuncionGetProdcutByID: Es nulo el id");
        return null;
    }

//    public Producto getProductByID(String id) {
//        return listaProductos.stream()
//                .filter(producto -> producto.getIdProducto().equals(id))
//                .findFirst()
//                .orElse(null);
//    }

    public Producto getProductByNombre(String nombre){
        for (Producto producto : listaProductos){
            if (producto.getNombre().equals(nombre)){
                return producto;
            }
        }
        return null;
    }

}
