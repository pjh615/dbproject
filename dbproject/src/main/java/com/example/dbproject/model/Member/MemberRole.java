package com.example.dbproject.model.Member;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    BARTENDER("ROLE_BARTENDER"),
    MEMBER("ROLE_MEMBER");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }

}
