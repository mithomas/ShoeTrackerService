package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Sighting;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("contextTest")
class SightingDaoImplContextTest {

  @Autowired
  private SightingDao dao;

  @MockBean
  private SightingMapper sightingMapper;

  @Nested
  class PerformValidation {

    @Test
    void GetSightings() {
      when(sightingMapper.getSightings()).thenReturn(asList(new Sighting()));

      assertThatThrownBy(() -> {
        dao.getSightings();
      }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void GetSighting() {
      when(sightingMapper.getSighting(1L)).thenReturn(new Sighting());

      assertThatThrownBy(() -> {
        dao.getSighting(1L);
      }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void CreateSighting() {
      assertThatThrownBy(() -> {
        dao.createSighting(new Sighting());
      }).isInstanceOf(ConstraintViolationException.class);
    }
  }

  @Nested
  class GetSighting {

    @Test
    void notFound() {
      when(sightingMapper.getSighting(42)).thenReturn(null);
      assertThatThrownBy((() -> dao.getSighting(42))).isInstanceOf(NoSuchElementException.class);
    }
  }
}