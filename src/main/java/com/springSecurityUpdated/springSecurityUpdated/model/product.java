package com.springSecurityUpdated.springSecurityUpdated.model;


import jakarta.persistence.*;

@Table(name = "products")
@Entity
public class product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String decription;

}
