package utfpr.edu.br.tcc.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import utfpr.edu.br.tcc.converter.SeguroConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.time.LocalDate;

@Entity
@Table(name = "seguros")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @NotNull
    @Column(nullable = false)
    private Integer nParcelas;

    @NotNull
    @Column(nullable = false)
    private Integer parcelasPagas;

    @Column(nullable = true)
    private String situacao;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name= "dataSeg", nullable = false, columnDefinition = "DATE")
    private LocalDate dataSeg;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name= "vencimento", nullable = true, columnDefinition = "DATE")
    private LocalDate vencimento;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipo_id", referencedColumnName = "id", nullable = false)
    private Tipo_Seguro tipoSeguro;

    @OneToOne
    @JoinColumn(name = "veiculo_id", referencedColumnName = "id", nullable = true, columnDefinition = "bigint")
    private Veiculo veiculo;

    @NotNull
    @OneToOne
    @JoinColumn(name = "email_id", referencedColumnName = "id", nullable = false)
    private Email email;
}
