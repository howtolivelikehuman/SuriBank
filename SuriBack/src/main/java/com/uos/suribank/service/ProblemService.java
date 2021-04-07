package com.uos.suribank.service;

import com.uos.suribank.dto.ProblemDTO.problemAddinfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.dto.ProfessorDTO;
import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.pagination.PageableDTO;
import com.uos.suribank.pagination.ProblemPageable;
import com.uos.suribank.repository.ProblemReopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProblemService {


    @Autowired
    private ProblemReopository problemRepository;

    public problemTableDTO getProblemList(PageableDTO page){
        Pageable pageable = ProblemPageable.makePageable(page);
        if(page.getFilter().length() > 0){
            page.setFilters();
        }
        return problemRepository.getPage(page.getFilters(), pageable);
    }

    
    @Transactional
    public boolean addProblem(problemAddinfoDTO pAddinfoDTO, 
    List<MultipartFile> q_img, List<MultipartFile> a_img) throws Exception{
       
        boolean result = problemRepository.addProblem(pAddinfoDTO);
        Long problem_id = problemRepository.getProblemId(pAddinfoDTO);

        String a_path[] = null;
        String q_path[] = null;

        if(q_img != null){
            q_path = uploadImage(q_img, 'Q', problem_id);
        }

        if(a_img != null){
            a_path = uploadImage(a_img, 'A', problem_id);
        }
        
        problemRepository.addImages(q_path,a_path,problem_id);
        return result;
    }

    public String[] uploadImage(List<MultipartFile> files, char type, Long id) throws Exception {
        String[] pathList = new String[files.size()];
        String absolutepath = System.getProperty("user.dir")+"/src/main/resources/";
        String path = "/images/" + id;

        File folder = new File(absolutepath+"/images/");
        if(!folder.exists()){
            folder.mkdir();
        }
        folder = new File(absolutepath + path);
        if(!folder.exists()){
            folder.mkdir();
        }

        for(int i=0; i< files.size(); i++){
            String name = ""+id+type+i;

            //absolute path in this project
            String filename = path +"/"+name+".jpg";
            pathList[i] = filename;
            files.get(i).transferTo(new File(absolutepath + filename));
        }
        return pathList;
    }

    //@Transactional
    public problemInfoDTO getProblemInfo(Long id){
        return problemRepository.getProblemInfo(id); 
    }

    public List<SubjectDTO> getSubjectList(){
        return problemRepository.getSubjectList();
    }

    public List<ProfessorDTO> getProfessorList(){
        return problemRepository.getProfessorList();
    }
}
