package com.abhiskum.rest.query.model;

import com.abhiskum.rest.query.annotation.Column;
import com.abhiskum.rest.query.annotation.Table;

@Table(name = "METADATA")
public class Metadata {

    @Column(name = "CODE")
    private String code;
    @Column(name = "DESCRIPTION")
    private String description;
}
