package com.projekt.wirtualny_dom_kultury.repository;

import com.projekt.wirtualny_dom_kultury.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
