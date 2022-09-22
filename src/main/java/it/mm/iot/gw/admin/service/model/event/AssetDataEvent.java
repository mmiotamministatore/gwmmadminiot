package it.mm.iot.gw.admin.service.model.event;

import it.mm.iot.gw.admin.service.model.asset.Sensore;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AssetDataEvent {

    private Sensore ref;
    private String data;

    private PropertyChangeSupport support;

    public AssetDataEvent() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setData(String value) {
        support.firePropertyChange("data", this.data, value);
        this.data = value;
    }

    public Sensore getRef() {
        return ref;
    }

    public void setRef(Sensore ref) {
        this.ref = ref;
    }
}
