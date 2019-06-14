package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmailAndSenha (String email, String senha);

    Page<Usuario> findByBairroContainingIgnoreCase (String bairro, Pageable pageable);

    Page<Usuario> findByCpf (String cpf, Pageable pageable);

    Page<Usuario> findByEmailContainingIgnoreCase (String email, Pageable pageable);

    Page<Usuario> findByNomeContainingIgnoreCase (String nome, Pageable pageable);

    Page<Usuario> findByRg (String rg, Pageable pageable);

    Page<Usuario> findByRuaContainingIgnoreCase (String rua, Pageable pageable);

    Page<Usuario> findByTelefoneContaining (String telefone, Pageable pageable);

    Page<Usuario> findByTipoContainingIgnoreCase (String tipo, Pageable pageable);

    Page<Usuario> findByCidade_Nome (String cidade_nome, Pageable pageable);

    Usuario findByEmail(String email);

    Page<Usuario> findAllByOrderById (Pageable pageable);


}
