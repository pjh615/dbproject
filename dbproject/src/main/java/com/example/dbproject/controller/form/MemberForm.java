package com.example.dbproject.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberForm {
    @Size(min = 2, max = 25)
    @NotEmpty(message = "ID는 필수 입력 항목입니다.")
    private String memberId;

    @Size(min = 2, max = 25)
    @NotEmpty(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인해주세요.")
    private String password2;

    private String role;
}
