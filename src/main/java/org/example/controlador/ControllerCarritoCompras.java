package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Producto;
import org.example.servicios.ServiciosProducto;

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
//        app.routes(() ->{
//            path("/carritoCompras", () ->{
//                post("/{idProducto}", ctx -> {
//                    String identificador = ctx.pathParam("idProducto");
//                    int cantidad = ctx.formParamAsClass("cantidad", Integer.class).get();
//                    System.out.println("Id: "+ identificador + " Cantidad: "+ cantidad);
//                    Producto tempProducto = serviciosProducto.getProductByID(identificador);
//                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
//
//                    carroCompras.addProducto(new Producto(identificador, tempProducto.getNombre(), tempProducto.getPrecio(), cantidad > tempProducto.getCantidad() ? tempProducto.getCantidad() : cantidad));
//                    System.out.println("Se agrego el producto al carrito: "+ tempProducto.getNombre()+ " ID "+ tempProducto.getIdProducto() + " Cantidad: "+ tempProducto.getCantidad());
//                    System.out.println("Del carrito id: "+ carroCompras.getIdCarroCompra());
//                    ctx.sessionAttribute("carroCompras", carroCompras); //almacenar carro de compras en la sesión
//
//                    ctx.redirect("/");
//                });
//                post("/Eliminar/{idProducto}", ctx -> {
//                    String identificador = ctx.pathParam("idProducto");
//                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
//                    carroCompras.deleteProducto(serviciosProducto.getProductByID(identificador));
//
//                    ctx.sessionAttribute("carroCompras", carroCompras);
//                    ctx.redirect("/carritoCompras");
//                });
//                get(ctx -> {
//                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
//                    List<Producto> listaProductos = carroCompras.getListaProductos();
//                    Map<String, Object> modelo = new HashMap<>();
//                    modelo.put("titulo", "Carro de compras");
//                    modelo.put("listaProductos", listaProductos);
//                    modelo.put("monto", 200);
//                    modelo.put("cantidadProdCarrito", carroCompras.getCantidad());
//
//                    System.out.println("Llego al carrito");
//
//
//
//                    ctx.render("/templates/vista/carritoCompra.html", modelo);
//                });
//            });
//        });

        //Corrección funcional
        app.routes(() ->{
            path("/carritoCompras",  () ->{
                //Abrir carrito de compras
                get( ctx -> {
                   CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                   Map<String, Object> modelo = new HashMap<>();
                   List<Producto> listaProductos = carroCompras.getListaProductos();

                    //System.out.println("Tamanio: "+listaProductos.size());

                    modelo.put("productos", listaProductos);
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());
                    modelo.put("titulo", "Carro de compras");
                    modelo.put("total_carrito", carroCompras.getMontoCarroCompra());

                    //Guardar el nombre de Usuario en header
                    modelo.put("session", ctx.sessionAttributeMap());
                    ctx.render("/templates/vista/carritoCompra.html", modelo);
                });

                //Listado de productos por comprar
//                get(ctx -> {
//                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
//                    Map<String, Object> modelo = new HashMap<>();
//                    List<Producto> listaProductos = carroCompras.getListaProductos();
//
//                    modelo.put("titulo", "Lista de productos");
//                    modelo.put("productos", listaProductos);
//                    modelo.put("cantidadProdCarrito", carroCompras.getCantidad());
//
//                    ctx.render("/templates/vista/index.html", modelo);
//                });

                //Crear carrito, y añadir productos al carrito
                post("/{idProducto}", ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    int cantidad = ctx.formParamAsClass("cantidad", Integer.class).get();
                    //System.out.println("Dentro de crear, IDProd: "+ identificador + " cantidad: " + cantidad);
                    Producto tempProducto = serviciosProducto.getProductByID(identificador);
                    tempProducto.setCantidad(tempProducto.getCantidad() + cantidad - 1);
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");

                    carroCompras.addProducto(tempProducto);
                    //System.out.println("\n\n La cantidad del item es de : "+ tempProducto.getCantidad()+"debe ser "+ cantidad);
                    ctx.sessionAttribute("carroCompras", carroCompras);

                    ctx.redirect("/");
                });

                //Eliminar producto del carrito
                post("/Eliminar/{idProducto}", ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    carroCompras.deleteProducto(serviciosProducto.getProductByID(identificador));
                    //System.out.println("El id borrado es:"+ serviciosProducto.getProductByID(identificador) );

                    ctx.sessionAttribute("carroCompras", carroCompras);
                    ctx.redirect("/carritoCompras");
                });

                //Limpiar todos los productos del carrito
                post("/LimpiarCarroCompras", ctx -> {
                    System.out.println("Llego a limpiarCarroCompras");
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    carroCompras.limpiarCarrito(carroCompras.getListaProductos());
                    List<Producto> listaProductos = carroCompras.getListaProductos();
                    //carroCompras.limpiarCarrito(listaProductos);
                    carroCompras.setListaProductos(carroCompras.limpiarCarrito(listaProductos));

                    ctx.sessionAttribute("carroCompras", carroCompras);

                    //Map<String, Object> modelo = new HashMap<>();

                    //System.out.println("Tamanio: "+listaProductos.size());


                    System.out.println("Llego a final del carrito");
                    //ctx.render("/templates/vista/carritoCompra.html", modelo);
                    ctx.redirect("/carritoCompras");
                });
            });
        });
    }
}
