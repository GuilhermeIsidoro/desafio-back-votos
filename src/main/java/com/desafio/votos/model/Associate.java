package com.desafio.votos.model;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "ASSOCIATE", schema = "DESAFIO_VOTOS")
public class Associate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Associate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String maskCPF() {
    	return "***" + this.id.substring(3,9) + "**";
    }
}
