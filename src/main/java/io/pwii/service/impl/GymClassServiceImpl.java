package io.pwii.service.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import io.pwii.entity.GymClass;
import io.pwii.entity.Instructor;
import io.pwii.mapper.GymClassMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.repository.AthleteRepository;
import io.pwii.repository.GymClassRepository;
import io.pwii.repository.InstructorRepository;
import io.pwii.service.GymClassService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class GymClassServiceImpl implements GymClassService {

  @Inject
  private GymClassRepository gymClassRepository;

  @Inject
  private InstructorRepository instructorRepository;

  @Inject
  private AthleteRepository athleteRepository;

  @Inject
  private GymClassMapper gymClassMapper;

  @Transactional
  @Override
  public GymClass create(GymClassRequestModel gymClass) {
    Optional<Instructor> optionalInstructor =
        instructorRepository.findByIdOptional(gymClass.getInstructorId());

    if (optionalInstructor.isEmpty()) {
      throw new BadRequestException("Invalid Instructor");
    }

    gymClass.setClassTime(LocalTime.of(gymClass.getClassTime().getHour(), 0));

    Optional<GymClass> instructorClass = gymClassRepository.findOneByClassDateAndClassTimeAndInstructor(
      gymClass.getClassDate(), 
      gymClass.getClassTime(), 
      gymClass.getInstructorId()
    );

    if (instructorClass.isPresent()) {
      throw new BadRequestException("Instructor already has a class in this Date and Time");
    }

    GymClass entity = gymClassMapper.toEntity(gymClass);
    entity.setInstructor(optionalInstructor.get());

    if (gymClass.getAthletesIds().size() > 0) {
      entity.setAthletes(athleteRepository.findAllById(gymClass.getAthletesIds()));
    }

    gymClassRepository.persist(entity);

    return entity;
  }

  @Override
  public PageModel<GymClass> list(int page, int limit) {
    PanacheQuery<GymClass> allGymClasses = gymClassRepository.findAll();
    List<GymClass> currentPageContent = allGymClasses.page(page, limit).list();

    return PageModel.<GymClass>builder()
    .content(currentPageContent)
    .currentPageTotalItems(currentPageContent.size())
    .currentPage(page)
    .numberOfPages(allGymClasses.pageCount())
    .totalItems(allGymClasses.count())
    .build();
  }

}
