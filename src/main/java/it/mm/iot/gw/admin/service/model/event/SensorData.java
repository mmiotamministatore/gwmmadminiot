package it.mm.iot.gw.admin.service.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SensorData {

    @JsonProperty("u")
    private String idEvento;

    @JsonProperty("ur")
    private String idEvento2;

    @JsonProperty("s")
    private String idDispositivo;

    @JsonProperty("r")
    private String idCoda;

    @JsonProperty("tz")
    private LocalDateTime dataEvento;

    @JsonProperty("t")
    private String tenant;

    @JsonProperty("et")
    private String eventType;

    @JsonProperty("ca")
    private LocalDateTime dataElaborazione;

    @JsonProperty("m")
    private String jsonMessage;

    private List<SensorMeasure> detailData;

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdEvento2() {
        return idEvento2;
    }

    public void setIdEvento2(String idEvento2) {
        this.idEvento2 = idEvento2;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdCoda() {
        return idCoda;
    }

    public void setIdCoda(String idCoda) {
        this.idCoda = idCoda;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getDataElaborazione() {
        return dataElaborazione;
    }

    public void setDataElaborazione(LocalDateTime dataElaborazione) {
        this.dataElaborazione = dataElaborazione;
    }

    public List<SensorMeasure> getDetailData() {
        return detailData;
    }

    public void setDetailData(List<SensorMeasure> detailData) {
        this.detailData = detailData;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(String jsonMessage) {
        this.jsonMessage = jsonMessage;
    }
}
