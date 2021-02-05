package com.uos.suribank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.service.ProblemService;

@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    // 조회
    @GetMapping(path = "/{page}")
    public Page<problemInfoDTO> getList(Pageable pageable) {
        return problemService.getPage(pageable);
    }
}
