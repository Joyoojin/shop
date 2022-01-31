package com.shop.repository;

import com.shop.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final EntityManager em;


    //관리자 회원조회
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //삭제하기
    @Override
    public void deleteByMemID(String memID) {
        em.createQuery("delete * from Member m where m.memID = memID ", Member.class);
    }

}
