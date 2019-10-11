package com.bolsadeideas.springboot.backend.apirest.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CREATED_AT")
	@JsonIgnore
	private Date createdAt;

	@Column(name = "UPDATE_AT")
	@JsonIgnore
	private Date updateAt;

	@JsonIgnore
	private boolean status;
	
	@JsonIgnore
    @Version
    private Integer version = 0;

	public BaseEntity() {
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@PrePersist
	private void prePersist() {
		this.status = true;
		this.createdAt = new Date();
	}
	
	@PostPersist
	private void postPersist() {
		this.updateAt = new Date();
	}
}
