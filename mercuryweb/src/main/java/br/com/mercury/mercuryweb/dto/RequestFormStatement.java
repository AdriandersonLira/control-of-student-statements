package br.com.mercury.mercuryweb.dto;

import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import br.com.mercury.mercuryweb.models.Statement;
import br.com.mercury.mercuryweb.models.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RequestFormStatement {
    private LocalDate dateGenerate;
    @NotBlank
    @NotNull
    private String observation;

    @OneToOne
    @JoinColumn
    private Student student;

    public Statement toStatement() {
        Statement statement = new Statement();
        statement.setDateGenerate(this.dateGenerate);
        statement.setObservation(this.observation);
        statement.setStudent(this.student);

        return statement;
    }

    public Statement updateStatement(Statement statement) {
        statement.setDateGenerate(this.dateGenerate);
        statement.setObservation(this.observation);
        statement.setStudent(this.student);

        return statement;
    }

    public void fromStatement(Statement statement) {
        this.dateGenerate = statement.getDateGenerate();
        this.observation = statement.getObservation();
        this.student = statement.getStudent();
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
}
