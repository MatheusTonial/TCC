package utfpr.edu.br.tcc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "marcas")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@EqualsAndHashCode
//@ToString
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message="Deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String descricao;


}
