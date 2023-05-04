package project.discspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name ="disc")
public class Disc {

    public Disc(String name, String singer, Integer num) {
        this.name = name;
        this.singer = singer;
        this.num = num;
    }

    @NotNull
    @Column(nullable = false, name = "nome")
    private String name;

    @NotNull
    @Column(nullable = false, name = "singer")
    private String singer;

    @Column(unique = true, nullable = false, name = "num")
    private Integer num;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

}



