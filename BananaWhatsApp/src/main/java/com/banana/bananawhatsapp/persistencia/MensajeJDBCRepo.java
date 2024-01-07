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
        /*pendiente corregir tratamiento transacción*/
        String sqlMensaje = "SELECT * FROM mensaje WHERE from_user = ? OR to_user = ?";
        String sqlUsuario = "SELECT * FROM usuario WHERE id = ?";
        try (

                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt1 = conn.prepareStatement(sqlMensaje, Statement.RETURN_GENERATED_KEYS);
        ) {
            conn.setAutoCommit(false);
            usuario.valido();
            stmt1.setInt(1, usuario.getId());
            stmt1.setInt(2, usuario.getId());
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                try {
                    System.out.println("from_user: " + rs1.getInt("from_user"));
                    /* remitente*/
                    PreparedStatement stmt2 = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
                    stmt2.setInt(1, rs1.getInt("from_user"));
                    ResultSet rs2 = stmt2.executeQuery();
                    try {

                        Usuario remitente = new Usuario(rs2.getInt("id"), rs2.getString("nombre"), rs2.getString("email"), rs2.getDate("alta").toLocalDate(), rs2.getBoolean("activo"));
                    } catch (SQLException e) {
                        System.out.println("error select remitente: " + e);
                        e.printStackTrace();
                        throw new SQLException(e);
                    }


                    /*destinatario*/
                    PreparedStatement stmt3 = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
                    stmt2.setInt(1, rs1.getInt("to_user"));
                    ResultSet rs3 = stmt3.executeQuery();

                    Usuario destinatario = new Usuario(rs3.getInt("id"), rs3.getString("nombre"), rs3.getString("email"), rs3.getDate("alta").toLocalDate(), rs3.getBoolean("activo"));


                    mensajes.add(
                            new Mensaje(rs1.getInt("id"),
                                    destinatario, destinatario,
                                    rs1.getString("cuerpo"),
                                    rs1.getDate("fecha").toLocalDate())
                    );
                    conn.commit();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SQLException(e);
                }
            }
            ;
            return mensajes;
        }
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
