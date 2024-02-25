package com.nvelich.newsnba.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="hoopers")
public class Hooper {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Count", nullable = false)
    private int count;

}