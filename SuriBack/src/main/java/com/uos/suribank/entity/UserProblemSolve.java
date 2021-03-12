package com.uos.suribank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "user_solve")
@DynamicUpdate
@DynamicInsert
public class UserProblemSolve{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @ManyToOne(targetEntity = ProblemTable.class)
    @JoinColumn(name = "problem")
    ProblemTable problem;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user")
    User user;

    @Column(length  = 500, name = "user_answer")
    String userAnswer;
}
