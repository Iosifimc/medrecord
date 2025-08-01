package org.medrecord.dto;

import java.sql.Date;

public class MedidasFisiologicasDTO {
    private int idRegistroMedida;
    private String tipoMedida;
    private String unidadMedida;
    private double valorMedida;
    private Date fechaRegistro;
    private String notaAdicional;

    public int getIdRegistroMedida() {
        return idRegistroMedida;
    }

    public void setIdRegistroMedida(int idRegistroMedida) {
        this.idRegistroMedida = idRegistroMedida;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getValorMedida() {
        return valorMedida;
    }

    public void setValorMedida(double valorMedida) {
        this.valorMedida = valorMedida;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNotaAdicional() {
        return notaAdicional;
    }

    public void setNotaAdicional(String notaAdicional) {
        this.notaAdicional = notaAdicional;
    }
}
