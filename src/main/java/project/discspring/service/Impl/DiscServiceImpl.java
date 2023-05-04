package project.discspring.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.discspring.model.Disc;
import project.discspring.model.form.DiscForm;
import project.discspring.model.form.DiscFormUpdate;
import project.discspring.repository.DiscRepository;
import project.discspring.service.DiscService;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscServiceImpl implements DiscService {

    private final DiscRepository repository;


    @Override
    public Disc create(DiscForm form) {
        Disc d = new Disc();
        d.setName(form.getName());
        d.setSinger(form.getSinger());
        d.setNum(form.getNum());

        return repository.save(d);
    }

    @Override
    public Disc get(Long id) {

        return repository.findById(id).get();
    }

    @Override
    public List<Disc> getAll() {

        return repository.findAll();
    }

    @Override
    public Disc update(DiscFormUpdate formUpdate) {
        Disc d = repository.findById(formUpdate.getId()).get();
        d.setName(formUpdate.getName());
        d.setSinger(formUpdate.getSinger());
        d.setNum(formUpdate.getNum());

        return repository.save(d);
    }

    @Override
    public Disc delete(Long id) {
        Disc d = repository.findById(id).get();

        repository.delete(d);
        return d;
    }
}
