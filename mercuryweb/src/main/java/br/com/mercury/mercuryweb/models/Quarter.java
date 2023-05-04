package br.com.mercury.mercuryweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Quarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer year;
    @NotNull
    private Integer statusQuarter;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate start;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate finish;

    public Quarter() {
    }

    public Quarter(Integer year, Integer statusQuarter, LocalDate start, LocalDate finish) {
        this.year = year;
        this.statusQuarter = statusQuarter;
        this.start = start;
        this.finish = finish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getStatusQuarter() {
        return statusQuarter;
    }

    public void setStatusQuarter(Integer statusQuarter) {
        this.statusQuarter = statusQuarter;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return year + "." + statusQuarter;
    }
}
