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
        try{
            if(!userRepository3.existsById(userId) || !parkingLotRepository3.existsById(parkingLotId)){
                throw new Exception("Cannot make reservation");
            }

            User user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spots = parkingLot.getSpotList();
            boolean checkSpot = false;
            for(Spot spot : spots){
                if(!spot.isOccupied()){
                    checkSpot = true;
                    break;
                }
            }

            if(checkSpot == false){
                throw new Exception("Cannot make reservation");
            }

            SpotType requestSpotType;
            if(numberOfWheels == 4) requestSpotType = SpotType.FOUR_WHEELER;
            else if (numberOfWheels == 2) requestSpotType = SpotType.TWO_WHEELER;
            else requestSpotType = SpotType.OTHERS;

            int minPrice = Integer.MAX_VALUE;
            checkSpot = false;
            Spot selectedSpot = null;

            for(Spot spot : spots){
                if(requestSpotType.equals(spot.getSpotType())){
                    if(spot.getPricePerHour() * timeInHours < minPrice && spot.isOccupied() == false ){
                        minPrice = spot.getPricePerHour() * timeInHours;
                        checkSpot = true;
                        selectedSpot = spot;
                    }
                } else if (requestSpotType.equals(SpotType.FOUR_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS)) {
                    if(spot.getPricePerHour() * timeInHours < minPrice && spot.isOccupied() == false ){
                        minPrice = spot.getPricePerHour() * timeInHours;
                        checkSpot = true;
                        selectedSpot = spot;
                    }
                } else if (requestSpotType.equals(SpotType.TWO_WHEELER) && (spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.OTHERS))){
                    if(spot.getPricePerHour() * timeInHours < minPrice && spot.isOccupied() == false ){
                        minPrice = spot.getPricePerHour() * timeInHours;
                        checkSpot = true;
                        selectedSpot = spot;
                    }
                }
            }

            if(checkSpot == false){
                throw new Exception("Cannot make reservation");
            }
            if(selectedSpot != null){
                selectedSpot.setOccupied(true);
            }

            Reservation reservation = new Reservation();
            reservation.setNumberOfHours(timeInHours);
            reservation.setSpot(selectedSpot);
            reservation.setUser(user);

            selectedSpot.getReservationList().add(reservation);
            user.getReservationList().add(reservation);

            reservationRepository3.save(reservation);
            userRepository3.save(user);
            spotRepository3.save(selectedSpot);
            parkingLotRepository3.save(parkingLot);

            return reservation;
        }
        catch (Exception e){
            throw new Exception("Cannot make reservation");
//            return null;
        }
    }
}
