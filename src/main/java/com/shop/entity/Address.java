package com.shop.entity;
/*
 * 다음 카카오 우편번호 api -주소
 */

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter  //setter는 닫아두어서 변경 방지
public class Address {

    private String postcode;        //우편번호
    private String roadAddress;     //도로명주소
    private String jibunAddress;    //지번
    private String detailAddress;   //상세주소
    private String extraAddress;    //추가주소정보

    protected Address() { //protected
    }

    public Address(String postcode, String roadAddress, String jibunAddress, String detailAddress, String extraAddress) {
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }
}
