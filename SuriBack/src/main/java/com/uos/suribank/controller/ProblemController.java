package com.uos.suribank.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    //목록 조회
    @PostMapping(path = "/list")
    public ResponseEntity<?> getList(@RequestBody PageableDTO pageableDTO) {
        
        problemTableDTO pDto = problemService.getProblemList(pageableDTO);

        if(pDto == null){
			throw new NotFoundException("Page not found");
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


<<<<<<< HEAD
    //삽입 - 절대주소
=======
    //삽입
>>>>>>> 314ad52f9ddb118337eefb8656e21207cc910389
    @RequestMapping(path = "/add", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void addProblem(@RequestPart("data") problemAddinfoDTO pAddinfoDTO, 
    @RequestPart("a_img") List<MultipartFile> a_img, @RequestPart("q_img") List<MultipartFile> q_img,
     Authentication authentication){
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

<<<<<<< HEAD
    //삽입2 - 상대주소
=======
    //삽입2 - t상
>>>>>>> 314ad52f9ddb118337eefb8656e21207cc910389
    @RequestMapping(path = "/add2", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void addProblem2(@RequestPart("data") problemAddinfoDTO pAddinfoDTO, 
    @RequestPart("a_img") List<MultipartFile> a_img, @RequestPart("q_img") List<MultipartFile> q_img,
     Authentication authentication){
        pAddinfoDTO.setUploader_id(Long.parseLong(authentication.getName()));

        boolean result = false;
        try{
            result = problemService.addProblem2(pAddinfoDTO, q_img, a_img);
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

    //평가하기
    @PostMapping(path = "score/{id}", produces = "application/json")
    public void scoreProblem(@PathVariable Long id, @RequestParam("score") int score){
        try{
            problemService.scoreProblem(id, score);
        }catch(Exception e){
            e.printStackTrace();
            throw new InsertErrorException("Failed to Update score");
        }
    }
}
