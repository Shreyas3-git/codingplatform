package com.crio.codingplateform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
        int score = user.getScore();
        if(this.badges.size() == 3)
            return;
        if (this.badges.isEmpty() && score >= 1 && score < 30 || !this.badges.contains(Badges.CODE_NINJA) && score >= 1 && score < 30) {
            this.badges.add(Badges.CODE_NINJA);
        } else if (this.badges.isEmpty() && score >= 30 && score < 60 || !this.badges.contains(Badges.CODE_CHAMP) && score >= 30 && score < 60) {
            this.badges.add(Badges.CODE_CHAMP);
        } else if (this.badges.isEmpty() && score >= 60 && score <= 100 || !this.badges.contains(Badges.CODE_MASTER) && score >= 60 && score <= 100) {
            this.badges.add(Badges.CODE_MASTER);
        }
    }
}
