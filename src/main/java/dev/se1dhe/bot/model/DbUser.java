package dev.se1dhe.bot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "db_user")
public class DbUser {
    @Id
    private Long id;
    private String userName;
    private int accessLevel;
    private LocalDateTime regDate;
    private String lang;

}
