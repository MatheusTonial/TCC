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
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/relatorio/cliente")
public class RelatorioClienteController {

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

    ModelAndView modelAndView = new ModelAndView("relatorios/relatorioCliente");


    @GetMapping("/seguro")
    public void export(@RequestParam("cliente") String cliente, @RequestParam(value = "botao") String botao, HttpServletResponse response, RedirectAttributes attributes) throws IOException, JRException, SQLException {
        String ordem = "";
        JasperPrint jasperPrint = seguroReportService.generatePromissoria(1L, "Relatorio de Seguros por Cliente", cliente, "classpath:/reports/ClienteSeguroReport.jrxml", ordem, ordem);
        if (botao.equalsIgnoreCase("mostrar")) {
            gerarRelatorio.imprimir(response, jasperPrint);
        } else if (botao.equalsIgnoreCase("baixar")) {
            gerarRelatorio.baixar("RelatorioCliente.pdf", response, jasperPrint);
        }

        attributes.addFlashAttribute("mensagem","Relatorio gerado com sucesso!");
    }

    @GetMapping("/email")
    public void email(@RequestParam("cliente") String cliente, @RequestParam("endereco") String endereco,
                      @RequestParam("assunto") String assunto, @RequestParam("texto") String texto, HttpServletResponse response) throws IOException, JRException, SQLException {
        JasperPrint jasperPrint = seguroReportService.generatePromissoria(1L, "Relatorio de Seguros por Cliente", cliente, "classpath:/reports/ClienteSeguroReport.jrxml", "", "");
        gerarRelatorio.imprimir(response, jasperPrint);
        envioEmail.enviarArquivo(endereco, assunto, texto, gerarRelatorio.gerarPdf(jasperPrint), "RelatorioCliente.pdf");
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable) {
        Page<Seguro> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguro", new Seguro());
        return modelAndView;
    }


}



