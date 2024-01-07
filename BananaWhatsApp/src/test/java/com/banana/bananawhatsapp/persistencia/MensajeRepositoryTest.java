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
        Usuario usr1 = new Usuario(null, "usr1", "u1@r.com", LocalDate.now(), true);
        repoUsuario.crear(usr1);
        Usuario usr2 = new Usuario(null, "usr2", "u2@d.com", LocalDate.now(), true);
        repoUsuario.crear(usr2);
        Usuario usr3 = new Usuario(null, "usr3", "u3@d.com", LocalDate.now(), true);
        repoUsuario.crear(usr3);
        Usuario usr4 = new Usuario(null, "usr7", "u4@d.com", LocalDate.now(), true);
        repoUsuario.crear(usr4);

        Mensaje nuevoMensaje = new Mensaje(null, usr1, usr2, "Menaje de usr1 a usr2", LocalDate.now());
        repoMensaje.crear(nuevoMensaje);
        Mensaje nuevoMensaje2 = new Mensaje(null, usr1, usr3, "Menaje de usr1 a usr3", LocalDate.now());
        repoMensaje.crear(nuevoMensaje2);
        Mensaje nuevoMensaje3 = new Mensaje(null, usr2, usr1, "Menaje de usr2 a usr1", LocalDate.now());
        repoMensaje.crear(nuevoMensaje3);
        Mensaje nuevoMensaje4 = new Mensaje(null, usr4, usr1, "Menaje de usr1 a usr3", LocalDate.now());
        repoMensaje.crear(nuevoMensaje4);
        Mensaje nuevoMensaje5 = new Mensaje(null, usr1, usr4, "Menaje de usr2 a usr1", LocalDate.now());
        repoMensaje.crear(nuevoMensaje5);
        Mensaje nuevoMensaje6 = new Mensaje(null, usr4, usr1, "Menaje de usr1 a usr3", LocalDate.now());
        repoMensaje.crear(nuevoMensaje6);

        List<Mensaje> mensajes = repoMensaje.obtener(usr1);

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