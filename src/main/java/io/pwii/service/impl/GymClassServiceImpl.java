package io.pwii.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import io.pwii.entity.Athlete;
import io.pwii.entity.GymClass;
import io.pwii.entity.Instructor;
import io.pwii.mapper.GymClassMapper;
import io.pwii.model.PageModel;
import io.pwii.model.request.GymClassRequestModel;
import io.pwii.model.request.GymClassUpdateRequestModel;
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

    validateClassTimeForInstructor(gymClass.getClassDate(), gymClass.getClassTime(),
        gymClass.getInstructorId());

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

  @Transactional
  @Override
  public GymClass update(Long gymClassId, GymClassUpdateRequestModel gymClass) {
    Optional<GymClass> optionalGymClass = gymClassRepository.findByIdOptional(gymClassId);
    if (optionalGymClass.isEmpty()) {
      throw new NotFoundException("GymClass Not Found");
    }

    GymClass entity = optionalGymClass.get();

    if (gymClass.getInstructorId() != null
        && gymClass.getInstructorId() != entity.getInstructor().getId()) {
      Optional<Instructor> newInstructor =
          instructorRepository.findByIdOptional(gymClass.getInstructorId());
      if (newInstructor.isEmpty()) {
        throw new BadRequestException("Invalid new Instructor");
      }
      entity.setInstructor(newInstructor.get());
    }

    if (gymClass.getAthletesIds() != null && gymClass.getAthletesIds().size() > 0) {
      List<Athlete> athletes = athleteRepository.findAllById(gymClass.getAthletesIds());
      if (athletes.size() < 1) {
        throw new BadRequestException("Invalid Athletes");
      }
      entity.setAthletes(athletes);
    }


    LocalDate date = gymClass.getClassDate() != null ? gymClass.getClassDate() : entity.getClassDate();
    LocalTime time = gymClass.getClassTime() != null ? LocalTime.of(gymClass.getClassTime().getHour(), 0) : entity.getClassTime();

    if (gymClass.getClassDate() != null || gymClass.getClassTime() != null) {
      validateClassTimeForInstructor(date, time, entity.getInstructor().getId());
    }

    gymClassMapper.updateToEntity(gymClass, entity);
    entity.setClassTime(time);

    gymClassRepository.persist(entity);
    return entity;
  }

  private void validateClassTimeForInstructor(LocalDate date, LocalTime time, Long instructorId) {
    Optional<GymClass> instructorClass =
        gymClassRepository.findOneByClassDateAndClassTimeAndInstructor(
            date,
            time,
            instructorId);

    if (instructorClass.isPresent()) {
      throw new BadRequestException("Instructor already has a class in this Date and Time");
    }

    if (date.isBefore(LocalDate.now())) {
      throw new BadRequestException("classDate cannot be in the past");
    }
  }

}
