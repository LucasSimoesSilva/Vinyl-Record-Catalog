package project.discspring.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscFormUpdate {
    private Long id;
    private String name;
    private String singer;
    private Integer num;
}
