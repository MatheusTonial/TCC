package utfpr.edu.br.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utfpr.edu.br.tcc.model.*;
import utfpr.edu.br.tcc.repository.*;
import utfpr.edu.br.tcc.service.EnvioEmail;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seguros")
public class SeguroController {

    @Autowired
    private SeguroRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private Tipo_SeguroRepository tipo_seguroRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ParcelasRepository parcelasRepository;
    @Autowired
    private EnvioEmail envioEmail;

    ModelAndView modelAndView = new ModelAndView("seguro/list");

    @GetMapping("/novo")
    public ModelAndView novo(Seguro seguro) {
        modelAndView.addObject(seguro);
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(Seguro seguro, BindingResult result, RedirectAttributes attr) {
        seguro.setEmail(emailRepository.findOne(1L));
        seguro.setParcelasPagas(0);
        seguro.setVencimento(seguro.getDataSeg().plusYears(1L));
        repository.save(seguro);
        int nPar = 1;
        do {
            Parcelas parcelas = new Parcelas();
            parcelas.setDataVencimento(seguro.getDataSeg().plusMonths(nPar));
            parcelas.setDataPago(null);
            parcelas.setStatus("aberto");
            parcelas.setSeguro(seguro);
            String tamanho = Integer.toString(nPar)+"/"+Integer.toString(seguro.getNParcelas());
            parcelas.setTamanho(tamanho);
            parcelasRepository.save(parcelas);
            nPar++;
        } while (nPar <= seguro.getNParcelas());

        attr.addFlashAttribute("mensagem", "Seguro inserido com sucesso!");
        return new ModelAndView("redirect:/seguros");
    }

    public ModelAndView carregar(@PageableDefault Pageable pageable){
        modelAndView.addObject("parcelas", parcelasRepository.findAll(pageable));
        modelAndView.addObject("emails", emailRepository.findAllByOrderById(pageable));
        modelAndView.addObject("email", new Email());
        List<Parcelas> pc = parcelasRepository.findAllByOrderById();

        StringBuilder list1 = new StringBuilder();
        for(int i = 1; i < pc.size(); i++){
            list1.append(pc.get(i-1).getDataVencimento().toString()).append(";")
                    .append(pc.get(i-1).getStatus()).append(";")
                    .append(pc.get(i-1).getSeguro().getId()).append(",");
        }
        modelAndView.addObject("listaParcelaSeg", list1);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable) {
        Page<Seguro> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        carregar(pageable);
        return modelAndView;
    }

    @GetMapping("/pagos")
    public ModelAndView listarPagos(@PageableDefault Pageable pageable){
        modelAndView.addObject("seguros", repository.findAllBySituacaoOrderById("terminado", pageable));
        modelAndView.addObject("seguro", new Seguro());
        carregar(pageable);
        return modelAndView;
    }

    @GetMapping("/abertos")
    public ModelAndView listarAbertos(@PageableDefault Pageable pageable){
        modelAndView.addObject("seguros", repository.findAllBySituacaoNull(pageable));
        modelAndView.addObject("seguro", new Seguro());
        carregar(pageable);
        return modelAndView;
    }

//    @GetMapping("/parcela")
//    public ModelAndView parcelas(@PageableDefault Pageable pageable, Long id){
//        modelAndView.addObject("parcelas", parcelasRepository.findBySeguro_IdOrderById(id, pageable));
//        modelAndView.addObject("parcela", new Parcelas());
//
//        return modelAndView;
//    }

    @GetMapping("/confirmar/{id}/{idData}")
    @ResponseBody
    public List<String> confirmar(@PathVariable("id") Long id, @PathVariable("idData") String dataP, RedirectAttributes attributes){
        Seguro seguro = repository.findOne(id);
        Email email;
        String data = "";
        if(dataP.equalsIgnoreCase("1")){
            email = emailRepository.findOne(1L);
            LocalDate date = seguro.getVencimento();
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = date.format(formatters);
        }else{
            email = emailRepository.findOne(2L);
            data = dataP.substring(0, 2)+"/"+dataP.substring(2, 4)+"/"+dataP.substring(4);
        }
        String titulo = email.getTitulo();
        String texto = email.getTexto();


        titulo = titulo.replaceAll("CLIENTE", seguro.getUsuario().getNome());
        titulo = titulo.replaceAll("TIPO", seguro.getTipoSeguro().getDescricao());
        titulo = titulo.replaceAll("DATA", data);

        texto = texto.replaceAll("CLIENTE", seguro.getUsuario().getNome());
        texto = texto.replaceAll("TIPO", seguro.getTipoSeguro().getDescricao());
        texto = texto.replaceAll("DATA", data);

        List<String> confirmar = new ArrayList<>(4);
        confirmar.add(0, seguro.getUsuario().getEmail());
        confirmar.add(1, titulo);
        confirmar.add(2, texto);
        confirmar.add(3, seguro.getId().toString());
        return confirmar;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Seguro editar(@PathVariable Long id, RedirectAttributes attr) {
        attr.addFlashAttribute("mensagem", "Seguro editado com sucesso!");
        return repository.findOne(id);
    }

    @GetMapping("/enviar")
    public ModelAndView enviar(@RequestParam Long id, RedirectAttributes attributes){
        Seguro seguro = repository.findOne(id);
        Email email = emailRepository.findOne(1L);
        String titulo = email.getTitulo();
        String texto = email.getTexto();
        LocalDate date = seguro.getDataSeg();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = date.format(formatters);

        titulo = titulo.replaceAll("CLIENTE", seguro.getUsuario().getNome());
        titulo = titulo.replaceAll("TIPO", seguro.getTipoSeguro().getDescricao());
        titulo = titulo.replaceAll("DATA", data);

        texto = texto.replaceAll("CLIENTE", seguro.getUsuario().getNome());
        texto = texto.replaceAll("TIPO", seguro.getTipoSeguro().getDescricao());
        texto = texto.replaceAll("DATA", data);
        envioEmail.enviarMensagem(seguro.getUsuario().getEmail(), titulo, texto);
        attributes.addFlashAttribute("mensagem", "Aviso enviado com sucesso!");
        return new ModelAndView("redirect:/seguros");
    }

    @GetMapping("/email/{id}")
    @ResponseBody
    public Email email(@PathVariable Long id, RedirectAttributes attr) {
        attr.addFlashAttribute("mensagem", "Aviso editado com sucesso!");
        return emailRepository.findOne(id);
    }

    @PostMapping("/email/salvar")
    public ModelAndView salvarEmail(@Valid Email email, BindingResult result, RedirectAttributes attr) {
        emailRepository.save(email);
        attr.addFlashAttribute("mensagem", "Email inserido com sucesso!");
        return new ModelAndView("redirect:/seguros");
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attr) {
        repository.delete(id);
        attr.addFlashAttribute("mensagem", "Seguro excluido com sucesso");
        return "redirect:/seguros";
    }

    @GetMapping("/buscar/usuario")
    public ModelAndView getByUsuario(@RequestParam("nome") String nome, @PageableDefault Pageable pageable) {
        Page<Seguro> page = repository.findByUsuario_NomeContainingIgnoreCase(nome, pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        return modelAndView;
    }

    @GetMapping("/buscar/tipo")
    public ModelAndView getByTipo(@RequestParam("nome") String nome, @PageableDefault Pageable pageable) {
        Page<Seguro> page = repository.findByTipoSeguro_DescricaoContainingIgnoreCase(nome, pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        return modelAndView;
    }

    @GetMapping("/buscar/data")
    public ModelAndView getByData(@RequestParam("dataSeg") String dataSeg, @PageableDefault Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dataSeg, formatter);
        Page<Seguro> page = repository.findByDataSeg(localDate, pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        return modelAndView;
    }

    @ModelAttribute("usuarios")
    public List<Usuario> listaDeUsuario() {
        return usuarioRepository.findAll();
    }

    @ModelAttribute("tipo_seguros")
    public List<Tipo_Seguro> listaDeTipo_Seguro() {
        return tipo_seguroRepository.findAll();
    }

    @ModelAttribute("veiculos")
    public List<Veiculo> listaDeVeiculo() {
        return veiculoRepository.findAll();
    }

    @ModelAttribute("emails")
    public List<Email> listaDeEmail() {
        return emailRepository.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }


}
