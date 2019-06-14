package utfpr.edu.br.tcc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;
    private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(10);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message = "Nome deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    @Getter
    @Setter
    private String nome;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message = "E-mail deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 255, message = "Senha deve estar entre {min} e {max} caracters")
    @Column(length = 255, nullable = false)
    private String senha;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message = "Rua deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String rua;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 1, max = 7, message = "Numero deve ter no maximo {max} caracters")
    @Column(length = 7, nullable = false)
    private String numero;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message = "Bairro deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String bairro;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 10, max = 14, message = "Telefone deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String telefone;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 10, max = 14, message = "Cpf deve ter {min} caracters")
    @Column(length = 45, nullable = false)
    private String cpf;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 9, max = 12, message = "RG deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String rg;

    @NotNull
    @Column(length = 45, nullable = false)
    private String tipo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cidade_id", referencedColumnName = "id", nullable = false)
    private Cidade cidade;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Permissao> permissoes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auto = new ArrayList<>();
        auto.addAll(getPermissoes());
        return auto;
    }

    public void addPermissao(Permissao permissao) {
        if (permissoes == null) {
            permissoes = new HashSet<>();
        }
        permissoes.add(permissao);
    }

    public String getEncodedPassword(String senha) {
        if (!senha.isEmpty()) {
            return bCrypt.encode(senha);
        }
        return senha;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
