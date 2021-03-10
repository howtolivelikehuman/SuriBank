package com.uos.suribank.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.entity.QUserProblemSolve;
import com.uos.suribank.entity.UserProblemSolve;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class SolveRepository extends QuerydslRepositorySupport{
    
    public SolveRepository() {
        super(UserProblemSolve.class);
    }
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JPAQueryFactory queryFactory;


    public void updateAnswer(Long user_id, solveProblemDTO sProblemDTO){
        QUserProblemSolve problemSolve = QUserProblemSolve.userProblemSolve;
        queryFactory.update(problemSolve)
                    .where(problemSolve.user.id.eq(user_id)
                        .and(problemSolve.problem.id.eq(sProblemDTO.getProblem())))
                    .set(problemSolve.userAnswer,sProblemDTO.getUserAnswer())
                    .execute();
    }

    public solveDTO getAnswer(Long user_id, Long problem_id){
        QUserProblemSolve qproblemSolve = QUserProblemSolve.userProblemSolve;

        return queryFactory.from(qproblemSolve)
                     .select(Projections.constructor(solveDTO.class, qproblemSolve.id, qproblemSolve.user.id, 
                                                    qproblemSolve.problem.id, qproblemSolve.userAnswer))
                    .where(qproblemSolve.user.id.eq(user_id)
                        .and(qproblemSolve.problem.id.eq(problem_id))).fetchOne();
    } 

    public void insertAnswer(Long user_id, solveProblemDTO solveProblemDTO){
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        String sql = "insert into user_solve (problem, user, user_answer) values (:a, :b, :c)";
    
        try{
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(sql)
                    .setParameter("a", solveProblemDTO.getProblem())
                    .setParameter("b", user_id)
                    .setParameter("c", solveProblemDTO.getUserAnswer()).executeUpdate();
            entityManager.getTransaction().commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();

        } finally {
            entityManager.close();
        }
    }
}
