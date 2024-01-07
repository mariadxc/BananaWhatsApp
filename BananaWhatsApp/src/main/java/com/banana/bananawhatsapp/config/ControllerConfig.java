package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.controladores.ControladorMensajes;
import com.banana.bananawhatsapp.controladores.ControladorUsuarios;
import com.banana.bananawhatsapp.servicios.IServicioMensajeria;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Autowired
    IServicioUsuarios userSvc;

    @Autowired
    IServicioMensajeria mensajeriaSvc;

    @Bean
    ControladorUsuarios crearUsuarioController() {
        ControladorUsuarios controller = new ControladorUsuarios();
        controller.setServicioUsuarios(userSvc);

        return controller;
    }

    @Bean
    ControladorMensajes crearMensajeController() {
        ControladorMensajes mensajesController = new ControladorMensajes();
        mensajesController.setServicioMensajeria(mensajeriaSvc);

        return mensajesController;
    }

}
