package com.uos.suribank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.pagination.DomainSpec;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.repository.ProblemReopository;

@Service
public class ProblemService {

    @Autowired
    private ProblemReopository problemRepository;

    public Pageable makePageable(PageableDTO page){
        DomainSpec domainSpec = new DomainSpec(ProblemTable.class);
        return domainSpec.getPageable(page);
    }
    
    public problemTableDTO getPage (final String type, final String value, Pageable pageable){
        return problemRepository.getPage(type, value, pageable);
    }

}
