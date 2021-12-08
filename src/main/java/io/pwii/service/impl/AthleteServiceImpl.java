package io.pwii.service.impl;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import io.pwii.entity.Athlete;
import io.pwii.entity.Instructor;
import io.pwii.mapper.AthleteMapper;
import io.pwii.model.AthleteRest;
import io.pwii.model.PageModel;
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
  public Athlete create(AthleteRest athlete) {

    Athlete athleteEntity = athleteMapper.toEntity(athlete);
    athleteEntity.setPassword(BcryptUtil.bcryptHash(athlete.getPassword()));

    if (athlete.getInstructorId() != null) {
      Optional<Instructor> instructorOptional =
          instructorRepository.findByIdOptional(athlete.getInstructorId());

      if (instructorOptional.isPresent()) {
        athleteEntity.setInstructor(instructorOptional.get());
      }
    }

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

}
