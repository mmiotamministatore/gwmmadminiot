package it.mm.iot.gw.admin.service.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SensorMeasure {

    @JsonProperty("tz")
    private String dataEvento;

    @JsonProperty("t")
    private String tipoMisura;

    @JsonProperty("x")
    private String descrizioneMisura;

    @JsonProperty("v")
    private BigDecimal misura;

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getTipoMisura() {
        return tipoMisura;
    }

    public void setTipoMisura(String tipoMisura) {
        this.tipoMisura = tipoMisura;
    }

    public String getDescrizioneMisura() {
        return descrizioneMisura;
    }

    public void setDescrizioneMisura(String descrizioneMisura) {
        this.descrizioneMisura = descrizioneMisura;
    }

    public BigDecimal getMisura() {
        return misura;
    }

    public void setMisura(BigDecimal misura) {
        this.misura = misura;
    }
}
