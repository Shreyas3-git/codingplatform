package com.crio.codingplateform.dto;

import com.crio.codingplateform.entity.Badges;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto
{
    private long userId;
    private String username;
    private int score;
    private Set<Badges> badges;

}
