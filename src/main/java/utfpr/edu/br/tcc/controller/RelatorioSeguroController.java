package utfpr.edu.br.tcc.controller;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import utfpr.edu.br.tcc.model.*;
import utfpr.edu.br.tcc.report.decorator.SeguroReportService;
import utfpr.edu.br.tcc.repository.*;
import utfpr.edu.br.tcc.service.EnvioEmail;
import utfpr.edu.br.tcc.service.GerarRelatorio;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/relatorio/seguro")
public class RelatorioSeguroController {

    ModelAndView modelAndView = new ModelAndView("relatorios/relatorioSeguro");
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
    private SeguroReportService seguroReportService;
    @Autowired
    private GerarRelatorio gerarRelatorio;

    @Autowired
    private EnvioEmail envioEmail;


    @GetMapping("/vencimento")
    public void export(@RequestParam("data1")String data1, @RequestParam("data2")String data2, @RequestParam(value = "botao") String botao,
                       HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {
        Date dt1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
        Date dt2 = gerarRelatorio.data2(data1, data2);
        String sub;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(dt2);
        if(dt1.compareTo(dt2) > 0){
            Date dt3 = dt1;
            dt1 = dt2;
            dt2 = dt3;
            sub = strDate + " até " + data1;
        }else{
            sub = data1 + " até " + strDate;
        }

        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatório Seguros a Vencer", dt1, dt2,
                "classpath:/reports/VencimentoSeguroEntreReport.jrxml", sub);
        if (botao.equalsIgnoreCase("mostrar")){
            gerarRelatorio.imprimir(response, jasperPrint);
        }else if (botao.equalsIgnoreCase("baixar")){
            gerarRelatorio.baixar("RelatorioSegurosaVencer.pdf", response, jasperPrint);
        }else if(botao.equalsIgnoreCase("enviar")){
            envioEmail.enviarArquivo("mateus_tonial@hotmail.com", "tcc email", "menssagem para teste email", gerarRelatorio.gerarPdf(jasperPrint), "RelatorioSegurosaVencer.pdf");
        }
    }

    @GetMapping("/email")
    public void email(@RequestParam("data11")String data1, @RequestParam("data2")String data2, @RequestParam("endereco") String endereco,
                      @RequestParam("assunto") String assunto, @RequestParam("texto") String texto, HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {
        Date dt1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
        Date dt2 = gerarRelatorio.data2(data1, data2);
        String sub;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(dt2);
        if(dt1.compareTo(dt2) > 0){
            Date dt3 = dt1;
            dt1 = dt2;
            dt2 = dt3;
            sub = strDate + " até " + data1;
        }else{
            sub = data1 + " até " + strDate;
        }
        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatório Seguros a Vencer", dt1, dt2,
                "classpath:/reports/VencimentoSeguroEntreReport.jrxml", sub);
        gerarRelatorio.imprimir(response, jasperPrint);
        envioEmail.enviarArquivo(endereco, assunto, texto, gerarRelatorio.gerarPdf(jasperPrint), "RelatorioSegurosaVencer.pdf");
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

    @ModelAttribute("usuarios")
    public List<Usuario> listaDeUsuario(){
        return usuarioRepository.findAll();
    }

    @ModelAttribute("tipo_seguros")
    public List<Tipo_Seguro> listaDeTipo_Seguro(){
        return tipo_seguroRepository.findAll();
    }

    @ModelAttribute("veiculos")
    public List<Veiculo> listaDeVeiculo(){
        return veiculoRepository.findAll();
    }

    @ModelAttribute("emails")
    public List<Email> listaDeEmail(){
        return emailRepository.findAll();
    }




}

