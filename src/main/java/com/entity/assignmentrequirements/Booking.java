package com.entity.assignmentrequirements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="booking_id")
    private Long bookingId;

    @Column(name="date_of_booking")
    private Date dateOfBooking;

    public Booking(Date from) {
        this.dateOfBooking = from;
    }
}
