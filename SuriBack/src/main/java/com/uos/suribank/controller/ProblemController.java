package com.uos.suribank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.exception.UserNotFoundException;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.service.ProblemService;

@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;


    //조회2
    @GetMapping(path = "/list")
    public ResponseEntity<?> getList(@RequestBody PageableDTO pageableDTO) {

        System.out.println(pageableDTO.getType() + " " + pageableDTO.getValue() + " " + pageableDTO.getSorts());
        
        problemTableDTO pDto = problemService.getPage(pageableDTO.getType(), pageableDTO.getValue(), problemService.makePageable(pageableDTO));

        if(pDto == null){
			throw new UserNotFoundException("Page not found");
		}
        return ResponseEntity.ok(pDto);
    }

    //조회2
    @GetMapping(path = "/list2")
    public ResponseEntity<?> getList2(@RequestParam(name ="type") final String type, 
                                      @RequestParam(name = "value") final String value, Pageable pageable) {

        problemTableDTO pDto = problemService.getPage(type, value, pageable);

        if(pDto == null){
			throw new UserNotFoundException("Page not found");
		}
        return ResponseEntity.ok(pDto);
    }
}
