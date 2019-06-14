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
import utfpr.edu.br.tcc.model.Cidade;
import utfpr.edu.br.tcc.model.Estado;
import utfpr.edu.br.tcc.repository.CidadeRepository;
import utfpr.edu.br.tcc.repository.EstadoRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;

    ModelAndView modelAndView = new ModelAndView("cidade/list");

    @GetMapping("/novo")
    public ModelAndView novo(Cidade cidade){
        modelAndView.addObject(cidade);
        return modelAndView;

    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable){
        Page<Cidade> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("cidades", page);
        modelAndView.addObject("cidade", new Cidade());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()){
            return novo(cidade);

        }
        repository.save(cidade);
        attr.addFlashAttribute("success", "Cidade inserida com sucesso!");
        return new ModelAndView("redirect:/cidades");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Cidade editar(@PathVariable Long id, RedirectAttributes attr) {
        attr.addFlashAttribute("success", "Cidade editada com sucesso!");
        return repository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attr) {
        repository.delete(id);
        attr.addAttribute("success", "Cidade excluido com sucesso");
        return "redirect:/cidades";
    }

    @GetMapping("/buscar/nome")
    public ModelAndView getByNome(@RequestParam("nome") String nome, @PageableDefault Pageable pageable){
        Page<Cidade> page =  repository.findCidadeByNomeContainingIgnoreCase(nome, pageable);
        modelAndView.addObject("cidades", page);
        modelAndView.addObject("cidade", new Cidade());
        return modelAndView;
    }
    @GetMapping("/buscar/estado")
    public ModelAndView getByEstado(@RequestParam("nome") String nome, @PageableDefault Pageable pageable){
        Page<Cidade> page =  repository.findByEstado_NomeContainingIgnoreCase(nome, pageable);
        modelAndView.addObject("cidades", page);
        modelAndView.addObject("cidade", new Cidade());
        return modelAndView;
    }

    @ModelAttribute("estados")
    public List<Estado> listaDeEstado(){
        return estadoRepository.findAll();
    }

}
