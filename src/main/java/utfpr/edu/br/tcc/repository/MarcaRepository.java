package utfpr.edu.br.tcc.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Marca;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Page<Marca> findAllByOrderById(Pageable pageable);

}
