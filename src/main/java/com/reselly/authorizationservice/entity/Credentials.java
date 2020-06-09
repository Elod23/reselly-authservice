package com.reselly.authorizationservice.entity;

import javax.persistence.*;

@Entity(name = "users")
@Table(name = "users")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String authorizationRealm;

    public Credentials() {}

    public Credentials(long id, String email, String password, String authorizationRealm) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorizationRealm = authorizationRealm;
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

    public String getAuthorizationRealm() {
        return authorizationRealm;
    }

    public void setAuthorizationRealm(String authorizationRealm) {
        this.authorizationRealm = authorizationRealm;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
