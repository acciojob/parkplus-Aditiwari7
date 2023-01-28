package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private SpotType spotType;

    private int pricePerHour;
    private boolean occupied;

    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    public Spot(){}

    public Spot(ParkingLot parkingLot1, int pricePerHr, SpotType spotType1){
        this.parkingLot = parkingLot1;
        this.pricePerHour = pricePerHr;
        this.spotType = spotType1;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }
    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public SpotType getSpotType() {
        return spotType;
    }
    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public boolean getOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }
    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
