package utfpr.edu.br.tcc.controller;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utfpr.edu.br.tcc.model.Parcelas;
import utfpr.edu.br.tcc.report.decorator.SeguroReportService;
import utfpr.edu.br.tcc.repository.ParcelasRepository;
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


@Controller
@RequestMapping("/relatorio/parcela")
public class RelatorioParcelaController {
    @Autowired
    private ParcelasRepository parcelasRepository;
    @Autowired
    private SeguroReportService seguroReportService;
    @Autowired
    private EnvioEmail envioEmail;
    @Autowired
    private GerarRelatorio gerarRelatorio;

    ModelAndView modelAndView = new ModelAndView("relatorios/relatorioParcela");

    @GetMapping("/data")
    public void export(@RequestParam("data1")String data1, @RequestParam("data2")String data2,
                       @RequestParam(value = "botao") String botao, RedirectAttributes attributes,
                       HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {
        Date dt1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
        Date dt2  = gerarRelatorio.data2(data1, data2);
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

        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatório Parcelas a Vencer", dt1, dt2,
                "classpath:/reports/ParcelasSeguroReport.jrxml", sub);
        if (botao.equalsIgnoreCase("mostrar")){
            gerarRelatorio.imprimir(response, jasperPrint);
            attributes.addFlashAttribute("mensagem","Relatório gerado com seucesso!");
        }else if (botao.equalsIgnoreCase("baixar")){
            gerarRelatorio.baixar("RelatorioParcelasaVencer.pdf", response, jasperPrint);
            attributes.addFlashAttribute("mensagem","Relatório baixado com sucesso!");
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
        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatório Parcelas a Vencer", dt1, dt2,
                "classpath:/reports/ParcelasSeguroReport.jrxml", sub);
        gerarRelatorio.imprimir(response, jasperPrint);
        envioEmail.enviarArquivo(endereco, assunto, texto, gerarRelatorio.gerarPdf(jasperPrint), "RelatorioParcelasaVencer.pdf");
    }


    @GetMapping
    public ModelAndView listar(@PageableDefault Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        Page<Parcelas> page = parcelasRepository.findAllByDataVencimentoAfterOrderById(localDate, pageable);
        modelAndView.addObject("parcelas", page);
        modelAndView.addObject("parcela", new Parcelas());
        return modelAndView;
    }

}

