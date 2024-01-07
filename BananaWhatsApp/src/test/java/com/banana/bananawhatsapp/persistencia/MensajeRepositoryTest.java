package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@ActiveProfiles("dev") - revisar incremental en inMemori
@ActiveProfiles("prod")
class MensajeRepositoryTest {
    @Autowired
    IMensajeRepository repoMensaje;
    @Autowired
    IUsuarioRepository repoUsuario;

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws Exception {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        repoUsuario.crear(remitente);
        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        repoUsuario.crear(destinatario);
        System.out.println(remitente);
        System.out.println(destinatario);

        Mensaje nuevoMensaje = new Mensaje(null, remitente, destinatario, "Cuerpo del mensaje", LocalDate.now());
        repoMensaje.crear(nuevoMensaje);
        System.out.println(nuevoMensaje);

        assertThat(nuevoMensaje, notNullValue());
        assertThat(nuevoMensaje.getId(), greaterThan(0));
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws Exception {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        repoUsuario.crear(remitente);
        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        repoUsuario.crear(destinatario);

        Mensaje nuevoMensaje = new Mensaje(null, remitente, destinatario, null, LocalDate.now());

        assertThrows(Exception.class, () -> {
            repoMensaje.crear(nuevoMensaje);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        repoUsuario.crear(remitente);
        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        repoUsuario.crear(destinatario);
        System.out.println(remitente);
        System.out.println(destinatario);

        Mensaje nuevoMensaje = new Mensaje(null, remitente, destinatario, "Cuerpo del mensaje", LocalDate.now());
        repoMensaje.crear(nuevoMensaje);
        System.out.println(nuevoMensaje);

        List<Mensaje> mensajes = repoUsuario.obtener(remitente, destinatario);

        System.out.println(mensajes);
        assertThat(mensajes.size(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
    }

}