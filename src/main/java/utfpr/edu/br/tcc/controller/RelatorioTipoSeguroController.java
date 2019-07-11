package utfpr.edu.br.tcc.controller;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
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
import utfpr.edu.br.tcc.report.decorator.SeguroReportService;
import utfpr.edu.br.tcc.repository.*;
import utfpr.edu.br.tcc.service.EnvioEmail;
import utfpr.edu.br.tcc.service.GerarRelatorio;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/relatorio/tipo")
public class RelatorioTipoSeguroController {

    ModelAndView modelAndView = new ModelAndView("relatorios/relatorioTipoSeguro");
    @Autowired
    private SeguroRepository repository;
    @Autowired
    private SeguroReportService seguroReportService;
    @Autowired
    private Tipo_SeguroRepository tipo_seguroRepository;
    @Autowired
    private GerarRelatorio gerarRelatorio;
    @Autowired
    private EnvioEmail envioEmail;


    @GetMapping("/seguro")
    public void export(@RequestParam("tipoSeguro") String tipoSeguro, @RequestParam(value = "botao") String botao, HttpServletResponse response) throws IOException, JRException, SQLException {
        String ordem = "";
        JasperPrint jasperPrint = seguroReportService.generatePromissoria(1L, "Relatorio de Seguros de "+tipoSeguro, tipoSeguro, "classpath:/reports/TipoSeguroReport.jrxml", ordem, ordem);
        if (botao.equalsIgnoreCase("mostrar")){
            gerarRelatorio.imprimir(response, jasperPrint);
        }else if (botao.equalsIgnoreCase("baixar")){
            gerarRelatorio.baixar("RelatorioSeguro"+tipoSeguro+".pdf", response, jasperPrint);
        }else if(botao.equalsIgnoreCase("enviar")){
            envioEmail.enviarArquivo("mateus_tonial@hotmail.com", "tcc email", "menssagem para teste email", gerarRelatorio.gerarPdf(jasperPrint), "RelatorioSeguro"+tipoSeguro+".pdf");
        }

    }

    @GetMapping("/email")
    public void email(@RequestParam("tipoSeguro") String tipoSeguro, @RequestParam("endereco") String endereco,
                              @RequestParam("assunto") String assunto, @RequestParam("texto") String texto, HttpServletResponse response) throws IOException, JRException, SQLException {
        JasperPrint jasperPrint = seguroReportService.generatePromissoria(1L, "Relatorio de Seguros de "+tipoSeguro, tipoSeguro, "classpath:/reports/TipoSeguroReport.jrxml", "", "");
        gerarRelatorio.imprimir(response, jasperPrint);
        envioEmail.enviarArquivo(endereco, assunto, texto, gerarRelatorio.gerarPdf(jasperPrint), "RelatorioSeguro"+tipoSeguro+".pdf");
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable){
        Page<Seguro> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        return modelAndView;
    }

    @InitBinder
    public void initBinder (WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }



//    @ModelAttribute("usuarios")
//    public List<Usuario> listaDeUsuario(){
//        return usuarioRepository.findAll();
//    }
//
    @ModelAttribute("tipo_seguros")
    public List<Tipo_Seguro> listaDeTipo_Seguro(){
        return tipo_seguroRepository.findAll();
    }
//
//    @ModelAttribute("veiculos")
//    public List<Veiculo> listaDeVeiculo(){
//        return veiculoRepository.findAll();
//    }
//
//    @ModelAttribute("emails")
//    public List<Email> listaDeEmail(){
//        return emailRepository.findAll();
//    }
//
//


}


