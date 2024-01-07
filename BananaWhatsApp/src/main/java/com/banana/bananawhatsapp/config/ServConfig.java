package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.servicios.IServicioMensajeria;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import com.banana.bananawhatsapp.servicios.MensajeServicioImpl;
import com.banana.bananawhatsapp.servicios.UsuarioServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServConfig {
    @Autowired
    IUsuarioRepository usuarioRepo;
    @Autowired
    IMensajeRepository mensajeRepo;

    public IServicioUsuarios getUsuarioRepo() {
        UsuarioServicioImpl usuarioRepositorio = new UsuarioServicioImpl();
        usuarioRepositorio.setServicioUsuario(usuarioRepositorio);
        return usuarioRepositorio;
    }

    public IServicioMensajeria getMensajeRepo() {
        MensajeServicioImpl mensajeRepositorio = new MensajeServicioImpl();
        mensajeRepositorio.setServicioMensajeria(mensajeRepositorio);
        return mensajeRepositorio;
    }
}

