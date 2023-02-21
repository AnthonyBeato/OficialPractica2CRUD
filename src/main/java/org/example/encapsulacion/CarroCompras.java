package org.example.encapsulacion;

import org.example.servicios.ServiciosProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarroCompras {

    private String idCarroCompra;
    private List<Producto> listaProductos = new ArrayList<>();

//    public CarroCompras() {
//        this.listaProductos = listaProductos;
//        this.idCarroCompra = UUID.randomUUID().toString();
//    }

    public CarroCompras(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        this.idCarroCompra = UUID.randomUUID().toString();
    }

    public String getIdCarroCompra() {
        return idCarroCompra;
    }

    public void setIdCarroCompra(String idCarroCompra) {
        this.idCarroCompra = idCarroCompra;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void addProducto(Producto producto){
        if(listaProductos != null){
            for (int i = 0; i < listaProductos.size(); i++){
                if(listaProductos.get(i).getIdProducto() == producto.getIdProducto()){
                    listaProductos.get(i).updateCantidad(listaProductos.get(i).getCantidad() + producto.getCantidad(), ServiciosProducto.getInstancia().getProductByID(producto.getIdProducto()).getCantidad());
                    System.out.println("Se añadio item "+ i);
                }
            }
        }
    }

    public void deleteProducto(Producto producto){
        listaProductos.remove(producto);
    }

    public double getPrecioIndividual(){
        double total = 0;
        double precio = 0;
        int cantidad = 0;

        for (int i = 0; i < listaProductos.size(); i++){
            precio = listaProductos.get(i).getPrecio();
            cantidad = listaProductos.get(i).getCantidad();
            total += precio * cantidad;
        }

        return total;
    }

    public double getTotalCarroCompra(){
        double total = 0;
        for(int i = 0; i< listaProductos.size(); i++){
            total += listaProductos.get(i).getPrecio();
        }
        return total;
    }

    public int getCantidad(){
        int cantidad = 0;
        for (int i =0; i< listaProductos.size(); i++){
            cantidad += listaProductos.get(i).getCantidad();
        }
        return cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarroCompras that = (CarroCompras) o;
        return idCarroCompra == that.idCarroCompra;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarroCompra);
    }
}
