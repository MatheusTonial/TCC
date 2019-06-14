package utfpr.edu.br.tcc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "veiculos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 7, max = 8, message="Valor invalido! Ex: aaa-1111")
    @Column(length = 45, nullable = false)
    private String placa;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message="Deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String modelo;

    @NotNull(message = "Campo em branco!")
//    @NotBlank(message = "Campo em branco!")
    @Column(nullable = false)
    private Integer ano;


    @ManyToOne
    @JoinColumn(name = "marca_id", referencedColumnName = "id", nullable = false)
    private Marca marca;

}
