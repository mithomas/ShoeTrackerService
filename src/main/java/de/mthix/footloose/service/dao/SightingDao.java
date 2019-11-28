package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Sighting;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface SightingDao {

  Sighting createSighting(@Valid Sighting sighting);

  /**
   * @throws java.util.NoSuchElementException if the sighting with the given id could  not be found.
   */
  Sighting getSighting(long id);

  List<Sighting> getSightings();

  int getSightingsCount();
}
