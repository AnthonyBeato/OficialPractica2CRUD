package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Producto;
import org.example.encapsulacion.VentaProductos;
import org.example.servicios.ServiciosVentasProductos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControllerVentas extends ControllerBase {

    ServiciosVentasProductos serviciosVentasProductos = ServiciosVentasProductos.getInstancia();

    public ControllerVentas (Javalin app){
        super (app);
    }
    @Override
    public void aplicarDireccionamiento() {
        app.routes(() ->{
            path("/Ventas", () ->{
               post(ctx -> {
                   String nombre = ctx.formParam("nombre");
                   CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");

                   VentaProductos ventaProductos = new VentaProductos(new Date(), nombre, carroCompras.getListaProductos());
                   serviciosVentasProductos.createVentaProducto(ventaProductos);
                   ctx.sessionAttribute("carroCompras", new CarroCompras(carroCompras.getListaProductos()));
                   ctx.redirect("/");
               });
            });
        });

        app.routes(() ->{
            path("Seguridad/Pedidos", () ->{
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de productos disponibles");
                    modelo.put("listaProductos", serviciosVentasProductos.getListVentas());
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidad());
                    ctx.render("/templates/vista/ventas.html");
                });
            });
        });
    }
}
