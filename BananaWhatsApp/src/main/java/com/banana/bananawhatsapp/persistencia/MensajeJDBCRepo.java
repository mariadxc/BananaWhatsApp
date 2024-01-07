package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MensajeJDBCRepo implements IMensajeRepository {
    private String db_url;

    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";
        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            mensaje.valido();

            stmt.setString(1, mensaje.getCuerpo());
            stmt.setDate(2, Date.valueOf(mensaje.getFecha()));
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("No mensaje id asignado");
            }
        } catch (MensajeException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        List<Mensaje> mensajes = new ArrayList<>();
        Usuario usr = null;
        Mensaje mensaje = null;


        String sql = "SELECT * FROM mensaje WHERE from_user = ? OR to_user = ?";
        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
//                conn.setAutoCommit(false);
        ) {
            usuario.valido();

            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, usuario.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mensajes.add(
                        new Mensaje(rs.getInt("id"),
                                rs.getDate("fecha").toLocalDate(),
                                usuario.grs.getInt("from_user"),
                                rs.getInt("to_user"))
            }
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("No mensaje id asignado");
            }
        } catch (MensajeException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        return mensajes;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
