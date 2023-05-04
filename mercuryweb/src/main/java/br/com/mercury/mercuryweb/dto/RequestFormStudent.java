package br.com.mercury.mercuryweb.dto;

import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestFormStudent {
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String registration;

    @OneToOne
    @JoinColumn
    private Institution currentInstitution;

    public Student toStudent() {
        Student student = new Student();
        student.setName(this.name);
        student.setRegistration(this.registration);
        student.setCurrentInstitution(this.currentInstitution);

        return student;
    }

    public Student updateStudent(Student student) {
        student.setName(this.name);
        student.setRegistration(this.registration);
        student.setCurrentInstitution(this.currentInstitution);

        return student;
    }

    public void fromStudent(Student student) {
        this.name = student.getName();
        this.registration = student.getRegistration();
        this.currentInstitution = student.getCurrentInstitution();
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
}
