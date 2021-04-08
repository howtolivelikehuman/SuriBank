package com.uos.suribank.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.suribank.dto.SolveDTO.solveDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemDTO;
import com.uos.suribank.dto.SolveDTO.solveProblemInfoDTO;
import com.uos.suribank.dto.SolveDTO.solveTableDTO;
import com.uos.suribank.dto.SolveDTO.solveUserInfoDTO;
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

    QUserProblemSolve qUserPSolve;

    @Autowired
    private JPAQueryFactory queryFactory;


    public void updateAnswer(Long user_id, Long problem_id, solveProblemDTO sProblemDTO){
        qUserPSolve = QUserProblemSolve.userProblemSolve;
        queryFactory.update(qUserPSolve)
                    .where(qUserPSolve.user.id.eq(user_id)
                        .and(qUserPSolve.problem.id.eq(problem_id)))
                    .set(qUserPSolve.userAnswer,sProblemDTO.getUserAnswer())
                    .set(qUserPSolve.score, sProblemDTO.getScore())
                    .execute();
    }

    public solveDTO findAnswer(Long user_id, Long problem_id){
        qUserPSolve = QUserProblemSolve.userProblemSolve;

        return queryFactory.from(qUserPSolve)
                .select(Projections.constructor(solveDTO.class, qUserPSolve.id, qUserPSolve.user.id,
                        qUserPSolve.problem.id, qUserPSolve.userAnswer, qUserPSolve.score, qUserPSolve.solveDate))
                    .where(qUserPSolve.user.id.eq(user_id)
                        .and(qUserPSolve.problem.id.eq(problem_id))).fetchOne();
    } 

    public solveDTO getAnswer(Long solve_id){
        qUserPSolve = QUserProblemSolve.userProblemSolve;

        return queryFactory.from(qUserPSolve)
                     .select(Projections.constructor(solveDTO.class, qUserPSolve.id, qUserPSolve.user.id,
                                                    qUserPSolve.problem.id, qUserPSolve.userAnswer, qUserPSolve.score, qUserPSolve.solveDate))
                    .where(qUserPSolve.id.eq(solve_id)).fetchOne();
    } 

    public void insertAnswer(Long user_id,Long problem_id, solveProblemDTO solveProblemDTO){
        EntityManager entityManager= entityManagerFactory.createEntityManager();
        String sql = "insert into user_solve (problem, user, user_answer, score) values (:a, :b, :c, :d)";
    
        try{
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(sql)
                    .setParameter("a", problem_id)
                    .setParameter("b", user_id)
                    .setParameter("c", solveProblemDTO.getUserAnswer())
                    .setParameter("d",solveProblemDTO.getScore())
                    .executeUpdate();
            entityManager.getTransaction().commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    public solveTableDTO getSolvedAnswerList(Pageable pageable,int type, Long userId, Long problemId){
        qUserPSolve = QUserProblemSolve.userProblemSolve;
        solveTableDTO solvetableDTO = new solveTableDTO();
        JPQLQuery<?> query = from(qUserPSolve);

        switch(type){
            //user's solved answer
            case 1:
                query = query.select(Projections.constructor(solveUserInfoDTO.class,
                                            qUserPSolve.id, qUserPSolve.problem.id, qUserPSolve.problem.title,
                                            qUserPSolve.user.id, qUserPSolve.user.nickname))
                                        .where(qUserPSolve.user.id.eq(userId));
                break;
            //problem's solved answer
            case 2:
                System.out.println(userId);
                query = query.select(Projections.constructor(solveProblemInfoDTO.class,
                                            qUserPSolve.id, qUserPSolve.user.id, qUserPSolve.user.nickname,
                                            qUserPSolve.userAnswer, qUserPSolve.solveDate))
                                            .where(qUserPSolve.problem.id.eq(problemId)
                                            .and(qUserPSolve.user.id.ne(userId)));
                break;
            default:
                break;
        }
        
        //setting solveTable
        solvetableDTO.setSolvedInfo(getQuerydsl().applyPagination(pageable,query).fetch());
        solvetableDTO.setPage(pageable.getPageNumber());
        solvetableDTO.setSize(pageable.getPageSize());
        solvetableDTO.setSort(pageable.getSort().toString());
        solvetableDTO.setNumberofElements(solvetableDTO.getSolvedInfo().size());
        solvetableDTO.setTotalElements((int) query.fetchCount());
        solvetableDTO.setTotalPages((solvetableDTO.getTotalElements() + solvetableDTO.getSize() - 1) / solvetableDTO.getSize());
        return solvetableDTO;
    }
}
