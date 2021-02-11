package com.uos.suribank.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemShortDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.entity.QProblemTable;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemReopository extends QuerydslRepositorySupport {

    @Autowired
	private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JPAQueryFactory queryFactory;
    
    public ProblemReopository() {
        super(ProblemTable.class);
    }

    public problemTableDTO getPage(final String type, final String value, Pageable pageable) {

        QProblemTable problemTable = QProblemTable.problemTable;
        problemTableDTO pDto = new problemTableDTO();

        JPQLQuery<problemShortDTO> query= from(problemTable)
                                        .select(Projections.constructor(problemShortDTO.class, problemTable.id, problemTable.title,
                                                problemTable.subject, problemTable.professor, problemTable.user.name, problemTable.type,
                                                 problemTable.score, problemTable.hit));
        switch (type) {
            case "title":
                query = query.where(problemTable.title.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "subject":
                query = query.where(problemTable.subject.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "professor":
                query = query.where(problemTable.professor.stringValue().likeIgnoreCase(value + "%"));
                break;

            case "type":
                query =query.where(problemTable.type.intValue().eq(Integer.parseInt(value)));
                break;

            default:
                query = query.fetchAll();
                break;
        }
        pDto.setProbleminfo(getQuerydsl().applyPagination(pageable, query).fetch());
        pDto.setPage(pageable.getPageNumber());
        pDto.setSize(pageable.getPageSize());
        pDto.setSort(pageable.getSort().toString());
        pDto.setNumberofElements(pDto.getProbleminfo().size());
        pDto.setTotalElements((int) query.fetchCount());
        pDto.setTotalPages((pDto.getTotalElements() + pDto.getSize() - 1) / pDto.getSize());
        return pDto;
    }

    public boolean addProblem(problemAddDTO pAddDTO){
        int result = 0;
        String sql = "insert into problem_table (title, subject, professor, answer, question, type, uploader_id)"
                     + "values( :a, :b, :c, :d, :e, :f, :g)";
        
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            result = entityManager.createNativeQuery(sql)
            .setParameter("a", pAddDTO.getTitle())
            .setParameter("b", pAddDTO.getSubject())
            .setParameter("c", pAddDTO.getProfessor())
            .setParameter("d", pAddDTO.getAnswer())
            .setParameter("e", pAddDTO.getQuestion())
            .setParameter("f", pAddDTO.getType())
            .setParameter("g", pAddDTO.getUploader_id()).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (HibernateException ex)
        {
            ex.printStackTrace();
        }
        return result > 0 ? true : false;
    }


    public problemInfoDTO getProblemInfo(Long id){
        QProblemTable problemTable = QProblemTable.problemTable;

        return queryFactory.from(problemTable).select(Projections.constructor(problemInfoDTO.class,
                        problemTable.id, problemTable.title, problemTable.subject, 
                        problemTable.professor, problemTable.question, problemTable.answer,
                        problemTable.user.name, problemTable.registerdate, problemTable.type, 
                        problemTable.score, problemTable.hit))
                .where(problemTable.id.eq(id)).fetchOne();
    }
}
