package com.veeteq.finance.bankdocument.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    @Column(name = "crea_dt", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "updt_dt", nullable = false, updatable = true)
    private LocalDateTime updateDateTime;

    @Version
    @Column(name = "vers_nm")
    private Integer version;
    
    protected LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    protected BaseEntity<E> setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    protected LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    protected BaseEntity<E> setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    protected Integer getVersion() {
		return version;
	}

    protected BaseEntity<E> setVersion(Integer version) {
		this.version = version;
		return this;
	}
    
}
