package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utfpr.edu.br.tcc.model.Cidade;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Page <Cidade> findCidadeByNomeContainingIgnoreCase  (String nome, Pageable pageable);

    Page <Cidade> findByEstado_NomeContainingIgnoreCase (String estado_nome, Pageable pageable);

    Page<Cidade> findAllByOrderById (Pageable pageable);


}
