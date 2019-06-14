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
import utfpr.edu.br.tcc.model.Parcelas;
import utfpr.edu.br.tcc.model.Seguro;
import utfpr.edu.br.tcc.repository.ParcelasRepository;
import utfpr.edu.br.tcc.repository.SeguroRepository;


import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/parcelas")
public class ParcelasController {

    @Autowired
    private ParcelasRepository repository;
    
    @Autowired
    private SeguroRepository seguroRepository;

    ModelAndView modelAndView = new ModelAndView("parcela/list");

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable){
        Page<Parcelas> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("parcelas", page);
        modelAndView.addObject("parcela", new Parcelas());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Parcelas parcelas, BindingResult result, RedirectAttributes attr) {
        repository.save(parcelas);
        return new ModelAndView("redirect:/parcelas");
    }

    @GetMapping("/{id}")
    public ModelAndView editar(@PathVariable Long id, RedirectAttributes attr) {
        Parcelas parcelas = repository.findOne(id);
        Seguro seguro;
        seguro = parcelas.getSeguro();
        if (parcelas.getStatus().equalsIgnoreCase("aberto")){
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String text = date.format(formatters);
            LocalDate parsedDate = LocalDate.parse(text, formatters);
            parcelas.setDataPago(parsedDate);
            parcelas.setStatus("pago");
            repository.save(parcelas);
            seguro.setParcelasPagas(seguro.getParcelasPagas()+1);
            if (seguro.getNParcelas()==seguro.getParcelasPagas()){
                seguro.setSituacao("terminado");
            }
            seguroRepository.save(seguro);
            attr.addFlashAttribute("success", "Parcela editada com sucesso!");
        }else{
            parcelas.setDataPago(null);
            parcelas.setStatus("aberto");
            repository.save(parcelas);
            seguro.setParcelasPagas(seguro.getParcelasPagas()-1);
        }
//        attr.addFlashAttribute("error", "Parcela ja foi paga!");

        return new ModelAndView("redirect:/parcelas/seguro/"+parcelas.getSeguro().getId());
//        return getBySeguro(parcelas.getSeguro().getId(), null);
    }

    @GetMapping("/seguro/{id}")
    public ModelAndView getBySeguro(@PathVariable Long id, @PageableDefault Pageable pageable){
        Page<Parcelas> page = repository.findBySeguro_IdOrderById(id, pageable);
        modelAndView.addObject("parcelas", page);
        modelAndView.addObject("parcela", new Parcelas());
        return modelAndView;
    }

    @ModelAttribute("seguros")
    public List<Seguro> listaDeSeguro(){
        return seguroRepository.findAll();
    }


}
