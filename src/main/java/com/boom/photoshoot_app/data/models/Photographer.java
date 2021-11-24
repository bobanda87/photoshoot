package com.boom.photoshoot_app.data.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "photographers")
public class Photographer {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    public Photographer() {
    }

    public Photographer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Photographer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                '}';
    }
}
