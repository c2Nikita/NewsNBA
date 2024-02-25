package com.nvelich.newsnba.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="hoopers")
public class Hoopers {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String Name;

    @Column(name = "Count", nullable = false)
    private int Count;

}