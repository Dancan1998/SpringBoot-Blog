package com.springboot.blogbuiltonspringboot.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private EnumRole role;
}
