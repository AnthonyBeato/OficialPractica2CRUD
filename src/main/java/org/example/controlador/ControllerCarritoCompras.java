package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Producto;
import org.example.servicios.ServiciosProducto;

import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControllerCarritoCompras extends ControllerBase {

    ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();

    public ControllerCarritoCompras (Javalin app) {
        super(app);
    }

    @Override
    public void aplicarDireccionamiento() {
        app.routes(() ->{
            path("/carritoCompras", () ->{
                post("/{idProducto}", ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    int cantidad = ctx.formParamAsClass("cantidad", Integer.class).get();
                    System.out.println("Id: "+ identificador + " Cantidad: "+ cantidad);
                    Producto tempProducto = serviciosProducto.getProductByID(identificador);
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");

                    carroCompras.addProducto(new Producto(identificador, tempProducto.getNombre(), tempProducto.getPrecio(), cantidad > tempProducto.getCantidad() ? tempProducto.getCantidad() : cantidad));
                    System.out.println("Se agrego el producto al carrito: "+ tempProducto.getNombre()+ " ID "+ tempProducto.getIdProducto() + " Cantidad: "+ tempProducto.getCantidad());
                    System.out.println("Del carrito id: "+ carroCompras.getIdCarroCompra());
                    ctx.sessionAttribute("carroCompras", carroCompras); //almacenar carro de compras en la sesión

                    ctx.redirect("/");
                });
                post("/Eliminar/{idProducto}", ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    carroCompras.deleteProducto(serviciosProducto.getProductByID(identificador));

                    ctx.sessionAttribute("carroCompras", carroCompras);
                    ctx.redirect("/carritoCompras");
                });
                get(ctx -> {
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    List<Producto> listaProductos = carroCompras.getListaProductos();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Carro de compras");
                    modelo.put("listaProductos", listaProductos);
                    modelo.put("monto", 200);
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidad());

                    System.out.println("Llego al carrito");



                    ctx.render("/templates/vista/carritoCompra.html", modelo);
                });
            });
        });
    }
}
