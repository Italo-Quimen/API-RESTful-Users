package com.polo.api.restful.users.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "phones")
public class Phone implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int number;
	private int citycode;
	private int contrycode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public int getContrycode() {
		return contrycode;
	}

	public void setContrycode(int contrycode) {
		this.contrycode = contrycode;
	}

	@XmlTransient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;

}
