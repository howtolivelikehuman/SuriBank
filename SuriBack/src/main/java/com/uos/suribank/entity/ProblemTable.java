package com.uos.suribank.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "problem_table")
@DynamicUpdate
@DynamicInsert
public class ProblemTable{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;
    
    @Column(length  = 30, nullable = false)
    String title;
    
    @ManyToOne
    @JoinColumn(name = "uploader_id", referencedColumnName="id")
    private User user;

    @Column(length  = 30)
    String subject;

    @Column(length = 50)
    String professor;

    @Column(nullable = false, columnDefinition = "Float(3,2) DEFAULT 0")
    float score;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    int hit;

    @Column(columnDefinition = "INT(2) DEFAULT 0")
    int type;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Date registerdate;

    @Column(length  = 500)
    String question;
    @Column(length  = 500)
    String answer;

}
