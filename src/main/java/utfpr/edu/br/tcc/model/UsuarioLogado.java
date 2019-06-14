package utfpr.edu.br.tcc.model;

import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@SessionScope
public class UsuarioLogado implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeUsuario(){
        return this.usuario.getNome();
    }

    public boolean inLogado(){
        return usuario != null;
    }
}
