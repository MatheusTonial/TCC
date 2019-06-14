package utfpr.edu.br.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import utfpr.edu.br.tcc.model.Veiculo;
import utfpr.edu.br.tcc.repository.MarcaRepository;
import utfpr.edu.br.tcc.repository.VeiculoRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/veiculos")

public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private MarcaRepository marcaRepository;

    ModelAndView modelAndView = new ModelAndView("veiculo/list");

    @GetMapping("/novo")
    public ModelAndView novo(Veiculo veiculo){
        modelAndView.addObject(veiculo);
        return modelAndView;
    }

//    @GetMapping("/cadastrar")
//    public String cadastrar(ModelMap model){
//        model.addAttribute("veiculo", new Veiculo());
//        return "/veiculo/form";
//    }

    @GetMapping
    public ModelAndView listar(@PageableDefault(size = 5)Pageable pageable){
        Page<Veiculo> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("veiculos", page);
        modelAndView.addObject("veiculo", new Veiculo());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()){
            return novo(veiculo);
        }
        repository.save(veiculo);
        attr.addFlashAttribute("success", "Veiculo inserido com sucesso!");
        return new ModelAndView("redirect:/veiculos");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Veiculo editar(@PathVariable Long id, RedirectAttributes attributes) {
//        modelAndView.addObject(repository.findOne(id));
//        repository.save(repository.findOne(id));
        attributes.addFlashAttribute("mensagem","Veiculo editado com sucesso!");
        return repository.findOne(id);
    }


    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        repository.delete(id);
        attributes.addFlashAttribute("success", "Veiculo excluido com sucesso");
        return "redirect:/veiculos";
    }

    @GetMapping("/buscar/marca")
    public ModelAndView getByDescricao(@RequestParam("descricao") String descricao, @PageableDefault(size = 5) Pageable pageable){
        Page<Veiculo> page = repository.findByMarca_DescricaoContainingIgnoreCase(descricao, pageable);
        modelAndView.addObject("veiculos", page);
        modelAndView.addObject("veiculo", new Veiculo());
        return modelAndView;
    }

    @GetMapping("/buscar/placa")
    public ModelAndView getByPlaca(@RequestParam("descricao") String descricao, @PageableDefault(size = 5)Pageable pageable){
        Page<Veiculo> page = repository.findByPlacaContainingIgnoreCase(descricao, pageable);
        modelAndView.addObject("veiculos", page);
        modelAndView.addObject("veiculo", new Veiculo());
        return modelAndView;
    }

    @GetMapping("/buscar/modelo")
    public ModelAndView getByModelo(@RequestParam("descricao") String descricao, @PageableDefault(size = 5)Pageable pageable){
        Page<Veiculo> page = repository.findByModeloContainingIgnoreCase(descricao, pageable);
        modelAndView.addObject("veiculos", page);
        modelAndView.addObject("veiculo", new Veiculo());
        return modelAndView;
    }

    @GetMapping("/buscar/ano")
    public ModelAndView getByAno(@RequestParam("descricao") int ano, @PageableDefault(size = 5)Pageable pageable){
        modelAndView.addObject("veiculos", repository.findByAno(ano, pageable));
        modelAndView.addObject("veiculo", new Veiculo());
        return modelAndView;
    }

    @ModelAttribute("marcas")
    public List<Marca> listaDeMarca(){
        return marcaRepository.findAll();
    }


//    @GetMapping(value = "/dash")
//    public String dashboardPageListClasses(Model model) {
//        model.addAttribute("counts", repository.count());
////        String x = model.toString();
//        return "";
//    }

    
}
