package org.example.encapsulacion;

import java.util.Objects;
import java.util.UUID;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;

    public Usuario(String nombre, String usuario, String contrasena) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.idUsuario = UUID.randomUUID().toString();
    }

    public void updateUsuario(Usuario tempUsuario){
        nombre = tempUsuario.getNombre();
        usuario = tempUsuario.getUsuario();
        contrasena = tempUsuario.getContrasena();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(usuario, usuario.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }
}
