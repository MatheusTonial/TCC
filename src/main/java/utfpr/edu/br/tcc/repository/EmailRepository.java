package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Email;

public interface EmailRepository extends JpaRepository <Email, Long> {

    Page<Email> findAllByOrderById (Pageable pageable);
}
