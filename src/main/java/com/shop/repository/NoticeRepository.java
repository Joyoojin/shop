package com.shop.repository;


import com.shop.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * 게시판
 */
public interface NoticeRepository extends JpaRepository<Notice, Long>,
        QuerydslPredicateExecutor<Notice>, NoticeRepositoryCustom {


    List<Notice> findByNoticeID(Long noticeID);

    List<Notice> findByNoticeTitleContaining(String keyword);

    //조회수 증가
    @Modifying
    @Query("update Notice n set n.noticeHit = n.noticeHit + 1 where n.noticeID = :noticeID")
    int updateNoticeHit(Long noticeID);

}


 /* 추가 설명 )

        @Modifying 어노테이션은 @Query 어노테이션에서 작성된 조회를 제외한 데이터의 변경이 있는 삽입(Insert), 수정(Update), 삭제(Delete) 쿼리 사용시 필요한 어노테이션이다

*/