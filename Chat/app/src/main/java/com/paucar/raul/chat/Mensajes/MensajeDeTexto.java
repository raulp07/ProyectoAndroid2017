package com.paucar.raul.chat.Mensajes;

/**
 * Created by raul on 16/04/2017.
 */

public class MensajeDeTexto {

    private String id;
    private String mensaje;
    private String HoraMensaje;
    private int tipoMensaje;

    public MensajeDeTexto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHoraMensaje() {
        return HoraMensaje;
    }

    public void setHoraMensaje(String horaMensaje) {
        HoraMensaje = horaMensaje;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}
