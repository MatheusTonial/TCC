package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Tipo_Seguro;

public interface Tipo_SeguroRepository extends JpaRepository<Tipo_Seguro, Long> {

    Page<Tipo_Seguro> findAllByOrderById (Pageable pageable);
}
