package com.model;

import com.enums.DailyStatusTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="DailyStatus")
@Table(
        name = "dailyStatus"
)

public class DailyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private DailyStatusTypes type;

}
