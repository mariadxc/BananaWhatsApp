package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MensajeInMermoryRepo implements IMensajeRepository {

    Set<Mensaje> mensajesUsuario = new HashSet<>();
    private static Integer num = 0;

    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
//        mensaje.valido();
//        mensaje.setId(num+1);
//        mensajesUsuario.add(mensaje);
//        return mensaje;
        return null;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
