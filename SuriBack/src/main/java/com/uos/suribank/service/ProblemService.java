package com.uos.suribank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.entity.User;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.repository.ProblemReopository;
import com.uos.suribank.pagination.ProblemPageable;

@Service
public class ProblemService {

    @Autowired
    private ProblemReopository problemRepository;

    public problemTableDTO getProblemList(PageableDTO page){
        Pageable pageable = ProblemPageable.makePageable(page);
        return problemRepository.getPage(page.getType(), page.getValue(), pageable);
    }

    public Long addProblem(problemAddDTO pAddDTO){
        return problemRepository.addProblem(pAddDTO);
    }
}
