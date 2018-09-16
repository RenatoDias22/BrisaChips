package com.renatodias.brisachips.Fragmants.Cadastro.Model;

public class LatlongPosition {
    Double latitude;
    Double longitude;

    public LatlongPosition(){}

    public LatlongPosition(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
