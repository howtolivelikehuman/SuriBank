package com.uos.suribank.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "problem_image")
@DynamicUpdate
@DynamicInsert
public class ProblemImage {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @ManyToOne(targetEntity = ProblemTable.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "problem_id")
    ProblemTable problemTable;

    //0 for question, 1 for answer;
    @Column
    int type;

    @Column(length  = 100, nullable = false)
    String link;
}*/