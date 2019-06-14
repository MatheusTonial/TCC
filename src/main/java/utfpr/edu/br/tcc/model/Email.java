package utfpr.edu.br.tcc.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 45, message="Deve estar entre {min} e {max} caracters")
    @Column(length = 45, nullable = false)
    private String titulo;

    @NotBlank(message = "Campo em branco!")
    @Size(min = 4, max = 250, message="Deve estar entre {min} e {max} caracters")
    @Column(length = 250, nullable = false)
    private String Texto;

    @NotNull(message = "Campo em branco!")
    @Column(nullable = false)
    private Integer prazo1;

    @NotNull(message = "Campo em branco!")
    @Column(nullable = false)
    private Integer prazo2;

}
