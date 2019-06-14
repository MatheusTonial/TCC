package utfpr.edu.br.tcc.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import utfpr.edu.br.tcc.model.Veiculo;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Page<Veiculo> findByMarca_DescricaoContainingIgnoreCase (String marca_descricao, Pageable pageable);

    Page<Veiculo> findByPlacaContainingIgnoreCase (String placa, Pageable pageable);

    Page<Veiculo> findByModeloContainingIgnoreCase (String modelo, Pageable pageable);

    Page<Veiculo> findByAno (int ano, Pageable pageable);

    Page<Veiculo> findAllByOrderById (Pageable pageable);

//    Veiculo countAllByOrOrderById ();

    /**
    @Override
    public List<Veiculo> findByMarca(String nome) {
        return createQuery("Select v from Veiculo v where UPPER(v.marca.descricao) like UPPER(?1)", "%"+nome+"%");
    }

    @Override
    public List<Veiculo> findByPlaca(String nome) {
        return createQuery("Select v from Veiculo v where UPPER(v.placa) like UPPER(?1)", "%"+nome+"%");
    }

    @Override
    public List<Veiculo> findByModelo(String nome) {
        return createQuery("Select v from Veiculo v where UPPER(v.modelo) like UPPER(?1)", "%"+nome+"%");
    }

    @Override
    public List<Veiculo> findByAno(int ano) {
        return createQuery("Select v from Veiculo v where v.ano like ?1", (int)ano);
    }
     */
}
