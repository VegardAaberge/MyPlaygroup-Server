package com.myplaygroup.server.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "standard_plan",
        uniqueConstraints = {
                @UniqueConstraint(name = "name_unique", columnNames = "name"),
        }
)
public class StandardPlan {
    @Id
    @SequenceGenerator(
            name = "standard_plan_sequence",
            sequenceName = "standard_plan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "standard_plan_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    public StandardPlan(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
