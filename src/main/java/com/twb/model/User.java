package com.twb.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author twb
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;

    @Column(name="email")
    private String email;
	
	@Column(name="role")
    @Enumerated(value = EnumType.STRING)
	private Role role;
	
	@Column(name="full_name")	
	private String fullName;
	
	@Column(name="country")	
	private String country;

    public User(){}

    public User(String username, String password, String email, Role role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}


