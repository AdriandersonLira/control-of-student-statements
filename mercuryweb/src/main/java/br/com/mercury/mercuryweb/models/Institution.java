package br.com.mercury.mercuryweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String acronym;
    private String phone;

    @OneToOne
    @JoinColumn
    private Quarter currentQuarter;

    public Institution() {
    }

    public Institution(String name, String acronym, String phone, Quarter currentQuarter) {
        this.name = name;
        this.acronym = acronym;
        this.phone = phone;
        this.currentQuarter = currentQuarter;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym.toUpperCase();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Quarter getCurrentQuarter() {
        return currentQuarter;
    }

    public void setCurrentQuarter(Quarter currentQuarter) {
        this.currentQuarter = currentQuarter;
    }

    @Override
    public String toString() {
        return acronym.toUpperCase();
    }
}
