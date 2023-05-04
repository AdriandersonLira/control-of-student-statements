package br.com.mercury.mercuryweb.dto;

import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RequestFormQuarter {
    @NotNull
    private Integer year;

    @NotNull
    private Integer statusQuarter;


    @NotNull
    private LocalDate start;


    @NotNull
    private LocalDate finish;

    public Quarter toQuarter() {
        Quarter quarter = new Quarter();
        quarter.setYear(this.year);
        quarter.setStatusQuarter(this.statusQuarter);
        quarter.setStart(this.start);
        quarter.setFinish(this.finish);

        return quarter;
    }

    public Quarter updateQuarter(Quarter quarter) {
        quarter.setYear(this.year);
        quarter.setStatusQuarter(this.statusQuarter);
        quarter.setStart(this.start);
        quarter.setFinish(this.finish);

        return quarter;
    }

    public void fromQuarter(Quarter quarter) {
        this.year = quarter.getYear();
        this.statusQuarter = quarter.getStatusQuarter();
        this.start = quarter.getStart();
        this.finish = quarter.getFinish();
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
}
