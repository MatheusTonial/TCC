package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Page<Estado> findAllByOrderById (Pageable pageable);
}
