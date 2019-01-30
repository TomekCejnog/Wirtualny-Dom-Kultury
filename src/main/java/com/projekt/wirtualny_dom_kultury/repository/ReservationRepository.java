package com.projekt.wirtualny_dom_kultury.repository;

import com.projekt.wirtualny_dom_kultury.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
