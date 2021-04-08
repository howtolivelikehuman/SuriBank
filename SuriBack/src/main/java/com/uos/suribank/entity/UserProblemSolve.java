package com.uos.suribank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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

    @Column(nullable = false, columnDefinition = "Float(3,2) DEFAULT 0")
    float score;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date solveDate;
}
