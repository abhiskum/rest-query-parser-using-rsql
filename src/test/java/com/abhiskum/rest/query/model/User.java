package com.abhiskum.rest.query.model;

import com.abhiskum.rest.query.annotation.Column;
import com.abhiskum.rest.query.annotation.JoinTable;
import com.abhiskum.rest.query.annotation.Table;

import java.util.List;

@Table(name= "USER", alias = "USER")
public class User {

    @Column(name = "USER_NAME", alias = "USER_NAME")
    private String name;
    @Column(name = "AGE", alias = "USER_AGE")
    private int age;
    @JoinTable(alias = "ADDRESS")
    private List<Address> addresses;
    @JoinTable(alias = "GENDER")
    private Metadata gender;
    @Column(name = "IS_INDIAN", alias = "IS_INDIAN")
    private boolean indian;

    @JoinTable(alias = "MANAGER")
    private User manager;

    @JoinTable(alias = "ALIAS")
    @Column(name = "NAME")
    private List<String> aliasNames;

}
