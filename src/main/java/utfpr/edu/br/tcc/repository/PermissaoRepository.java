package utfpr.edu.br.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Permissao findByPermissao (String permissao);
}