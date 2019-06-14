package utfpr.edu.br.tcc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import utfpr.edu.br.tcc.TccApplication;
import utfpr.edu.br.tcc.model.Permissao;
import utfpr.edu.br.tcc.model.Seguro;
import utfpr.edu.br.tcc.model.Tipo_Seguro;
import utfpr.edu.br.tcc.model.Usuario;
import utfpr.edu.br.tcc.repository.ParcelasRepository;
import utfpr.edu.br.tcc.repository.SeguroRepository;
import utfpr.edu.br.tcc.repository.Tipo_SeguroRepository;
import utfpr.edu.br.tcc.repository.UsuarioRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {

    //    @GetMapping(value = "/")
//    public String index(){
//        return "index";
//    }
    @Autowired
    private SeguroRepository repository;
    @Autowired
    private Tipo_SeguroRepository tipoSeguroRepository;
    @Autowired
    private ParcelasRepository parcelasRepository;
    @Autowired
    private SeguroController seguroController;

    ModelAndView modelAndView = new ModelAndView("index");

    @GetMapping
    public ModelAndView index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int teste = authentication.getAuthorities().toString().indexOf("ADMIN");
        System.out.println("teste - " +teste);

        if(teste != -1){
            barGraph();
            pieChart();
            tabelas();
        }else if(teste == -1){
            Usuario usuario = (Usuario) authentication.getPrincipal();
            System.out.println(usuario.getId());
            listar(null, usuario.getId());
        }


        return modelAndView;
    }

    public ModelAndView listar(@PageableDefault Pageable pageable, Long id) {
        Page<Seguro> page = repository.findByUsuario_IdOrderByVencimento(id, pageable);
        modelAndView.addObject("seguros", page);
        modelAndView.addObject("seguroCli", new Seguro());
        modelAndView.addObject(seguroController.carregar(pageable));
        return modelAndView;
    }


    public ModelAndView tabelas() {
        //quantidade seguros e valor total
        List<Double> tbSeguro = new ArrayList<>(2);
        Double n = 0.0;
        Double v = 0.0;
        for (int i = 1; i <= repository.findAll().size(); i++) {
            n++;
            long x = i;
            v = v + repository.findOne(x).getValor();
        }
        tbSeguro.add(0, n);
        tbSeguro.add(1, v);
        modelAndView.addObject("tbSeguro", tbSeguro);
        //quantidade parcelas pagas e a pagar e seus valores
        List<Double> tbParcelaP = new ArrayList<>(2);
        List<Double> tbParcelaV = new ArrayList<>(2);
        Double pn = 0.0, pv = 0.0, vn = 0.0, vv = 0.0;
        for (int i = 1; i <= parcelasRepository.findAll().size(); i++) {
            long x = i;
            if (parcelasRepository.findOne(x).getStatus().equalsIgnoreCase("aberto")) {
                vn++;
                vv = vv + ((double) parcelasRepository.findOne(x).getSeguro().getValor() / parcelasRepository.findOne(x).getSeguro().getNParcelas());
            } else if (parcelasRepository.findOne(x).getStatus().equalsIgnoreCase("pago")) {
                pn++;
                pv = pv + ((double) parcelasRepository.findOne(x).getSeguro().getValor() / parcelasRepository.findOne(x).getSeguro().getNParcelas());
            }
        }
        tbParcelaV.add(0, vn);
        tbParcelaV.add(1, vv);
        tbParcelaP.add(0, pn);
        tbParcelaP.add(1, pv);
        modelAndView.addObject("tbParcelaP", tbParcelaP);
        modelAndView.addObject("tbParcelaV", tbParcelaV);

        return modelAndView;
    }

    public ModelAndView barGraph() {
        List<Integer> total = new ArrayList<>(12);
        List<Integer> nMes = new ArrayList<>(12);

        LocalDate data = LocalDate.now();
        LocalDate datafim;
        LocalDate dataIni;
        int l = 0;
        for (int i = 12; i >= 1; i--) { //criando lista com a quantidade de seguros por mes e outra com o mes correspondente
            datafim = data.minusDays(data.getDayOfMonth()).minusMonths(l);//dia 30;
            dataIni = datafim.minusDays(datafim.getDayOfMonth() - 1);
            total.add(l, repository.countSegurosByDataSegBetween(dataIni, datafim));
            Integer obj = new Integer(dataIni.getMonthValue());
            nMes.add(l, obj);
            l++;
        }
        //gerando o conteudo do grafico
        Map<String, Integer> graficoMes = new LinkedHashMap<>();
        for (int i = 0; i < 12; i++) {
            graficoMes.put(getNomeMes(nMes.get(i)), total.get(i));
        }
        modelAndView.addObject("graficoMes", graficoMes);
        return modelAndView;
    }

    public ModelAndView pieChart() {
        List<String> tipos = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        List<Integer> percentual = new ArrayList<>();
        for (int i = 1; i <= tipoSeguroRepository.count(); i++) {
            String descricao = tipoSeguroRepository.findOne((long) i).getDescricao();
            tipos.add(i - 1, descricao);
            total.add(i - 1, repository.countSegurosByTipoSeguro_Descricao(descricao));
            int valor = (int) (total.get(i - 1) * 100.0f / repository.count());
            percentual.add(i - 1, valor);
        }

        Map<String, Integer> graficoPizza = new LinkedHashMap<>();
        Map<String, String> graficoPizzaNome = new LinkedHashMap<>();
        Map<String, Integer> graficoPizzaQtt = new LinkedHashMap<>();
        for (int i = 0; i < total.size(); i++) {
            graficoPizza.put(tipos.get(i), percentual.get(i));
            graficoPizzaNome.put(tipos.get(i), tipos.get(i));
            graficoPizzaQtt.put(tipos.get(i), total.get(i));
        }

        modelAndView.addObject("graficoPizza", graficoPizza);
        modelAndView.addObject("graficoPizzaNome", graficoPizzaNome);
        modelAndView.addObject("graficoPizzaQtt", graficoPizzaQtt);
        return modelAndView;
    }


//qttd*100.0f/total.0f

    public String getNomeMes(int i) {
        String mes = "";
        if (i == 1) {
            mes = "Janeiro";
        } else if (i == 2) {
            mes = "Fevereiro";
        } else if (i == 3) {
            mes = "Março";
        } else if (i == 4) {
            mes = "Abril";
        } else if (i == 5) {
            mes = "Maio";
        } else if (i == 6) {
            mes = "Junho";
        } else if (i == 7) {
            mes = "Julho";
        } else if (i == 8) {
            mes = "Agosto";
        } else if (i == 9) {
            mes = "Setembro";
        } else if (i == 10) {
            mes = "Outubro";
        } else if (i == 11) {
            mes = "Novembro";
        } else if (i == 12) {
            mes = "Dezembro";
        }
        return mes;
    }


}
