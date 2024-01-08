package com.polo.api.restful.users.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.polo.api.restful.users.validation.IsExistsDb;
import com.polo.api.restful.users.validation.IsRequired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 307244731458290597L;

	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID uuid;

	@NotNull(message = "{NotNull.user.name}")
	@IsRequired(message = "{IsRequired.user.name}")
	private String name;

	@IsExistsDb(message = "{IsExistsDb.user.email}")
	@Email(message = "{Email.user.email}", regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
	private String email;

	@Pattern(message = "{Pattern.user.password}", regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]{2})[A-Za-z0-9]{8,}$")
	@NotBlank
	private String password;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JsonIgnoreProperties({"users", "handler", "hibernateLazyInitializer"})
	@ManyToMany
	@JoinTable(
			name = "users_roles",
	        joinColumns = @JoinColumn(name="user_id"),
	        inverseJoinColumns = @JoinColumn(name="role_id"),
	        uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})}
			)
	private List<Role> roles;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Phone> phones;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date created;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date modified;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date last_login;

	private String token;

	private boolean isactive;
	
	@Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

	public User() {
		phones = new ArrayList<Phone>();
		created = new Date();
		last_login = new Date();
		roles = new ArrayList<>();
	}

	public UUID getId() {
		return uuid;
	}

	public void setId(UUID id) {
		this.uuid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	
	@PrePersist
	public void prePersist() {
		isactive = true;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
