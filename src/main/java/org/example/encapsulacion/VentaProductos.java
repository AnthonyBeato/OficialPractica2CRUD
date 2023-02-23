package org.example.encapsulacion;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VentaProductos {
    private String idVentas;
    private Date fechaCompra;
    private String nombreCliente;

    private List<Producto> listaProductos;

    private double total = 0;

    public VentaProductos(Date fechaCompra, String nombreCliente, List<Producto> listaProductos, double total) {
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
        this.idVentas = UUID.randomUUID().toString();
        for (int i =0; i< listaProductos.size(); i++){
            total += listaProductos.get(i).getTotal();
        }
        this.total = total;
    }

    public String getIdVentas() {
        return idVentas;
    }

    public void setIdVentas(String idVentas) {
        this.idVentas = idVentas;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaProductos that = (VentaProductos) o;
        return idVentas == that.idVentas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVentas);
    }
}
