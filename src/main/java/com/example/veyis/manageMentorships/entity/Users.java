package com.example.veyis.manageMentorships.entity;
import com.example.veyis.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users extends BaseEntity {
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "GMAIL",unique = true)
    private String gmail_address;
    @Column(name = "MENTORED_PROGRAM_ID")
    private Long mentored_program_id;


    @Override
    public String toString(){
        return this.username + " " + this.getId();
    }
}