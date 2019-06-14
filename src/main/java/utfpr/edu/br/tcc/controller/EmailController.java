package utfpr.edu.br.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utfpr.edu.br.tcc.model.Email;
import utfpr.edu.br.tcc.repository.EmailRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/emails")
public class EmailController {
    
    @Autowired
    private EmailRepository repository;

    ModelAndView modelAndView = new ModelAndView("email/list");

    @GetMapping("/novo")
    public ModelAndView novo(Email email){
        modelAndView.addObject(email);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable){
        Page<Email> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("emails", page);
        modelAndView.addObject("email", new Email());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Email email, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()){
            return novo(email);
        }
        repository.save(email);
        attr.addFlashAttribute("success", "Email inserido com sucesso!");
        return new ModelAndView("redirect:/emails");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Email editar(@PathVariable Long id, RedirectAttributes attributes){
            attributes.addFlashAttribute("success", "Email editado com sucesso!");
            return repository.findOne(id);
        }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attr) {
        repository.delete(id);
        attr.addFlashAttribute("success", "Email excluido com sucesso");
        return "redirect:/emails";
    }
    
}
