package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean paymentCompleted;

//    @Enumerated(value = EnumType.STRING)
    private PaymentMode paymentMode;

    @OneToOne
    @JoinColumn
    private Reservation reservation;

    public Payment(){}

    public Payment(boolean paymentCompleted1, PaymentMode paymentMode1){
        this.paymentCompleted = paymentCompleted1;
        this.paymentMode = paymentMode1;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }
    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    public Reservation getReservation() {
        return reservation;
    }
}
