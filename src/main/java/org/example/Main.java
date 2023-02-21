package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.controlador.ControllerCarritoCompras;
import org.example.controlador.ControllerProducto;
import org.example.controlador.ControllerSeguridad;
import org.example.controlador.ControllerVentas;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            });
        });

        //Controladoras:
        new ControllerSeguridad(app).aplicarDireccionamiento();
        new ControllerProducto(app).aplicarDireccionamiento();
        new ControllerCarritoCompras(app).aplicarDireccionamiento();
        new ControllerVentas(app).aplicarDireccionamiento();

        app.start(7001);
    }
}