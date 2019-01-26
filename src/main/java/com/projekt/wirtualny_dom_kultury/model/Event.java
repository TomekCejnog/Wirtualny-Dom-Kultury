package com.projekt.wirtualny_dom_kultury.model;

import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eventName;

    private LocalDate date;

    private String description;

    private int eventLenght;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser owner;

    private int accessibility;

}
