package utfpr.edu.br.tcc.controller;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Controller
@RequestMapping("/relatorio/vencidas")
public class RelatorioParcelaVencidasController {
    @Autowired
    private ParcelasRepository parcelasRepository;
    @Autowired
    private SeguroReportService seguroReportService;
    @Autowired
    private EnvioEmail envioEmail;
    @Autowired
    private GerarRelatorio gerarRelatorio;

    ModelAndView modelAndView = new ModelAndView("relatorios/relatorioParcelaVencidas");

    Date hoje = new Date();



    @GetMapping("/hoje")
    public void export(@RequestParam(value = "botao") String botao, RedirectAttributes attributes,
                       HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {

        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatorio Parcelas a Vencer", hoje, hoje,
                "classpath:/reports/ParcelasVencidasReport.jrxml", "");
        if (botao.equalsIgnoreCase("mostrar")){
            gerarRelatorio.imprimir(response, jasperPrint);
        }else if (botao.equalsIgnoreCase("baixar")){
            gerarRelatorio.baixar("RelatorioParcelasaVencidas.pdf", response, jasperPrint);
            attributes.addFlashAttribute("mensagem","Relatorio baixado com sucesso!");
        }
    }

    @GetMapping("/email")
    public void email(@RequestParam("endereco") String endereco, @RequestParam("assunto") String assunto, @RequestParam("texto") String texto, HttpServletResponse response) throws IOException, JRException, SQLException, ParseException {

        JasperPrint jasperPrint = seguroReportService.generateRelatorioData(1L, "Relatorio Parcelas a Vencer", hoje, hoje,
                "classpath:/reports/RelatorioParcelasaVencidas.jrxml", "");
        gerarRelatorio.imprimir(response, jasperPrint);
        envioEmail.enviarArquivo(endereco, assunto, texto, gerarRelatorio.gerarPdf(jasperPrint), "RelatorioParcelasVencidas.pdf");
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

