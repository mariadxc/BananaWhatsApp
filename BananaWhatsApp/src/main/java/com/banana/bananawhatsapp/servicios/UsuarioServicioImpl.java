package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Setter
public class UsuarioServicioImpl implements IServicioUsuarios {

    @Autowired
    IServicioUsuarios servicioUsuario;

    @Autowired
    IUsuarioRepository repositorioUsuario;


    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {

        try {
            usuario.valido();
            repositorioUsuario.crear(usuario);
        } catch (Exception e) {
//        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la creación: " + e.getMessage());
        }

        return usuario;
//        return null;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        return false;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        return null;
    }

    @Override
    public Usuario obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }
}
