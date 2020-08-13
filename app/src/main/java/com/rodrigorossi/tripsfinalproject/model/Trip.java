package com.rodrigorossi.tripsfinalproject.model;

import java.util.Objects;

/**
 * Representa a classe de entidade para o cadastro de viagens
 */
public class Trip {
    private int id;
    private String destiny;
    private int initialMileage;
    private int finalMileage;
    private int tripType;
    private boolean refund;
    private int vehicle;

    public Trip() {
    }

    public Trip(int id, String destiny, int initialMileage, int finalMileage, int tripType, boolean refund, int vehicle) {
        this.id = id;
        this.destiny = destiny;
        this.initialMileage = initialMileage;
        this.finalMileage = finalMileage;
        this.tripType = tripType;
        this.refund = refund;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public int getInitialMileage() {
        return initialMileage;
    }

    public void setInitialMileage(int initialMileage) {
        this.initialMileage = initialMileage;
    }

    public int getFinalMileage() {
        return finalMileage;
    }

    public void setFinalMileage(int finalMileage) {
        this.finalMileage = finalMileage;
    }

    public int getTripType() {
        return tripType;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public boolean isRefund() {
        return refund;
    }

    public void setRefund(boolean refund) {
        this.refund = refund;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id == trip.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", destiny='" + destiny + '\'' +
                ", initialMileage=" + initialMileage +
                ", finalMileage=" + finalMileage +
                ", tripType=" + tripType +
                ", refund=" + refund +
                ", vehicle=" + vehicle +
                '}';
    }
}
