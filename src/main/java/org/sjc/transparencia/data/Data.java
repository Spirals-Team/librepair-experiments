package org.sjc.transparencia.data;

import java.util.UUID;

public class Data {
    private UUID data_uuid;
    private Integer mes;
    private Integer ano;

    public UUID getData_uuid() {
        return data_uuid;
    }

    public void setData_uuid(UUID data_uuid) {
        this.data_uuid = data_uuid;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
