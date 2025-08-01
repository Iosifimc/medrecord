package org.medrecord.dto;

public class ProcedimientoMedicoDTO {
    private int idRegistroProcedimiento;
    private String tipoProcedimiento;
    private String notaAdicional;

    public int getIdRegistroProcedimiento() {
        return idRegistroProcedimiento;
    }

    public void setIdRegistroProcedimiento(int idRegistroProcedimiento) {
        this.idRegistroProcedimiento = idRegistroProcedimiento;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public String getNotaAdicional() {
        return notaAdicional;
    }

    public void setNotaAdicional(String notaAdicional) {
        this.notaAdicional = notaAdicional;
    }
}
