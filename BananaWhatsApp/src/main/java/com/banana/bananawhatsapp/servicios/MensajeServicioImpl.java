package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Setter
public class MensajeServicioImpl implements IServicioMensajeria {
    @Autowired
    private IServicioMensajeria servicioMensajeria;
    @Autowired
    private IMensajeRepository repositorioMenajeria;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {
        Mensaje mensaje = new Mensaje(null, remitente, destinatario, texto, LocalDate.now());

        try {
            repositorioMenajeria.crear(mensaje);
            System.out.println(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensajeException("Error en el envío de mensaje: " + e.getMessage());
        }
        return mensaje;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return null;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return false;
    }
}
