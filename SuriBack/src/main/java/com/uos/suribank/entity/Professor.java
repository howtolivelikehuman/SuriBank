package com.uos.suribank.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
public class Professor {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    Long code;

    @Column(length  = 30)
    String name;
}
