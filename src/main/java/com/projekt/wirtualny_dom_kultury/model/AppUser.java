package com.projekt.wirtualny_dom_kultury.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Size(min = 4)
    @Column(unique = true)
    private String username;

    @Size(min = 4)
    private String password;


    private String name;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Event> eventList;
}
