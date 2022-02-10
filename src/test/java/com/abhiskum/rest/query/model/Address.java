package com.abhiskum.rest.query.model;

import com.abhiskum.rest.query.annotation.Column;
import com.abhiskum.rest.query.annotation.JoinTable;
import com.abhiskum.rest.query.annotation.Table;

import java.util.List;

@Table(name = "ADDRESS", alias = "ADDRESS")
public class Address {

    @Column(name = "street", alias = "STREET") private String street;
    @JoinTable(alias = "COUNTRY") private Metadata country;

    @JoinTable(alias = "USERS") private List<User> users;
}
