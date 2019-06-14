package utfpr.edu.br.tcc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import utfpr.edu.br.tcc.model.Login;
import utfpr.edu.br.tcc.model.Usuario;
import utfpr.edu.br.tcc.repository.UsuarioRepository;

import java.security.Principal;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model){
        if (error != null){
            model.addAttribute("error", "Usuario ou senha invalido");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }




}