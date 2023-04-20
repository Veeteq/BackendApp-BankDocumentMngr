package com.veeteq.finance.bankdocument.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@AttributeOverride(name = "id",   column = @Column(name = "user_id"))
@AttributeOverride(name = "name", column = @Column(name = "user_name_tx"))
@SequenceGenerator(name = "default_seq", sequenceName = "user_seq", allocationSize = 1)
public class Account extends BaseEntity<Account> {
    private static final long serialVersionUID = 2255025764490008004L;
    
    @Column(name = "user_name_tx")
    private String name;
    
    @Override
    public Long getId() {
        return super.getId();
    }
    
    @Override
    public Account setId(Long id) {
        super.setId(id);
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }
    
}
