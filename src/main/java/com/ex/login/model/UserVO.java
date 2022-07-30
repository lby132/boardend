package com.ex.login.model;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserVO {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPass;
    private String loginYn;
}
