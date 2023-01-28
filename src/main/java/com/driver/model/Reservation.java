package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int numberOfHours;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn
    private Spot spot;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    Payment payment;

    public Reservation(){}

//    public Reservation(int numOfHours){
//        this.numberOfHours = numOfHours;
//    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }
    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public Payment getPayment() {
        return payment;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
    public Spot getSpot() {
        return spot;
    }
}
