package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
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
//@ActiveProfiles("dev") - revisar asignación id + de un user - static!
@ActiveProfiles("prod")
class ControladorMensajesTest {

    @Autowired
    ControladorMensajes controladorMensajes;
    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        controladorUsuarios.alta(remitente);
        System.out.println(remitente);

        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        controladorUsuarios.alta(destinatario);
        System.out.println(destinatario);

        Boolean resultado = controladorMensajes.enviarMensaje(remitente.getId(), destinatario.getId(), "texto cuerpo test");

        assertTrue(resultado);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = new Usuario(null, "Remitente", "r@r.com", LocalDate.now(), true);
        controladorUsuarios.alta(remitente);
        System.out.println(remitente);

        Usuario destinatario = new Usuario(null, "Destinatario", "d@d.com", LocalDate.now(), true);
        controladorUsuarios.alta(destinatario);
        System.out.println(destinatario);

        assertThrows(Exception.class, () -> {
            controladorMensajes.enviarMensaje(remitente.getId(), destinatario.getId(), null);
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
    }
}