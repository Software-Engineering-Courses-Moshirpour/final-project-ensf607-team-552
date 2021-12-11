package com.model;


import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Role")
@Table(
        name = "role"
)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private ERole roleName;

    public Role(ERole roleName) {
        this.roleName = roleName;
    }


}
