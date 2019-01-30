package com.projekt.wirtualny_dom_kultury.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //roles: ROLE_ADMIN, ROLE_USER, ROLE_EVENT_ORGANIZER
    private String name;
}
