package com.uos.suribank.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.ProblemDTO.problemAddinfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemShortDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.dto.ProfessorDTO;
import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.entity.*;
import com.uos.suribank.pagination.FilterDTO;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProblemReopository extends QuerydslRepositorySupport {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JPAQueryFactory queryFactory;

    QProblemTable problemTable;

    public ProblemReopository() {
        super(ProblemTable.class);
    }

    public problemTableDTO getPage(FilterDTO filter, Pageable pageable) {

        problemTable = QProblemTable.problemTable;
        problemTableDTO pDto = new problemTableDTO();

        JPQLQuery<problemShortDTO> query = from(problemTable).select(Projections.constructor(problemShortDTO.class,
                problemTable.id, problemTable.title, problemTable.subject.code, problemTable.professor.code,
                problemTable.user.name, problemTable.type, problemTable.score, problemTable.hit));

        if(filter != null){
            // set filter
            query = query.where(eqType(filter.getType()), eqSubject(filter.getSubject()),
            eqProfessor(filter.getProfessor()));
        }
        
        // setting pDto
        pDto.setProbleminfo(getQuerydsl().applyPagination(pageable, query).fetch());
        pDto.setPage(pageable.getPageNumber());
        pDto.setSize(pageable.getPageSize());
        pDto.setSort(pageable.getSort().toString());
        pDto.setNumberofElements(pDto.getProbleminfo().size());
        pDto.setTotalElements((int) query.fetchCount());
        pDto.setTotalPages((pDto.getTotalElements() + pDto.getSize() - 1) / pDto.getSize());
        return pDto;
    }

    private BooleanExpression eqType(List<Long> type) {
        if (type.size() > 0) {
            return problemTable.type.longValue().in(type);
        }
        return null;
    }

    private BooleanExpression eqSubject(List<Long> subject) {
        if (subject.size() > 0) {
            return problemTable.subject.code.in(subject);
        }
        return null;
    }

    private BooleanExpression eqProfessor(List<Long> professor) {
        if (professor.size() > 0) {
            return problemTable.professor.code.in(professor);
        }
        return null;
    }

    @Transactional
    public void addImages(String[] q_path, String[] a_path, Long problemId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sql = "insert into problem_image (problem_id, type, path) values(:a, :b, :c)";

        try {
            if (q_path != null) {
                // add Question Image
                for (int i = 0; i < q_path.length; i++) {
                    entityManager.getTransaction().begin();
                    entityManager.createNativeQuery(sql).setParameter("a", problemId).setParameter("b", 0)
                            .setParameter("c", q_path[i]).executeUpdate();
                    entityManager.getTransaction().commit();
                }
            }
            if (a_path != null) {
                // add Answer Image
                for (int i = 0; i < a_path.length; i++) {
                    entityManager.getTransaction().begin();
                    entityManager.createNativeQuery(sql).setParameter("a", problemId).setParameter("b", 1)
                            .setParameter("c", a_path[i]).executeUpdate();
                    entityManager.getTransaction().commit();
                }
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    public Long getProblemId(problemAddinfoDTO pAddDTO) {
        QProblemTable qProblemTable = QProblemTable.problemTable;
        return queryFactory.from(qProblemTable).select(qProblemTable.id)
                .where(qProblemTable.title.eq(pAddDTO.getTitle()).and(qProblemTable.professor.code.eq(pAddDTO.getProfessor()))).fetchOne();
    }

    public boolean addProblem(problemAddinfoDTO pAddDTO) {
        int result = 0;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = "insert into problem_table (title, subject, professor, answer, question, type, uploader_id)"
                + "values( :a, :b, :c, :d, :e, :f, :g)";

        try {
            entityManager.getTransaction().begin();
            result = entityManager.createNativeQuery(sql).setParameter("a", pAddDTO.getTitle())
                    .setParameter("b", pAddDTO.getSubject()).setParameter("c", pAddDTO.getProfessor())
                    .setParameter("d", pAddDTO.getAnswer()).setParameter("e", pAddDTO.getQuestion())
                    .setParameter("f", pAddDTO.getType()).setParameter("g", pAddDTO.getUploader_id()).executeUpdate();
            entityManager.getTransaction().commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();

        } finally {
            entityManager.close();
        }
        return result > 0 ? true : false;
    }

    @Transactional
    public problemInfoDTO getProblemInfo(Long id) {
        problemTable = QProblemTable.problemTable;
        QProblemImage problemImage = QProblemImage.problemImage;

        problemInfoDTO pinfo = queryFactory.from(problemTable)
                .select(Projections.constructor(problemInfoDTO.class, problemTable.id, problemTable.title,
                        problemTable.subject.code, problemTable.professor.code, problemTable.question, problemTable.answer,
                        problemTable.user.name, problemTable.registerdate, problemTable.type, problemTable.score,
                        problemTable.hit))
                .where(problemTable.id.eq(id)).fetchOne();
        pinfo.setQimagesPath(from(problemImage).select(problemImage.path).where(problemImage.type.eq(0),problemImage.problemTable.id.eq(id)).fetch());
        pinfo.setAimagesPath(from(problemImage).select(problemImage.path).where(problemImage.type.eq(1),problemImage.problemTable.id.eq(id)).fetch());
        return pinfo;
    }

    public List<SubjectDTO> getSubjectList() {
        QSubject subject = QSubject.subject;

        return queryFactory.from(subject).select(Projections.constructor(SubjectDTO.class, subject.code, subject.name))
                .fetch();
    }

    public List<ProfessorDTO> getProfessorList() {
        QProfessor professor = QProfessor.professor;

        return queryFactory.from(professor).select(Projections.constructor(ProfessorDTO.class, professor.code, professor.name))
                .fetch();
    }

    public problemShortDTO getScoreAndHit(Long id) {
        problemTable = QProblemTable.problemTable;
        return queryFactory.from(problemTable).select(Projections.constructor(problemShortDTO.class, problemTable.hit, problemTable.score))
                .where(problemTable.id.eq(id)).fetchOne();
    }

    public void updateScore(Long id, int nhit, float nscore){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            ProblemTable pTable = entityManager.find(ProblemTable.class, id);
            pTable.setHit(nhit);
            pTable.setScore(nscore);
            entityManager.getTransaction().commit();
        }catch (HibernateException ex) {
            ex.printStackTrace();
            throw ex;

        } finally {
            entityManager.close();
        }
    }
}
