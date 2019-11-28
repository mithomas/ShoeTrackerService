package de.mthix.footloose.service;

import de.mthix.footloose.model.Sighting;
import de.mthix.footloose.service.dao.SightingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
public class FootlooseServiceController implements SightingsApi {

  @Autowired
  private SightingDao dao;

  @Override
  public ResponseEntity<Sighting> getSightingById(Long sightingId) {
    return ResponseEntity.ok(dao.getSighting(sightingId));
  }

  @Override
  public ResponseEntity<List<Sighting>> getSightings() {
    List<Sighting> sightings = dao.getSightings();
    return ResponseEntity.ok(sightings != null ? sightings : emptyList());
  }

  @Override
  public ResponseEntity<Sighting> postSighting(@Valid Sighting sighting) {
    Sighting persisted = dao.createSighting(sighting);
    return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(persisted.getId()).toUri()).body(persisted);
  }
}
