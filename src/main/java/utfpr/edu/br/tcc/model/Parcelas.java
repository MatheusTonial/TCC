package utfpr.edu.br.tcc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "parcelas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Parcelas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 1, max = 45)
    @Column(length = 45, nullable = false)
    private String tamanho;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name= "dataVencimento", nullable = false, columnDefinition = "DATE")
    private LocalDate dataVencimento;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name= "dataPago", nullable = true, columnDefinition = "DATE")
    private LocalDate dataPago;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 1, max = 45)
    @Column(length = 45, nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "seguro_id", referencedColumnName = "id", nullable = false)
    private Seguro seguro;

}
