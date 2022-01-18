package org.example.spring.model.Entity;

import org.example.spring.model.User;

import java.io.Serializable;
import java.util.Objects;

public class UserEntity implements User, Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String email;

    public UserEntity() {
    }

    public UserEntity(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserEntity{");
        sb.append("Id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", Email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
