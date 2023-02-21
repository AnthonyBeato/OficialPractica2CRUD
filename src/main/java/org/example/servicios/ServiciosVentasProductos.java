package org.example.servicios;

import org.example.encapsulacion.VentaProductos;

import java.util.ArrayList;
import java.util.List;

public class ServiciosVentasProductos {
    private static ServiciosVentasProductos instancia;
    private static ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();
    private List<VentaProductos> listVentas = new ArrayList<>();

    //Creacion de venta
    public VentaProductos createVentaProducto(VentaProductos ventaProductos){
        VentaProductos tempVenta = getVentaProductotByID(ventaProductos.getIdVentas());
        if(tempVenta != null){
            System.out.println("Venta registrada");
            return null;
        }
        System.out.println("Venta registrada ");
        listVentas.add(ventaProductos);
        for (int i = 0; i < ventaProductos.getListaProductos().size(); i++){
            serviciosProducto.updateCantidadProducto(ventaProductos.getListaProductos().get(i).getIdProducto(), ventaProductos.getListaProductos().get(i).getCantidad() * -1);
        }

        return ventaProductos;
    }

    public static ServiciosVentasProductos getInstancia() {
        if(instancia == null){
            instancia = new ServiciosVentasProductos();
        }

        return instancia;
    }

    public static void setInstancia(ServiciosVentasProductos instancia) {
        ServiciosVentasProductos.instancia = instancia;
    }

    public List<VentaProductos> getListVentas() {
        return listVentas;
    }

    public void setListVentas(List<VentaProductos> listVentas) {
        this.listVentas = listVentas;
    }

    public VentaProductos getVentaProductotByID(String id){
        for (VentaProductos venta : listVentas){
            if (venta.getIdVentas().equals(id)){
                return venta;
            }
        }
        return null;
    }
}
