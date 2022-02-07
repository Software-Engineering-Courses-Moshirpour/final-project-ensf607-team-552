package com.model;


import com.enums.MedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "medtype"
)
public class MedRecordType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private MedType type;

}
