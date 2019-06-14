package utfpr.edu.br.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utfpr.edu.br.tcc.model.Marca;
import utfpr.edu.br.tcc.repository.MarcaRepository;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    MarcaRepository marcaRepository;

    ModelAndView modelAndView = new ModelAndView("marca/list");

    @GetMapping("/novo")
    public ModelAndView novo(Marca marca) {
//        ModelAndView modelAndView = new ModelAndView("marca/list");
        modelAndView.addObject(marca);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault(size = 5) Pageable pageable) {
        Page<Marca> page = marcaRepository.findAllByOrderById(pageable);

        modelAndView.addObject("marcas", page);
        modelAndView.addObject("marca", new Marca());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Marca marca, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return novo(marca);
        }
        marcaRepository.save(marca);
        attributes.addFlashAttribute("mensagem","Marca salva com sucesso!");
        return new ModelAndView("redirect:/marcas");
//        return modelAndView;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Marca editar(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("mensagem","Marca salva com sucesso!");
        return marcaRepository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id, RedirectAttributes attributes) {
        marcaRepository.delete(id);
        attributes.addFlashAttribute("mensagem", "Marca removida com sucesso!");
        return "redirect:/marcas";
    }
//    @GetMapping
//    public String listar(ModelMap model){
//        model.addAttribute("marca", marcaRepository.findAll());
//        return "/marca/list";
//    }



//    @PostMapping("/salvar")
//    public String salvar(@Valid Marca marca, BindingResult result, RedirectAttributes attr) {
//        if (result.hasErrors()){
//            return "/marca/form";
//        }
//        marcaRepository.save(marca);
//        attr.addFlashAttribute("success", "Marca salva com sucesso!");
//        return "redirect:/marca/list";
//    }

//    @GetMapping("/editar/{id}")
//    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
//        model.addAttribute("marca", marcaRepository.findOne(id));
//        return "/marca/form";
//
//    }

//    @PostMapping("/editar")
//    public ModelAndView editar(@Valid Marca marca, BindingResult result, RedirectAttributes attr) {
//        if (result.hasErrors()) {
//            return novo(marca);
//        }
////        marca.setId(id);
//        marcaRepository.save(marca);
////        service.editar(marca);
//        attr.addFlashAttribute("success", "Marca editada com sucesso!");
//        return new ModelAndView("redirect:/marcas");
//    }
//
//    @GetMapping("/excluir/{id}")
//    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
//        marcaRepository.delete(id);
//        attr.addFlashAttribute("success", "Marca excluida com sucesso");
//        return "redirect:/marcas/listar";
//    }


}
