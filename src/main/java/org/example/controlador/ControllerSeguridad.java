package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Usuario;
import org.example.servicios.ServiciosUsuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.path;

public class ControllerSeguridad extends ControllerBase {

    ServiciosUsuario serviciosUsuario = ServiciosUsuario.getInstancia();

    public ControllerSeguridad (Javalin app){
        super(app);
    }

    @Override
    public void aplicarDireccionamiento() {
        app.routes(() -> {
            path("/", () ->{
               before(ctx -> {
                   CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                   if(carroCompras == null){
                       ctx.sessionAttribute("carroCompras", new CarroCompras(new ArrayList<>()));
                   }
               });
            });
            path("/login", () -> {
                get(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if (usuario != null) {
                        ctx.redirect("/Seguridad/Productos");
                    }
                    Map<String, Object> modelo = new HashMap<>();
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidad());
                    ctx.render("/templates/vista/login.html");
                });
            });
        });

        app.routes(() -> {
            path("/Seguridad/", () ->{
                before(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if(usuario == null){
                        ctx.redirect("/login");
                    }
                });
                post(ctx -> {
                   String nombreUsuario = ctx.formParam("usuario");
                   String password = ctx.formParam("contrasena");
                   Usuario usuario = serviciosUsuario.getInstancia().authenticateUsuario(nombreUsuario, password);
                   if(usuario != null){
                       ctx.sessionAttribute("usuario", usuario);
                       ctx.redirect("/Seguridad/Productos");
                   }else {
                       ctx.redirect("/login");
                   }
                });
            });
        });
    }
}
