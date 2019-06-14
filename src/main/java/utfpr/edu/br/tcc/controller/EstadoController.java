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
import utfpr.edu.br.tcc.model.Estado;
import utfpr.edu.br.tcc.repository.EstadoRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository repository;

    ModelAndView modelAndView = new ModelAndView("estado/list");

    @GetMapping("/novo")
    public ModelAndView novo(Estado estado){
        modelAndView.addObject(estado);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault(size = 10)Pageable pageable){
        Page<Estado> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("estados", page);
        modelAndView.addObject("estado", new Estado());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Estado estado, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()){
            return novo(estado);
        }
        repository.save(estado);
        attr.addFlashAttribute("success", "Estado inserido com sucesso!");
//        return new ModelAndView("redirect:/estados");
        modelAndView.addObject("estados");
        modelAndView.addObject("estado", new Estado());
        return modelAndView;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Estado editar(@PathVariable Long id, RedirectAttributes attr) {
        attr.addFlashAttribute("success", "Estado editado com sucesso!");
        return repository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attr) {
        repository.delete(id);
        attr.addFlashAttribute("success", "Estado excluido com sucesso");
        return "redirect:/estados";
    }
    
}
