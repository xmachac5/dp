package org.master.repository.screen;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.master.model.screen.ScreenWriteModel;


@ApplicationScoped
public class ScreenWriteRepository implements PanacheRepository<ScreenWriteModel> {
    // Custom queries can be added here
}
