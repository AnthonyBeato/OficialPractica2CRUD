package org.example.controlador;

import Utilidad.ControllerBase;
import io.javalin.Javalin;
import org.example.encapsulacion.CarroCompras;
import org.example.encapsulacion.Producto;
import org.example.encapsulacion.Usuario;
import org.example.servicios.ServiciosProducto;
import org.example.servicios.ServiciosUsuario;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ControllerProducto extends ControllerBase {
    ServiciosProducto serviciosProducto = ServiciosProducto.getInstancia();

    public ControllerProducto(Javalin app){
        super(app);
    }

    @Override
    public void aplicarDireccionamiento() {
        app.routes(() -> {
           path("/", () -> {
               get("/",ctx -> {
                   CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                   Map<String, Object> modelo = new HashMap<>();
                   List<Producto> listaProductos = serviciosProducto.getListaProductos();

                   modelo.put("titulo", "Lista de compras");
                   modelo.put("productos", listaProductos);
                   modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());

                   //Guardar el nombre de Usuario en header
                   modelo.put("session", ctx.sessionAttributeMap());
                   ctx.render("/templates/vista/index.html", modelo);
               });
           }) ;
        });

        app.routes(() -> {
            path("/Seguridad/Productos", () -> {
                get("/",ctx -> {
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    Map<String, Object> modelo = new HashMap<>();
                    List<Producto> listaProductos = serviciosProducto.getListaProductos();

                    modelo.put("titulo", "Gestion de productos");
                    modelo.put("productos", listaProductos);
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());

                    //Guardar el nombre de Usuario en header
                    modelo.put("session", ctx.sessionAttributeMap());

                    Usuario usuarioActual = ctx.sessionAttribute("usuario");


                    if(usuarioActual != null && usuarioActual.getUsuario().equals("admin")){
                        ctx.render("/templates/vista/gestionProductos.html", modelo);
                    }else{
                        ctx.render("/templates/vista/error.html");
                    }

                });

                //Listado de productos
                get(ctx -> {
                    List<Producto> listaProductos = serviciosProducto.getListaProductos();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Lista de productos");
                    modelo.put("productos", listaProductos);
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());

                    //Guardar el nombre de Usuario en header
                    modelo.put("session", ctx.sessionAttributeMap());

                    ctx.render("/templates/vista/index.html", modelo);
                });

                //Formulario crear
                get("/Crear/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());

                    //Guardar el nombre de Usuario en header
                    modelo.put("session", ctx.sessionAttributeMap());
                    ctx.render("/templates/vista/crearProducto.html", modelo);
                });
                //Crear producto
                post("/Crear/", ctx -> {
                    String identificador = UUID.randomUUID().toString();
                    String nombre = ctx.formParam("nombre");
                    double precio = ctx.formParamAsClass("precio", Double.class).get();
                    //int cantidad = ctx.formParamAsClass("cantidad", Integer.class).get();

                    Producto tempProducto = new Producto(identificador, nombre, precio, 1);
                    serviciosProducto.createProducto(tempProducto);
                    ctx.redirect("/Seguridad/Productos");
                });

                //Formulario modificar
                get("/Modificar/{idProducto}",ctx -> {
                    Producto producto = serviciosProducto.getProductByID(ctx.pathParam("idProducto"));
                    CarroCompras carroCompras = ctx.sessionAttribute("carroCompras");
                    Map<String, Object> modelo = new HashMap<>();

                    modelo.put("titulo", "Modificar productos");
                    modelo.put("idProducto", producto.getIdProducto());
                    modelo.put("nombre", producto.getNombre());
                    modelo.put("precio", producto.getPrecio());
                    //modelo.put("cantidad", producto.getCantidad());
                    modelo.put("action", ("/Seguridad/Productos/Modificar/".concat(producto.getIdProducto())));
                    modelo.put("cantidadProdCarrito", carroCompras.getCantidadCarroCompra());

                    //Guardar el nombre de Usuario en header
                    modelo.put("session", ctx.sessionAttributeMap());
                    ctx.render("/templates/vista/editarProducto.html", modelo);
                });
                //Actualizar producto
                post("/Modificar/{idProducto}",ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    String nombre = ctx.formParam("nombre");
                    double precio = ctx.formParamAsClass("precio", Double.class).get();

                    Producto tempProducto = new Producto(identificador, nombre, precio, 1);

                    serviciosProducto.updateProducto(tempProducto);
                    ctx.redirect("/Seguridad/Productos");
                });

                //Eliminar producto
                post("/Eliminar/{idProducto}", ctx -> {
                    String identificador = ctx.pathParam("idProducto");
                    serviciosProducto.deleteProducto(identificador);
                    ctx.redirect("/Seguridad/Productos");
                });
            }) ;
        });
    }
}
