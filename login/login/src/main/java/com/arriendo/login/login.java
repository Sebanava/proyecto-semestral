package com.arriendo.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "login")
public class login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false )
    private String nombre ;

    @Column(nullable = false , unique =  true)
    private String email;

    @Column(nullable = false, unique = true )
    private int numero;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false , unique =   true )
    private String rut;
    
    
    

    
}
