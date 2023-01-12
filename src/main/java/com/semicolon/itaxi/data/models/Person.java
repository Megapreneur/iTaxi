package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.Authority;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    @Column(unique = true)
    @Email
    @Valid
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String address;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<Authority> authority = new HashSet<>();

}
