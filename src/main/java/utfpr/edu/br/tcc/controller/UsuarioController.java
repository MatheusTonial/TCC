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
import utfpr.edu.br.tcc.model.Permissao;
import utfpr.edu.br.tcc.model.Usuario;
import utfpr.edu.br.tcc.repository.CidadeRepository;
import utfpr.edu.br.tcc.repository.EstadoRepository;
import utfpr.edu.br.tcc.repository.PermissaoRepository;
import utfpr.edu.br.tcc.repository.UsuarioRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    ModelAndView modelAndView = new ModelAndView("usuario/list");

    @GetMapping("/login")
    public String login(ModelMap model) {
        return "/login";
    }

    @GetMapping("/novo")
    public ModelAndView novo(Usuario usuario) {
        modelAndView.addObject(usuario);
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listar(@PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findAllByOrderById(pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return novo(usuario);
        }
        String senha = usuario.getPassword();
        String encode = usuario.getEncodedPassword(senha);

        usuario.setSenha(encode);
        usuario.addPermissao(getPermissao(usuario.getTipo()));

        repository.save(usuario);
        attr.addFlashAttribute("mensagem", "Usuario inserido com sucesso!");
        return new ModelAndView("redirect:/usuarios");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Usuario editar(@PathVariable Long id, RedirectAttributes attributes) {
//        modelAndView.addObject(repository.findOne(id));
//        repository.save(repository.findOne(id));
        attributes.addFlashAttribute("mensagem", "Usuario editado com sucesso!");
        return repository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        repository.delete(id);
        attributes.addFlashAttribute("mensagem", "Usuario excluido com sucesso");
        return "redirect:/usuarios";
    }

    @ModelAttribute("cidades")
    public List<Cidade> listaDeCidade() {
        return cidadeRepository.findAll();
    }

    @ModelAttribute("estados")
    public List<Estado> listaDeEstado() {
        return estadoRepository.findAll();
    }


    private Permissao getPermissao(String tipo) {
        Permissao permissao;
        if(tipo.equalsIgnoreCase("Cliente")){
            permissao = permissaoRepository.findByPermissao("ROLE_USER");
        }else{
            permissao = permissaoRepository.findByPermissao("ROLE_ADMIN");
        }
        if (permissao == null) {
            permissao = new Permissao();
            permissao.setPermissao("ROLE_USER");
            permissaoRepository.save(permissao);
        }
        return permissao;
    }

    @GetMapping("/buscar/bairro")
    public ModelAndView getByBairro(@RequestParam("bairro") String bairro, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByBairroContainingIgnoreCase(bairro, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/cpf")
    public ModelAndView getByCpf(@RequestParam("cpf") String cpf, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByCpf(cpf, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/email")
    public ModelAndView getByEmail(@RequestParam("email") String email, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByEmailContainingIgnoreCase(email, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/nome")
    public ModelAndView getByNome(@RequestParam("nome") String nome, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByNomeContainingIgnoreCase(nome, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/rg")
    public ModelAndView getByRg(@RequestParam("rg") String rg, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByRg(rg, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/rua")
    public ModelAndView getByRua(@RequestParam("rua") String rua, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByRuaContainingIgnoreCase(rua, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/telefone")
    public ModelAndView getByTelefone(@RequestParam("telefone") String telefone, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByTelefoneContaining(telefone, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/tipo")
    public ModelAndView getByTipo(@RequestParam("tipo") String tipo, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByTipoContainingIgnoreCase(tipo, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }

    @GetMapping("/buscar/cidade")
    public ModelAndView getByCidade(@RequestParam("cidade") String cidade, @PageableDefault(size = 5) Pageable pageable) {
        Page<Usuario> page = repository.findByCidade_Nome(cidade, pageable);
        modelAndView.addObject("usuarios", page);
        modelAndView.addObject("usuario", new Usuario());
        return modelAndView;
    }


}
