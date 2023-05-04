package br.com.mercury.mercuryweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateGenerate;
    private String observation;

    @OneToOne
    @JoinColumn
    private Student student;

    public Statement() {
    }

    public Statement(LocalDate dateGenerate, String observation, Student student) {
        this.dateGenerate = dateGenerate;
        this.observation = observation;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGenerate() {
        return dateGenerate;
    }

    public void setDateGenerate(LocalDate dateGenerate) {
        this.dateGenerate = dateGenerate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "id=" + id +
                ", dateGenerate=" + dateGenerate +
                ", observation='" + observation + '\'' +
                ", student=" + student +
                '}';
    }
}
