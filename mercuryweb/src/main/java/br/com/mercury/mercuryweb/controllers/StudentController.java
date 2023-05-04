package br.com.mercury.mercuryweb.controllers;

import br.com.mercury.mercuryweb.dto.RequestFormInstitution;
import br.com.mercury.mercuryweb.dto.RequestFormStatement;
import br.com.mercury.mercuryweb.dto.RequestFormStudent;
import br.com.mercury.mercuryweb.models.Institution;
import br.com.mercury.mercuryweb.models.Quarter;
import br.com.mercury.mercuryweb.models.Statement;
import br.com.mercury.mercuryweb.models.Student;
import br.com.mercury.mercuryweb.repositories.InstitutionRepository;
import br.com.mercury.mercuryweb.repositories.QuarterRepository;
import br.com.mercury.mercuryweb.repositories.StatementRepository;
import br.com.mercury.mercuryweb.repositories.StudentRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private StatementRepository statementRepository;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        List<Student> students = this.studentRepository.findAll();
        modelAndView.setViewName("students/index");
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView current(ModelAndView modelAndView, RequestFormStudent requestFormStudent) {
        List<Institution> institutions = this.institutionRepository.findAll();

        modelAndView.setViewName("students/new");
        modelAndView.addObject("institutions", institutions);
        modelAndView.addObject("requestFormStudent", requestFormStudent);

        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView create(ModelAndView modelAndView, @Valid RequestFormStudent requestFormStudent, BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("students/new");
        } else {
            Student student = requestFormStudent.toStudent();
            this.studentRepository.save(student);
            attr.addFlashAttribute("message", "Estudante cadastrado com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/students");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id, RedirectAttributes attr) {
        Optional<Student> optional = this.studentRepository.findById(id);

        List<Statement> statements = this.statementRepository.findAll();

        if (optional.isPresent()) {
            Student student = optional.get();
            ModelAndView mv = new ModelAndView("students/show");
            mv.addObject("student", student);
            mv.addObject("statements", statements);

            return mv;

        } else {
            attr.addFlashAttribute("message", "Estudante não encontrado!");
            attr.addFlashAttribute("error", "true");
            ModelAndView mv = new ModelAndView("redirect:/students");
            return mv;
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RequestFormStudent request) {
        Optional<Student> optional = this.studentRepository.findById(id);
        List<Institution> institutions = this.institutionRepository.findAll();

        if (optional.isPresent()) {
            Student student = optional.get();
            request.fromStudent(student);

            modelAndView.setViewName("students/edit");
            modelAndView.addObject("institutions", institutions);
            modelAndView.addObject("studentId", student.getId());
            return modelAndView;
        } else {
            ModelAndView mv = new ModelAndView("redirect:/students");
            return mv;
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(
        @PathVariable Long id,
        ModelAndView modelAndView,
        @Valid RequestFormStudent request,
        BindingResult bindingResult,
        RedirectAttributes attr
    ) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("students/edit");
            modelAndView.addObject("studentId", id);
        } else {
            Optional<Student> optional = this.studentRepository.findById(id);

            if (optional.isPresent()) {
                Student student = request.updateStudent(optional.get());
                this.studentRepository.save(student);

                attr.addFlashAttribute("message", "Estudante alterado com sucesso!");
                attr.addFlashAttribute("error", "false");
                modelAndView.setViewName("redirect:/students");
            } else {
                modelAndView.setViewName("redirect:/students");
            }
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            this.studentRepository.deleteById(id);
            attr.addFlashAttribute("message", "Estudante #"+id+" removido com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/students");
        } catch (EmptyResultDataAccessException e) {
            attr.addFlashAttribute("message", "Estudante #"+id+" não o no banco de dados!");
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/students");
        }
        return modelAndView;
    }

    // Statements

    @GetMapping("/{id}/statements")
    public ModelAndView index(
        ModelAndView modelAndView,
        @PathVariable Long id
    ) {
        System.out.println("**********************************");
        modelAndView.setViewName("redirect:/students/"+id);
        return modelAndView;
    }

    @GetMapping("/{id}/statements/new")
    public ModelAndView currentStatement(
        ModelAndView modelAndView,
        RequestFormStatement requestFormStatement,
        @PathVariable Long id
    ) {
        modelAndView.setViewName("students/statements/new");
        modelAndView.addObject("studentId", id);
        modelAndView.addObject("requestFormStatement", requestFormStatement);

        return modelAndView;
    }

    @PostMapping("/{id}/statements")
    public ModelAndView createStatement(
        ModelAndView modelAndView,
        @Valid RequestFormStatement requestFormStatement,
        BindingResult bindingResult,
        RedirectAttributes attr,
        @PathVariable Long id
    ) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("studentId", id);
            modelAndView.setViewName("students/statements/new");
        } else {
            Statement statement = requestFormStatement.toStatement();

            Optional<Student> optional = this.studentRepository.findById(id);

            if (optional.isPresent()) {
                statement.setStudent(optional.get());
                statement.setDateGenerate(LocalDate.now());

                this.statementRepository.save(statement);
                attr.addFlashAttribute("message", "Declaração gerada com sucesso!");
                attr.addFlashAttribute("error", "false");
                modelAndView.setViewName("redirect:/students/"+id);
            }

        }

        return modelAndView;
    }

    @GetMapping("/{studentId}/statements/{id}/delete")
    public ModelAndView deleteStatement(@PathVariable Long id, @PathVariable Long studentId, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            this.statementRepository.deleteById(id);
            attr.addFlashAttribute("message", "Declaração #"+id+" apagada com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/students/"+studentId);
        } catch (Exception e) {
            attr.addFlashAttribute("message", "ERROR: "+id);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/students/"+studentId);
        }
        return modelAndView;
    }
}
