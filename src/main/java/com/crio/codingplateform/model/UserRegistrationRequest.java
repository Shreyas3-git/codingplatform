package com.crio.codingplateform.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest
{
    @Pattern(regexp = "^[1-9]|0[0-9]$",message = "Invalid UserId")
    private long userId;
    @NotNull(message = "Username should not be null")
    @NotBlank(message = "Username should not be blank")
    @Pattern(regexp = "^[a-zA-Z\s]{3,30}$",message = "Invalid Username")
    private String username;
}
