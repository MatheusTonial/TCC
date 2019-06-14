package utfpr.edu.br.tcc.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

//import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "cidades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message="Deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String nome;

    @NotNull(message = "{Selecione um estado}")
    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "id", nullable = false)
    private Estado estado;

}
