package com.uos.suribank.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.SubjectDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddDTO;
import com.uos.suribank.dto.ProblemDTO.problemAddinfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemInfoDTO;
import com.uos.suribank.dto.ProblemDTO.problemShortDTO;
import com.uos.suribank.dto.ProblemDTO.problemTableDTO;
import com.uos.suribank.entity.ProblemTable;
import com.uos.suribank.entity.QProblemImage;
import com.uos.suribank.entity.QProblemTable;
import com.uos.suribank.entity.QSubject;
import com.uos.suribank.pagination.ProblemListFilter;

import org.apache.ibatis.annotations.Select;
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

    QProblemTable problemTable;

    public ProblemReopository() {
        super(ProblemTable.class);
    }

    public problemTableDTO getPage(ProblemListFilter filter, Pageable pageable) {

        problemTable = QProblemTable.problemTable;
        problemTableDTO pDto = new problemTableDTO();

        JPQLQuery<problemShortDTO> query = from(problemTable).select(Projections.constructor(problemShortDTO.class,
                problemTable.id, problemTable.title, problemTable.subject.code, problemTable.professor,
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

    private BooleanExpression eqType(String type) {
        if (type.length() > 0) {
            return problemTable.type.intValue().eq(Integer.parseInt(type));
        }
        return null;
    }

    private BooleanExpression eqSubject(Long[] subject) {
        if (subject.length > 0) {
            return problemTable.subject.code.in(subject);
        }
        return null;
    }

    private BooleanExpression eqProfessor(String[] professor) {
        if (professor.length > 0) {
            return problemTable.professor.stringValue().in(professor);
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

    public Long getProblemId(String title, String professor) {
        QProblemTable qProblemTable = QProblemTable.problemTable;
        return queryFactory.from(qProblemTable).select(qProblemTable.id)
                .where(qProblemTable.title.eq(title).and(qProblemTable.professor.eq(professor))).fetchOne();
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
                        problemTable.subject.code, problemTable.professor, problemTable.question, problemTable.answer,
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
