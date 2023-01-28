package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        try {
            User user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            if (user == null || parkingLot == null) {
                throw new Exception("Cannot make reservation");
            }

            List<Spot> spots = parkingLot.getSpotList();
            Spot selectedSpot = null;
            int minPrice = Integer.MAX_VALUE;

            if(spots != null){
                for(Spot spot : spots){
                    int wheelsCount = Integer.MAX_VALUE;

                    if(spot.getSpotType().equals(SpotType.TWO_WHEELER))
                        wheelsCount = 2;
                    else if (spot.getSpotType().equals(SpotType.FOUR_WHEELER))
                        wheelsCount = 4;

                    if(wheelsCount>=numberOfWheels && minPrice > spot.getPricePerHour() && spot.getOccupied() == false){
                        selectedSpot = spot;
                        minPrice = spot.getPricePerHour();
                    }
                }
            }

            if(selectedSpot == null)
                throw new Exception("Cannot make reservation");

            Reservation reservation = new Reservation(timeInHours);
            reservation.setSpot(selectedSpot);
            reservation.setUser(user);

            List<Reservation> reservationList = user.getReservationList();

            if(reservationList == null)
                reservationList = new ArrayList<>();

            reservationList.add(reservation);
            user.setReservationList(reservationList);

            List<Reservation> SpotReservationList = selectedSpot.getReservationList();

            if(SpotReservationList == null)
                SpotReservationList = new ArrayList<>();

            SpotReservationList.add(reservation);

            selectedSpot.setReservationList(SpotReservationList);
            selectedSpot.setOccupied(true);

            userRepository3.save(user);
            spotRepository3.save(selectedSpot);

            return reservation;

        }
        catch (Exception e){
            return null;
        }
    }
}
