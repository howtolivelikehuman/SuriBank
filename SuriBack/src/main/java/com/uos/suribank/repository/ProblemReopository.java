package com.uos.suribank.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.entity.QProblemTable;
import com.uos.suribank.entity.QUser;
import com.uos.suribank.entity.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemReopository extends QuerydslRepositorySupport{

    @Autowired
    private JPAQueryFactory queryFactory;

    public ProblemReopository() {
        super(ProblemTable.class);
    }

    public problemTableDTO getPage(final String type, final String value, Pageable pageable){

        final JPQLQuery<problemInfoDTO> query;
        
        QProblemTable problemTable = QProblemTable.problemTable;
        problemTableDTO pDto = new problemTableDTO();
        
        switch(type){
            case "title":
                query = from(problemTable).select(Projections.constructor(problemInfoDTO.class, 
                problemTable.id, problemTable.title, problemTable.subject, problemTable.professor,
                problemTable.user.name, problemTable.type,problemTable.score, problemTable.hit ))
                        .where(problemTable.title.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "subject":
                query = from(problemTable).select(Projections.constructor(problemInfoDTO.class, 
                problemTable.id, problemTable.title, problemTable.subject, problemTable.professor,
                problemTable.user.name, problemTable.type,problemTable.score, problemTable.hit ))
                        .where(problemTable.subject.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "professor":
                query = from(problemTable).select(Projections.constructor(problemInfoDTO.class, 
                problemTable.id, problemTable.title, problemTable.subject, problemTable.professor,
                problemTable.user.name, problemTable.type,problemTable.score, problemTable.hit ))
                        .where(problemTable.professor.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "type":
                    query = from(problemTable).select(Projections.constructor(problemInfoDTO.class, 
                    problemTable.id, problemTable.title, problemTable.subject, problemTable.professor,
                    problemTable.user.name, problemTable.type,problemTable.score, problemTable.hit ))
                        .where(problemTable.type.intValue().eq(Integer.parseInt(value)));
                break;

            default:
            query = from(problemTable).select(Projections.constructor(problemInfoDTO.class, 
            problemTable.id, problemTable.title, problemTable.subject, problemTable.professor,
            problemTable.user.name, problemTable.type,problemTable.score, problemTable.hit )).fetchAll();
                break;
        }
        pDto.setProbleminfo(getQuerydsl().applyPagination(pageable, query).fetch());
        pDto.setPage(pageable.getPageNumber());
        pDto.setSize(pageable.getPageSize());
        pDto.setSort(pageable.getSort().toString());
        pDto.setNumberofElements(pDto.getProbleminfo().size());
        pDto.setTotalElements((int)query.fetchCount());
        pDto.setTotalPages((pDto.getTotalElements()+pDto.getSize()-1)/pDto.getSize());
        return pDto;
    }

    public Long addProblem(problemAddDTO pAddDTO){

        QProblemTable qProblemTable = QProblemTable.problemTable;
        QUser quser = QUser.user;
        
        System.out.println(pAddDTO.toString());
        User user = queryFactory.selectFrom(quser).where(quser.id.eq(pAddDTO.getUploader_id())).fetchOne();
        System.out.println(user.toString());
        
        if(queryFactory == null){
            System.out.println("왜 null?");
        }

        try{
            queryFactory.insert(qProblemTable)
            .set(qProblemTable.title, pAddDTO.getTitle())
            .set(qProblemTable.subject , pAddDTO.getSubject())
            .set(qProblemTable.type, pAddDTO.getType())
            .set(qProblemTable.professor, pAddDTO.getProfessor())
            .set(qProblemTable.answer ,  pAddDTO.getAnswer())
            .set(qProblemTable.question, pAddDTO.getQuestion())
            .set(qProblemTable.user, user).execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 3L;
    }
}
