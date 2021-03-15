package com.uos.suribank.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveInfoDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.dto.SolveDTO.solveTableDTO;
import com.uos.suribank.entity.QUserProblemSolve;
import com.uos.suribank.entity.UserProblemSolve;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class SolveRepository extends QuerydslRepositorySupport{
    
    public SolveRepository() {
        super(UserProblemSolve.class);
    }
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    QUserProblemSolve qProblemSolve;

    @Autowired
    private JPAQueryFactory queryFactory;


    public void updateAnswer(Long user_id, Long problem_id, solveProblemDTO sProblemDTO){
        qProblemSolve = QUserProblemSolve.userProblemSolve;
        queryFactory.update(qProblemSolve)
                    .where(qProblemSolve.user.id.eq(user_id)
                        .and(qProblemSolve.problem.id.eq(problem_id)))
                    .set(qProblemSolve.userAnswer,sProblemDTO.getUserAnswer())
                    .execute();
    }

    public solveDTO findAnswer(Long user_id, Long problem_id){
        qProblemSolve = QUserProblemSolve.userProblemSolve;

        return queryFactory.from(qProblemSolve)
                     .select(Projections.constructor(solveDTO.class, qProblemSolve.id, qProblemSolve.user.id,
                                                    qProblemSolve.problem.id, qProblemSolve.userAnswer, qProblemSolve.solveDate))
                    .where(qProblemSolve.user.id.eq(user_id)
                        .and(qProblemSolve.problem.id.eq(problem_id))).fetchOne();
    } 

    public solveDTO getAnswer(Long solve_id){
        qProblemSolve = QUserProblemSolve.userProblemSolve;

        return queryFactory.from(qProblemSolve)
                     .select(Projections.constructor(solveDTO.class, qProblemSolve.id, qProblemSolve.user.id,
                                                    qProblemSolve.problem.id, qProblemSolve.userAnswer, qProblemSolve.solveDate))
                    .where(qProblemSolve.id.eq(solve_id)).fetchOne();
    } 

    public void insertAnswer(Long user_id,Long problem_id, solveProblemDTO solveProblemDTO){
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        String sql = "insert into user_solve (problem, user, user_answer) values (:a, :b, :c)";
    
        try{
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(sql)
                    .setParameter("a", problem_id)
                    .setParameter("b", user_id)
                    .setParameter("c", solveProblemDTO.getUserAnswer()).executeUpdate();
            entityManager.getTransaction().commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    public solveTableDTO getSolvedAnswerList(Pageable pageable,int type, Long id){
        qProblemSolve = QUserProblemSolve.userProblemSolve;
        solveTableDTO solvetableDTO = new solveTableDTO();

        JPQLQuery<solveInfoDTO> query = from(qProblemSolve)
                                        .select(Projections.constructor(solveInfoDTO.class,
                                            qProblemSolve.id, qProblemSolve.problem.id, qProblemSolve.problem.title, 
                                            qProblemSolve.user.id, qProblemSolve.user.nickname));
        switch(type){
            //user's solved answer
            case 1:
                query = query.where(qProblemSolve.user.id.eq(id));
                break;
            //problem's solved answer
            case 2:
                query = query.where(qProblemSolve.problem.id.eq(id));
                break;
            default:
                break;
        }
        
        //setting solveTable
        solvetableDTO.setSolvedInfo(getQuerydsl().applyPagination(pageable, query).fetch());
        solvetableDTO.setPage(pageable.getPageNumber());
        solvetableDTO.setSize(pageable.getPageSize());
        solvetableDTO.setSort(pageable.getSort().toString());
        solvetableDTO.setNumberofElements(solvetableDTO.getSolvedInfo().size());
        solvetableDTO.setTotalElements((int) query.fetchCount());
        solvetableDTO.setTotalPages((solvetableDTO.getTotalElements() + solvetableDTO.getSize() - 1) / solvetableDTO.getSize());
        return solvetableDTO;
    }
}
