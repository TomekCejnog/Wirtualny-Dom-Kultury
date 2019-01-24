package com.projekt.wirtualny_dom_kultury.model;

import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;

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

    private String name;

    private DateFormat date;

    private String description;

    private int lenght;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser owner;

    boolean isEmptyPlace;

//k
}
