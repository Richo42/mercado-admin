package com.mercado.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "correlativo_comprobante")
public class CorrelativoComprobante {

    @Id
    private Integer anio;

    @Column(name = "ultimo_numero")
    private Long ultimoNumero;

    public CorrelativoComprobante() {}

    public CorrelativoComprobante(Integer anio, Long ultimoNumero) {
        this.anio = anio;
        this.ultimoNumero = ultimoNumero;
    }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public Long getUltimoNumero() { return ultimoNumero; }
    public void setUltimoNumero(Long ultimoNumero) { this.ultimoNumero = ultimoNumero; }
}