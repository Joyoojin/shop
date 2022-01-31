package com.shop.constant;


public enum Role {
    USER, ADMIN  // USER 일반유저 , ADMIN 관리자로 Role역할 구분.
}

/* 인증에 따른 접근 가능 페이지

1) 인증이 필요 없이 접근 가능 : 상품 상세 페이지 조회
2) ( USER,ADMIN ) 인증 시 접근 가능 : 상품 주문
3) ADMIN 권한만 접근 가능 : 상품 등록

 */