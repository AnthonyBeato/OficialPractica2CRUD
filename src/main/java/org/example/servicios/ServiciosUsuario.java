package org.example.servicios;

import org.example.encapsulacion.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServiciosUsuario {

    private static ServiciosUsuario instancia;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private ServiciosUsuario(){
        listaUsuarios.add(new Usuario("admin", "admin", "admin"));
        listaUsuarios.add(new Usuario("Anthony", "anthony", "1234"));
    }

    //Validar usuario
    public Usuario authenticateUsuario(String usuario, String password){
        Usuario tempUsuario = getUsuariotByUser(usuario);
        if(tempUsuario != null){
            if(tempUsuario.getContrasena().equals(password)){
                return tempUsuario;
            }
        }
        return null;
    }

    public static ServiciosUsuario getInstancia() {
        if(instancia == null){
            instancia = new ServiciosUsuario();
        }

        return instancia;
    }

    //Registrar usuario
    public Usuario registrarUsuario(Usuario usuario){
        Usuario tempUsuario = getUsuariotByUser(usuario.getUsuario());
        if(tempUsuario != null){
            return null;
        }
        listaUsuarios.add(usuario);

        return usuario;
    }

    public static void setInstancia(ServiciosUsuario instancia) {
        ServiciosUsuario.instancia = instancia;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuariotByID(String id){
        for (Usuario usuario : listaUsuarios){
            if (usuario.getIdUsuario().equals(id)){
                return usuario;
            }
        }
        return null;
    }

    public Usuario getUsuariotByUser(String user){
        for (Usuario usuario : listaUsuarios){
            if (usuario.getUsuario().equals(user)){
                return usuario;
            }
        }
        return null;
    }

}
