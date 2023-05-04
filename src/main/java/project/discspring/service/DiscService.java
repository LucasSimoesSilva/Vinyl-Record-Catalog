package project.discspring.service;

import project.discspring.model.Disc;
import project.discspring.model.form.DiscForm;
import project.discspring.model.form.DiscFormUpdate;

import java.util.List;

public interface DiscService {

    Disc create(DiscForm form);

    Disc get(Long id);

    List<Disc> getAll();

    Disc update(DiscFormUpdate formUpdate);

    Disc delete(Long id);

}
