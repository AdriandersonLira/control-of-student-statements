package br.com.mercury.mercuryweb.controllers;

import br.com.mercury.mercuryweb.dto.RequestFormInstitution;
import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import br.com.mercury.mercuryweb.repositories.InstitutionRepository;
import br.com.mercury.mercuryweb.repositories.QuarterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/institutions")
public class InstitutionController {
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private QuarterRepository quarterRepository;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        List<Institution> institutions = this.institutionRepository.findAll();
        modelAndView.setViewName("institutions/index");
        modelAndView.addObject("institutions", institutions);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView current(ModelAndView modelAndView, RequestFormInstitution requestFormInstitution) {
        List<Quarter> quarters = this.quarterRepository.findAll();

        modelAndView.setViewName("institutions/new");
        modelAndView.addObject("quarters", quarters);
        modelAndView.addObject("requestNewInstitution", requestFormInstitution);

        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView create(ModelAndView modelAndView, @Valid RequestFormInstitution requestFormInstitution, BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("institutions/new");
        } else {
            Institution institution = requestFormInstitution.toInstitution();
            this.institutionRepository.save(institution);
            attr.addFlashAttribute("message", "Instituição cadastrada com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/institutions");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id, RedirectAttributes attr) {
        Optional<Institution> optional = this.institutionRepository.findById(id);

        if (optional.isPresent()) {
            Institution institution = optional.get();
            ModelAndView mv = new ModelAndView("institutions/show");
            mv.addObject("institution", institution);
            return mv;

        } else {
            attr.addFlashAttribute("message", "Instituição não encontrada!");
            attr.addFlashAttribute("error", "true");
            ModelAndView mv = new ModelAndView("redirect:/institutions");
            return mv;
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RequestFormInstitution request) {
        Optional<Institution> optional = this.institutionRepository.findById(id);
        List<Quarter> quarters = this.quarterRepository.findAll();

        if (optional.isPresent()) {
            Institution institution = optional.get();
            request.fromInstitution(institution);

            modelAndView.setViewName("institutions/edit");
            modelAndView.addObject("quarters", quarters);
            modelAndView.addObject("institutionId", institution.getId());
            return modelAndView;
        } else {
            ModelAndView mv = new ModelAndView("redirect:/institutions");
            return mv;
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(
        @PathVariable Long id,
        ModelAndView modelAndView,
        @Valid RequestFormInstitution requestFormInstitution,
        BindingResult bindingResult,
        RedirectAttributes attr
    ) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("institutions/edit");
            modelAndView.addObject("institutionId", id);
        } else {
            Optional<Institution> optional = this.institutionRepository.findById(id);

            if (optional.isPresent()) {
                Institution institution = requestFormInstitution.updateInstitution(optional.get());
                this.institutionRepository.save(institution);

                attr.addFlashAttribute("message", "Instituição alterada com sucesso!");
                attr.addFlashAttribute("error", "false");
                modelAndView.setViewName("redirect:/institutions");
            } else {
                modelAndView.setViewName("redirect:/institutions");
            }
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            this.institutionRepository.deleteById(id);
            attr.addFlashAttribute("message", "Instituição #"+id+" removida com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/institutions");
        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/institutions");
        }
        return modelAndView;
    }
}
