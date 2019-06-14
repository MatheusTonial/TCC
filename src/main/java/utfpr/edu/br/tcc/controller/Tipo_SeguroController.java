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

import utfpr.edu.br.tcc.model.Tipo_Seguro;
import utfpr.edu.br.tcc.repository.Tipo_SeguroRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/tipo_Seguros")
public class Tipo_SeguroController {
    
    @Autowired
    Tipo_SeguroRepository repository;

    ModelAndView modelAndView = new ModelAndView("tipo_seguro/list");

    @GetMapping("/novo")
    public ModelAndView novo(Tipo_Seguro tipo_seguro){
        modelAndView.addObject(tipo_seguro);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable){
        Page<Tipo_Seguro> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("tipo_Seguros", page);
        modelAndView.addObject("tipo_Seguro", new Tipo_Seguro());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Tipo_Seguro tipo_Seguro, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()){
            return novo(tipo_Seguro);
        }
        repository.save(tipo_Seguro);
        attr.addFlashAttribute("success", "Tipo de Seguro inserido com sucesso!");
        return new ModelAndView("redirect:/tipo_Seguros");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Tipo_Seguro editar(@PathVariable Long id, RedirectAttributes attr) {
        attr.addFlashAttribute("success", "Tipo de Seguro editado com sucesso!");
        return repository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attr) {
        repository.delete(id);
        attr.addFlashAttribute("success", "Tipo de Seguro excluido com sucesso");
        return "redirect:/tipo_Seguros";
    }

}
