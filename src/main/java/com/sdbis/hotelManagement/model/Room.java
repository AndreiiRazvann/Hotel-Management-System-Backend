package com.sdbis.hotelManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
@Entity
@Getter
@Setter
@AllArgsConstructor

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy="room", fetch = LAZY, cascade = CascadeType.ALL) //o camera poate avea mai multe rezervari; cascade pt stergerea in cascada
    private List<BookedRoom> bookings;

    public Room() {     
        this.bookings = new ArrayList<>();
    }
    //pt fiecare camera creata, o lista goala de rezervari va fi adaugata

    public void addBooking(BookedRoom booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
    //metoda pentru a adauga rezervari pt fiecare camera
}
