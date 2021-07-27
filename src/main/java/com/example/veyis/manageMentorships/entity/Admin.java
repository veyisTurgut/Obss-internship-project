package com.example.veyis.manageMentorships.entity;
import com.example.veyis.common.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
public class Admin extends BaseEntity {
    @Column(name = "USERNAME",unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;

}
