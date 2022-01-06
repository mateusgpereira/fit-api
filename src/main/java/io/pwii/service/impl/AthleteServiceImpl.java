package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import io.pwii.entity.Athlete;
import io.pwii.entity.enums.UserRoles;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.AthleteRequestModel;
import io.pwii.model.request.AthleteUpdateRequest;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.AthleteService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class AthleteServiceImpl implements AthleteService {

  @Inject
  AthleteRepository athleteRepository;

  @Inject
  InstructorRepository instructorRepository;

  @Inject
  AthleteMapper athleteMapper;

  @Override
  public Athlete create(AthleteRequestModel athlete) {

    Athlete athleteEntity = athleteMapper.toEntity(athlete);
    athleteEntity.setPassword(BcryptUtil.bcryptHash(athlete.getPassword()));

    athleteEntity.setRole(UserRoles.ATHLETE);
    athleteRepository.persist(athleteEntity);
    return athleteEntity;
  }

  @Override
  public PageModel<Athlete> list(int page, int limit) {
    PanacheQuery<Athlete> allAthletes = athleteRepository.findAll();
    List<Athlete> currentPageList = allAthletes.page(page, limit).list();
    return PageModel.<Athlete>builder()
        .content(currentPageList)
        .currentPage(page)
        .currentPageTotalItems(currentPageList.size())
        .numberOfPages(allAthletes.pageCount())
        .totalItems(allAthletes.count())
        .build();
  }

  @Transactional
  @Override
  public Athlete update(Long athleteId, AthleteUpdateRequest athlete) {
    Optional<Athlete> optionalAthlete = athleteRepository.findByIdOptional(athleteId);
    if (optionalAthlete.isEmpty()) {
      throw new NotFoundException("Athlete Not Found");
    }

    Athlete entity = optionalAthlete.get();
    athleteMapper.updateToEntity(athlete, entity);

    if (athlete.getPassword() != null && !athlete.getPassword().isEmpty()) {
      entity.setPassword(BcryptUtil.bcryptHash(athlete.getPassword()));
    }

    athleteRepository.persist(entity);

    return entity;
  }

  @Transactional
  @Override
  public void delete(Long athleteId) {
    boolean wasDeleted = athleteRepository.deleteById(athleteId);

    if (!wasDeleted) {
      throw new BadRequestException("Something went wrong");
    }
    
  }

  @Override
  public Athlete getById(Long athleteId) {
    Optional<Athlete> optionalAthlete = athleteRepository.findByIdOptional(athleteId);
    if (optionalAthlete.isEmpty()) {
      throw new NotFoundException("Athlete Not Found");
    }
    return optionalAthlete.get();
  }

}
