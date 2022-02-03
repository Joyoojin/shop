package com.shop.dto;

import com.shop.entity.Member;
import com.shop.validation.RepeatedField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

//(회원가입 ) 화면가입 화면으로부터 넘어오는 가입정보를 담을 dto 생성.

/**
 * 비밀번호 4~20자로 . 비밀번호 재입력 . 우편번호 api 등.
 */

@RepeatedField(field = "password")
@Getter
@Setter
public class MemberFormDto {

    private static ModelMapper modelMapper = new ModelMapper();

    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Length(min = 2, max = 15, message = "아이디는 2자 이상, 15자 이하로 입력해주세요")
    private String memID;

    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식을 확인해주세요.")  //이메일 형식 체크
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력입니다.")
    @Length(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "비밀번호를 다시 입력해주세요.")
    private String repeatedPassword; //비밀번호 확인

    private String postcode;
    /**
     * 다음 카카오 api - address
     */
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;

    @NotEmpty(message = "전화번호는 필수 입력입니다.")
    private String memPhone;

    public static MemberFormDto of(Member member) {
        return modelMapper.map(member, MemberFormDto.class);
    }

    public Member createMember() {
        return modelMapper.map(this, Member.class);
    }


}