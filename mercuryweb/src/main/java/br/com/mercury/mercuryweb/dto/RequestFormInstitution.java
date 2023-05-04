package br.com.mercury.mercuryweb.dto;

import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestFormInstitution {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String acronym;

    @NotBlank
    @NotNull
    private String phone;

    @OneToOne
    @JoinColumn
    private Quarter currentQuarter;

    public Institution toInstitution() {
        Institution institution = new Institution();
        institution.setName(this.name);
        institution.setAcronym(this.acronym.toUpperCase());
        institution.setPhone(this.phone);
        institution.setCurrentQuarter(this.currentQuarter);

        return institution;
    }

    public Institution updateInstitution(Institution institution) {
        institution.setName(this.name);
        institution.setAcronym(this.acronym.toUpperCase());
        institution.setPhone(this.phone);
        institution.setCurrentQuarter(this.currentQuarter);

        return institution;
    }

    public void fromInstitution(Institution institution) {
        this.name = institution.getName();
        this.acronym = institution.getAcronym();
        this.phone = institution.getPhone();
        this.currentQuarter = institution.getCurrentQuarter();
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
}
