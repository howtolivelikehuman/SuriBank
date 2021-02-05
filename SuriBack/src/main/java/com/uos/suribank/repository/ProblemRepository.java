package com.uos.suribank.repository;

import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.entity.ProblemTable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemTable, Long>{
}
