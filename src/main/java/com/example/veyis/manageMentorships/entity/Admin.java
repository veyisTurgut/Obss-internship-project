package com.example.veyis.manageMentorships.entity;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Admin {
    @Id
    private String username;
    private String password;

}
