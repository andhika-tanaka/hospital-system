package com.internship.hospitalsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String dob;

    private String idNumber;

    private String gender;

    private String address;

    private  String phone1;

    private String phone2;

    private Integer active;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
