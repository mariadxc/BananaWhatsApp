package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@ActiveProfiles("dev")
@ActiveProfiles("prod")
class UsuarioRepositoryTest {
    @Autowired
    IUsuarioRepository repo;

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws Exception {
        Usuario nuevo = new Usuario(null, "Maria", "m@m.com", LocalDate.now(), true);
        repo.crear(nuevo);
        System.out.println(nuevo);
//        Usuario nuevo2 = new Usuario(null, "Otro", "m@m.com", LocalDate.now(), true);
//        repo.crear(nuevo2);
//        System.out.println(nuevo2);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Maria", "m", LocalDate.now(), true);
        assertThrows(Exception.class, () -> {
            repo.crear(nuevo);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException {
        Usuario nuevo = new Usuario(null, "Maria", "m@m.com", LocalDate.now(), true);
        repo.crear(nuevo);
        System.out.println(nuevo);
        Usuario upduser = new Usuario(nuevo.getId(), "Maria2", "m2@m.com", LocalDate.now(), true);
        repo.actualizar(upduser);

        assertThat(upduser, notNullValue());
        assertEquals(upduser.getId(), nuevo.getId());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException {
        Usuario nuevo = new Usuario(null, "Maria", "m@m.com", LocalDate.now(), true);
        repo.crear(nuevo);
        System.out.println(nuevo);
        Usuario upduser = new Usuario(nuevo.getId(), "Maria2", "m", LocalDate.now(), true);

        assertThrows(Exception.class, () -> {
            repo.actualizar(upduser);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() {
    }

}