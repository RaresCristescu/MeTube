package com.metube.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public class CommonEntity implements Serializable {
	private static final long serialVersionUID = -4761954006481818259L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected UUID id;

	@Column(name = "creation", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creation;

	@Column(name = "expires", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date expires;

	@Column(name = "modified", nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modified;

	@PrePersist
	public void prePersist() {
		if (this.creation == null) {
			this.creation = new Date();
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (this.modified == null) {
			this.modified = new Date();
		}
	}

	public CommonEntity(UUID id, Date creation, Date expires, Date modified) {
		super();
		this.id = id;
		this.creation = creation;
		this.expires = expires;
		this.modified = modified;
	}

	public CommonEntity() {
		super();
		this.creation = new Date();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
