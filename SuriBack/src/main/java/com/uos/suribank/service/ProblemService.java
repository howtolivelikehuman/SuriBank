package com.uos.suribank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.repository.ProblemRepository;

@Service
public class ProblemService {
    
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public problemTableDTO getPage(Pageable pageable){
        Page<ProblemTable> pg = problemRepository.findAll(pageable);

        Page<problemInfoDTO> map = pg.map(ProblemTable -> new problemInfoDTO(
            ProblemTable.getId(), ProblemTable.getTitle(), 
            ProblemTable.getSubject(), ProblemTable.getUser().getNickname(),
            ProblemTable.getExplanation(),
            ProblemTable.getScore(), ProblemTable.getHit()));

        //mapping
        problemTableDTO pTableDTO= new problemTableDTO(map.getContent(), 
        map.getTotalElements(), 
        map.getNumberOfElements(), 
        map.getTotalPages(),
        map.getNumber(),
        map.getSize(),
        map.getSort().toString());

        return pTableDTO;
    }
}
