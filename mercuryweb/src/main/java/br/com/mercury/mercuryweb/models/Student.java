package br.com.mercury.mercuryweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String registration;
    @OneToOne
    @JoinColumn
    private Institution currentInstitution;
    public Student() {
    }

    public Student(String name, String registration, Institution currentInstitution) {
        this.name = name;
        this.registration = registration;
        this.currentInstitution = currentInstitution;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Institution getCurrentInstitution() {
        return currentInstitution;
    }

    public void setCurrentInstitution(Institution currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registration='" + registration + '\'' +
                ", currentInstitution=" + currentInstitution +
                '}';
    }
}
