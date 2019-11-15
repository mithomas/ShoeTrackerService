package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Primary
@Validated
/**
 * This will use a batch-executor for multi-inserts; see: http://mybatis-user.963551.n3.nabble.com/Batch-inserts-updates-deletes-and-BatchResults-td3903754.html
 */
public class SightingDaoImpl implements SightingDao {

    @Autowired
    private SightingMapper sightingMapper;
    @Autowired
    private Validator validator;

    @Override
    public List<Sighting> getSightings() {
        final List<Sighting> sightings = sightingMapper.getSightings();
        validate(sightings);
        return sightings;
    }

    @Override
    public int getSightingsCount() {
        return sightingMapper.getSightingsCount();
    }

    @Override
    public Sighting getSighting(long id) {
        final Sighting sighting = sightingMapper.getSighting(id);
        validate(sighting);
        return sighting;
    }

    @Override
    @Transactional
    public Sighting createSighting(@Valid Sighting sighting) {
        sightingMapper.createSighting(sighting);

        sighting.getSeenShoes().get(0).id(sighting.getId()).addSightingsItem(sighting.getId());

        return sighting;
    }

    /**
     * Bean-validates elements of the given list and raises a {@link ConstraintViolationException} if this fails.
     * <p>
     * Spring's validation capabilities based on AOP do not work for local methods since they use proxy objects, so it is
     * performed programmatically here.
     *
     * @throws ConstraintViolationException if the validation failed.
     */
    private <T> void validate(Collection<T> objects) {
        Set<ConstraintViolation<T>> constraintViolations = objects.stream().map(o -> validator.validate(o)).flatMap(Set::stream).collect(Collectors.toSet());

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
    /**
     * Bean-validates all given objects and raises a {@link ConstraintViolationException} if this fails.
     * <p>
     * Spring's validation capabilities based on AOP do not work for local methods since they use proxy objects, so it is
     * performed programmatically here.
     *
     * @throws ConstraintViolationException if the validation failed.
     */
    private <T> void validate(T... objects) {
        validate(asList(objects));
    }
}
