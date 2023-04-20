package com.veeteq.finance.bankdocument.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_seq")
    private Long id;

    protected Long getId() {
        return id;
    }

    protected BaseEntity<E> setId(Long id) {
        this.id = id;
        return this;
    }

}
