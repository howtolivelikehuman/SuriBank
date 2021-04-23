package com.uos.suribank.controller;

import com.uos.suribank.dto.ProfessorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddinfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.exception.InsertErrorException;
import com.uos.suribank.exception.NotFoundException;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.service.ProblemService;

@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    //목록 조회
    @GetMapping(path = "/list")
    public ResponseEntity<?> getList(@ModelAttribute PageableDTO pageableDTO) {
        problemTableDTO pDto = null;
        try{
            pDto = problemService.getProblemList(pageableDTO);

            if(pDto == null){
                throw new NotFoundException("Page not found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(pDto);
    }

    //과목-코드 리스트 받아오기
    @GetMapping(path = "/subjectList")
    public ResponseEntity<?> getSubjectList(){
        List<SubjectDTO> sList = problemService.getSubjectList();

        if(sList == null){
            throw new NotFoundException("Subject not found");
		}
        return ResponseEntity.ok(sList);
    }

    //교수-코드 리스트 받아오기
    @GetMapping(path = "/professorList")
    public ResponseEntity<?> getProfessorList(){
        List<ProfessorDTO> pList = problemService.getProfessorList();

        if(pList == null){
            throw new NotFoundException("Professor not found");
        }
        return ResponseEntity.ok(pList);
    }

    //삽입 - 상대주소
    @RequestMapping(path = "/add", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void addProblem(@RequestPart("data") problemAddinfoDTO pAddinfoDTO,
                           @RequestPart(name = "a_img", required = false) List<MultipartFile> a_img,
                           @RequestPart(name = "q_img", required = false) List<MultipartFile> q_img, Authentication authentication){
        pAddinfoDTO.setUploader_id(Long.parseLong(authentication.getName()));
        boolean result = false;
        try{
            result = problemService.addProblem(pAddinfoDTO, q_img, a_img);
        }catch(Exception e){
            e.printStackTrace();
            throw new InsertErrorException("Failed to Upload Images");
        }
        if(!result){
            throw new InsertErrorException("Failed to insert into DB");
        }
    }

    //상세조회
    @GetMapping(path = "/{id}")
    public ResponseEntity<problemInfoDTO> getInfo(@PathVariable Long id){
        problemInfoDTO pIDTO = problemService.getProblemInfo(id);

        if(pIDTO == null){
			throw new NotFoundException("Page not found");
		}
        return ResponseEntity.ok(pIDTO);
    }
}
