package com.example.veyis.manageMentorships.entity;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "ADMIN")
public class Admin {
    @Id
    private String username;
    private String password;

}
