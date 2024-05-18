package com.crio.codingplateform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User
{

    @Id
    private long userId;
    private String username;
    private int score;
    private Set<Badges> badges;


    public void assignBadges(User user) {
        Set<Badges> badges = user.getBadges();
        int score = user.getScore();
        badges.clear();
        if (score >= 1 && score < 30) {
            badges.add(Badges.CODE_NINJA);
        } else if (score >= 30 && score < 60) {
            badges.add(Badges.CODE_CHAMP);
        } else if (score >= 60 && score <= 100) {
            badges.add(Badges.CODE_CHAMP);
        }
    }
}
