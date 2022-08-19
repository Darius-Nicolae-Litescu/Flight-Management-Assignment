package com.repository;

import com.entity.assignmentrequirements.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("select f from Flight f where f.city1 = ?1 and f.city2 = ?2")
    List<Flight> findByCity1AndCity2(String city1, String city2);

    @Query("select f from Flight f join f.bookingList b where b.bookingId = ?1")
    Flight findFlightByBookingId(Long bookingId);

    @Query("select f from Flight f join f.bookingList b where b.bookingId in ?1")
    List<Flight> findFlightsByBookingIds(List<Long> bookingIds);

    List<Flight> findByFlightName(String flightName);

    List<Flight> findByCity1(String city1);

    List<Flight> findByCity2(String city2);

    List<Flight> findByFlightType(String flightType);

    List<Flight> findByFlightNameAndCity1AndCity2AndFlightType(String flightName, String city1, String city2, String flightType);
}
