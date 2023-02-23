package org.example.encapsulacion;

import java.util.Objects;
import java.util.UUID;

public class Producto {
    private String idProducto;
    private String nombre;
    private double precio = 0;
    private int cantidad = 0;

    public Producto(String idProducto, String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
    }

    public void update(Producto tempProducto){
        idProducto = tempProducto.getIdProducto();
        nombre = tempProducto.getNombre();
        precio = tempProducto.getPrecio();
        cantidad = tempProducto.getCantidad();
    }

//    public void updateCantidad(int nuevaCantidad, int max){
//        if(nuevaCantidad > max){
//            cantidad = max;
//            return;
//        }
//        cantidad += nuevaCantidad;
//    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProducto == producto.idProducto;
    }

    public double getTotal() {
        return precio * cantidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto);
    }
}
