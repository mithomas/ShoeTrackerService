package de.mthix.footloose.service;

import de.mthix.footloose.model.Sighting;
import de.mthix.footloose.service.dao.SightingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class FootlooseServiceController implements SightingsApi {

    @Autowired
    private SightingDao dao;

    @Override
    public ResponseEntity<Sighting> getSightingById(Double sightingId) {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<Sighting>> getSightings() {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Sighting> postSighting(@Valid Sighting sighting) {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }
}
