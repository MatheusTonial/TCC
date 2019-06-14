package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import utfpr.edu.br.tcc.model.Parcelas;

import java.time.LocalDate;
import java.util.List;

public interface ParcelasRepository extends JpaRepository<Parcelas, Long> {

    Page<Parcelas> findAllByOrderById (Pageable pageable);

    Page<Parcelas> findBySeguro_IdOrderById(Long seguro_id, Pageable pageable);

    Page<Parcelas> findByStatus(String status, Pageable pageable);

    Page<Parcelas> findAllByDataVencimentoAfterOrderById (LocalDate dataVencimento, Pageable pageable);

    List<Parcelas> findAllByOrderById();
}
