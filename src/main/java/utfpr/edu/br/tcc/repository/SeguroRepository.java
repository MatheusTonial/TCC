package utfpr.edu.br.tcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utfpr.edu.br.tcc.model.Parcelas;
import utfpr.edu.br.tcc.model.Seguro;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {


    Page <Seguro> findByUsuario_NomeContainingIgnoreCase (String usuario_nome, Pageable pageable);

    Page <Seguro> findByDataSeg(LocalDate dataSeg, Pageable pageable);

    Page <Seguro> findByUsuario_IdOrderByVencimento(Long usuario_id, Pageable pageable);

    Page<Seguro> findAllByOrderById (Pageable pageable);

    Page<Seguro> findAllBySituacaoNull(Pageable pageable);

    Page<Seguro> findAllBySituacaoOrderById(String situacao, Pageable pageable);

    Page<Seguro> findByTipoSeguro_DescricaoContainingIgnoreCase (String tipoSeguro_descricao, Pageable page);

    Page <Seguro> findByUsuario_EmailContainingIgnoreCase (String usuario_email, Pageable pageable);

    Page <Seguro> findByUsuario_TelefoneContainingIgnoreCase (String usuario_telefone, Pageable pageable);

    Page <Seguro> findByUsuario_CpfContainingIgnoreCase (String usuario_cpf, Pageable pageable);

    Page <Seguro> findByUsuario_RgContainingIgnoreCase (String usuario_rg, Pageable pageable);

    Page <Seguro> findByDataSegBetween (LocalDate dataIni, LocalDate dataFim, Pageable pageable);

    Integer countSegurosByDataSegBetween(LocalDate dataIni, LocalDate dataFim);

    Integer countSegurosByTipoSeguro_Descricao(String tipoSeguro_descricao);

//    List<Seguro> findAll ();



//    public default List<Object[]> findSegurosPorMes(){
////        EntityManager em = null;
//        Query q = EntityManager.createNativeQuery("SELECT count(), extract(month from data_seg) as mes, extract(year from data_seg) as ano" +
//                " from seguros group by extract(month from data_seg), extract(year from data_seg) order by extract(year from data_seg)");
//        return ((javax.persistence.Query) q).getResultList();
//    }

//    Relatorios
//@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
//User findByEmailAddress(String emailAddress);

//    @Query(value = "SELECT seguro from parcelas where parcelas.seguro.id = ?1")
//    Page<Seguro> findSeguroByNParcelas (String Seguro_id, Pageable pageable);

}
