package br.com.mercury.mercuryweb.controllers;

import br.com.mercury.mercuryweb.dto.RequestFormQuarter;
import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import br.com.mercury.mercuryweb.repositories.InstitutionRepository;
import br.com.mercury.mercuryweb.repositories.QuarterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/quarters")
public class QuarterController {
    @Autowired
    private QuarterRepository quarterRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        List<Quarter> quarters = this.quarterRepository.findAll();

        modelAndView.setViewName("quarters/index");
        modelAndView.addObject("quarters", quarters);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView current(ModelAndView modelAndView, RequestFormQuarter request) {
        modelAndView.setViewName("quarters/new");
        modelAndView.addObject("requestNewQuarter", request);

        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView create(ModelAndView modelAndView, @Valid RequestFormQuarter request, BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("quarters/new");
        } else {
            Quarter quarter = request.toQuarter();

            this.quarterRepository.save(quarter);

            // Código para alterar o período de todas as instituições

            Optional<Quarter> optional = this.quarterRepository.findById(quarter.getId());

            if (optional.isPresent()) {
                List<Institution> institutions = this.institutionRepository.findAll();

                institutions.forEach(i -> {
                    Institution institution = i;
                    institution.setCurrentQuarter(quarter);
                    this.institutionRepository.save(institution);
                });
            }

            // ---

            attr.addFlashAttribute("message", "Período cadastrado com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/quarters");
        }
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView show(
        @PathVariable Long id,
        ModelAndView modelAndView,
        RedirectAttributes attr
    ) {
        Optional<Quarter> optional = this.quarterRepository.findById(id);

        if (optional.isPresent()) {
            Quarter quarter = optional.get();
            modelAndView.setViewName("quarters/show");
            modelAndView.addObject("quarter", quarter);

        } else {
            attr.addFlashAttribute("message", "Período não encontrado!");
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/quarters");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RequestFormQuarter request) {
        Optional<Quarter> optional = this.quarterRepository.findById(id);

        if (optional.isPresent()) {
            Quarter quarter = optional.get();
            request.fromQuarter(quarter);

            modelAndView.setViewName("quarters/edit");
            modelAndView.addObject("quarterId", quarter.getId());
        } else {
            modelAndView.setViewName("redirect:/quarters");
        }
        return modelAndView;

    }

    @PostMapping("/{id}")
    public ModelAndView update(
        @PathVariable Long id,
        ModelAndView modelAndView,
        @Valid RequestFormQuarter requestFormQuarter,
        BindingResult bindingResult,
        RedirectAttributes attr
    ) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("quarters/edit");
            modelAndView.addObject("quarterId", id);
        } else {
            Optional<Quarter> optional = this.quarterRepository.findById(id);

            if (optional.isPresent()) {
                Quarter quarter = requestFormQuarter.updateQuarter(optional.get());
                this.quarterRepository.save(quarter);

                attr.addFlashAttribute("message", "Período alterado com sucesso!");
                attr.addFlashAttribute("error", "false");
                modelAndView.setViewName("redirect:/quarters");
            } else {
                modelAndView.setViewName("redirect:/quarters");
            }
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(
        @PathVariable Long id,
        ModelAndView modelAndView,
        RedirectAttributes attr
    ) {
        try {
            this.quarterRepository.deleteById(id);
            attr.addFlashAttribute("message", "Período #"+id+" removido com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/quarters");
        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/quarters");
        }
        return modelAndView;
    }
}
