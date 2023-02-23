package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Producto;
import org.example.encapsulacion.Usuario;
import org.example.encapsulacion.VentaProductos;
import org.example.servicios.ServiciosUsuario;
import org.example.servicios.ServiciosVentasProductos;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControllerVentas extends ControllerBase {

    ServiciosVentasProductos serviciosVentasProductos = ServiciosVentasProductos.getInstancia();

    public ControllerVentas (Javalin app){
        super (app);
    }
    @Override
    public void aplicarDireccionamiento() {
        app.routes(() ->{
            path("/Seguridad/realizarVenta", () ->{
                post(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");

                    if(ctx.sessionAttribute("usuario") != null){
                        VentaProductos ventaProductos = new VentaProductos(new Date(), usuario.getNombre().toString(), carroCompras.getListaProductos());
                        serviciosVentasProductos.createVentaProducto(ventaProductos);
                        ctx.sessionAttribute("carroCompras", new CarroCompras());
                        System.out.println(ventaProductos.getNombreCliente());
                        for (int i = 0; i < ventaProductos.getListaProductos().size(); i++) {
                            System.out.println(ventaProductos.getListaProductos().get(i).getNombre());
                        }
                        ctx.redirect("/");
                    } else {
                        ctx.redirect("/error");
                    }
                });
            });

            get("/Ventas", ctx -> {
                List<VentaProductos> ventas = serviciosVentasProductos.getListVentas();
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("ventas", ventas);
                //modelo.put("listaProductos", ventas.get);
                //modelo.put("productos", ventas.)

                Usuario usuarioActual = ctx.sessionAttribute("usuario");

                if(usuarioActual != null && usuarioActual.getUsuario().equals("admin")){
                    ctx.render("/templates/vista/ventas.html", modelo);
                }else{
                    ctx.render("/templates/vista/error.html");
                }
            });
        });

    }
}
