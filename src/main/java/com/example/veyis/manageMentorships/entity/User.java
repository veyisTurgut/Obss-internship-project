package com.example.veyis.manageMentorships.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @Column(name = "NAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "GMAIL",unique = true)
    private String gmail_address;
    @Column(name = "MENTORED_PROGRAM")
    private String mentoredProgram;
}