package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@ActiveProfiles("dev") pendiente revisar incremental id usuario
@ActiveProfiles("prod")
class ServicioMensajeriaTest {
    @Autowired
    IServicioMensajeria servicioMensajeria;
    @Autowired
    IServicioUsuarios servicioUsuarios;

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws UsuarioException, MensajeException {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        servicioUsuarios.crearUsuario(remitente);
        System.out.println(remitente);

        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        servicioUsuarios.crearUsuario(destinatario);
        System.out.println(destinatario);

        Mensaje nuevoMensaje = servicioMensajeria.enviarMensaje(remitente, destinatario, "texto mensaje test");

        assertThat(nuevoMensaje, notNullValue());
        assertThat(nuevoMensaje.getId(), greaterThan(0));

    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        servicioUsuarios.crearUsuario(remitente);
        System.out.println(remitente);

        assertThrows(MensajeException.class, () -> {
            servicioMensajeria.enviarMensaje(remitente, null, "texto cuerpo NOK");
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
    }
}