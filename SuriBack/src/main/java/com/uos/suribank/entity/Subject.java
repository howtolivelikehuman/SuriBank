package com.uos.suribank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@DynamicUpdate
@DynamicInsert
public class Subject {
    
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    Long code;

    @Column(length  = 30)
    String name;
}
