package org.master.service.screen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.master.dto.screen.ScreenCreateDTO;
import org.master.dto.screen.ScreenListDTO;
import org.master.events.screen.ScreenEventPublisher;
import org.master.events.screen.ScreenEventType;
import org.master.model.screen.ScreenReadModel;
import org.master.model.screen.ScreenWriteModel;
import org.master.repository.screen.ScreenWriteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScreenService {

    @Inject
    EntityManager em;

    @Inject
    ScreenWriteRepository writeRepository;

    @Inject
    ScreenEventPublisher screenEventPublisher;

    @Transactional
    public void createScreen(ScreenCreateDTO screenCreateDTO){
        ScreenWriteModel screenWriteModel = new ScreenWriteModel();

        screenWriteModel.setName(screenCreateDTO.getName());
        screenWriteModel.setData(screenCreateDTO.getData());


        screenEventPublisher.publish(ScreenEventType.ScreenCreated, screenWriteModel.getUuid(), screenWriteModel.getName(), screenWriteModel.getData());

        em.persist(screenWriteModel);
    }

    public List<ScreenListDTO> getAllScreens(){
        return em.createQuery(
                        "SELECT new org.master.dto.screen.ScreenListDTO(s.id, s.name, s.data) FROM ScreenReadModel s", ScreenListDTO.class)
                .getResultList();
    }

    @Transactional
    public void createReadScreen(ScreenCreateDTO screenCreateDTO){
        ScreenReadModel screenReadModel = new ScreenReadModel();

        screenReadModel.setName(screenCreateDTO.getName());
        screenReadModel.setData(screenCreateDTO.getData());

        em.persist(screenReadModel);
    }

    public ScreenReadModel findByUuid(UUID uuid) {
        return em.find(ScreenReadModel.class, uuid);
    }

    public ScreenReadModel findByName(String name) {
        try {
            return em.createQuery("SELECT s FROM ScreenReadModel s WHERE s.name = :name", ScreenReadModel.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no user is found
        }
    }
}
