package com.uos.suribank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.QAbstractPersistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddinfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemShortDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.repository.ProblemReopository;
import com.uos.suribank.pagination.ProblemPageable;

@Service
public class ProblemService {


    @Autowired
    private ProblemReopository problemRepository;

    public problemTableDTO getProblemList(PageableDTO page){
        Pageable pageable = ProblemPageable.makePageable(page);
        return problemRepository.getPage(page.getFilter(), pageable);
    }

    
    @Transactional
    public boolean addProblem(problemAddinfoDTO pAddinfoDTO, 
    List<MultipartFile> q_img, List<MultipartFile> a_img) throws Exception{
        //absolute path in this project
        String path = System.getProperty("user.dir") 
        + "\\src\\main\\resources\\images\\"
        + pAddinfoDTO.getTitle();

        String a_path[] = null;
        String q_path[] = null;

        if(q_img != null){
            q_path = uploadImage(q_img, 'Q', pAddinfoDTO.getTitle(), path);
        }

        if(a_img != null){
            a_path = uploadImage(a_img, 'A', pAddinfoDTO.getTitle(), path);
        }
        
        boolean result = problemRepository.addProblem(pAddinfoDTO);
        Long problem_id = problemRepository.getProblemId(pAddinfoDTO.getTitle(), pAddinfoDTO.getProfessor());
        problemRepository.addImages(q_path,a_path,problem_id);
        return result;
    }


    public String[] uploadImage(List<MultipartFile> files, char type, String title, String path) throws Exception {
        String[] pathList = new String[files.size()];
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        for(int i=0; i< files.size(); i++){
            String name = title+type+i;
            pathList[i] = path+"/"+name+".jpg";
            files.get(i).transferTo(new File(pathList[i]));
        }
        return pathList;
    }

    public problemInfoDTO getProblemInfo(Long id){
        return problemRepository.getProblemInfo(id);
    }

    public List<SubjectDTO> getSubjectList(){
        return problemRepository.getSubjectList();
    }

    public void scoreProblem(Long id, int score){
        problemShortDTO psdto = problemRepository.getScoreAndHit(id);
        int nhit = psdto.getHit()+1;
        float nscore = ((nhit-1) * psdto.getScore() + score)/(nhit);
        
        problemRepository.updateScore(id, nhit, nscore);
    }

}
