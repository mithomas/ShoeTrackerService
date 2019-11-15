package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Sighting;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface SightingDao {

    Sighting createSighting(@Valid Sighting sighting);

    Sighting getSighting(long id);

    List<Sighting> getSightings();

    int getSightingsCount();
}
