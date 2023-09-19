package com.as.entities;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.as.utils.StaticParameters;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable<U> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@CreatedBy
	protected U createdBy;

	@JsonIgnore
	@CreatedDate
	@Temporal(TIMESTAMP)
	protected Date createdDate;

	@JsonIgnore
	@LastModifiedBy
	protected U lastModifiedBy;

	@JsonIgnore
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	protected Date lastModifiedDate;
	
	protected Long creditYear = StaticParameters.creditYear;
	
}