package de.mthix.footloose.service.dao;

import com.rits.cloning.Cloner;
import de.mthix.footloose.model.Shoe;
import de.mthix.footloose.model.Sighting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.Validator;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SightingDaoImplTest {

    @InjectMocks
    private SightingDaoImpl dao;

    @Mock
    private SightingMapper sightingMapper;
    @Mock
    private Validator validator;

    @BeforeEach
    void setUp() {
        dao = new SightingDaoImpl();
        initMocks(this);
    }

    @Nested
    class GetSightings {

        private List<Sighting> sightings;

        @BeforeEach
        void initSightingsList() {
            sightings = asList(new Sighting().id(1L)
                    .latitude(1.1f)
                    .longitude(-1.1f)
                    .seenAt(OffsetDateTime.parse("2018-10-11T16:28:03Z"))
                    .seenBy("Al G. Mein")
                    .addSeenShoesItem(new Shoe().id(1L).addSightingsItem(1L)));

            when(sightingMapper.getSightings()).thenReturn(sightings);
        }

        @Test
        void returnedSameObject() {
            assertThat(dao.getSightings()).isSameAs(sightings);
        }

        @Test
        void updatedNestedIdFields() {
            List<Sighting> expected = Cloner.standard().deepClone(sightings);
            assertThat(dao.getSightings()).isEqualTo(expected);
        }
    }

    @Nested
    class CreateSighting {

        private Sighting sighting;

        @BeforeEach
        void initSighting() {
            sighting = new Sighting()
                    .latitude(1.1f)
                    .longitude(-1.1f)
                    .seenAt(OffsetDateTime.parse("2018-10-11T16:28:03Z"))
                    .seenBy("Al G. Mein")
                    .addSeenShoesItem(new Shoe());

            when(sightingMapper.createSighting(sighting)).thenAnswer(invocation -> {
                ((Sighting) invocation.getArgument(0)).id(1L);
                return 1;
            });
        }

        @Test
        void returnedSameObject() {
            assertThat(dao.createSighting(sighting)).isSameAs(sighting);
        }

        @Test
        void updatedNestedIdFields() {
            Sighting updated = Cloner.standard().deepClone(sighting);
            updated.id(1L).getSeenShoes().get(0).id(1L).addSightingsItem(1L);

            assertThat(dao.createSighting(sighting)).isEqualTo(updated);
        }
    }

    @Nested
    class GetSighting {

        private Sighting sighting;

        @BeforeEach
        void initSighting() {
            sighting = new Sighting().id(1L)
                    .latitude(1.1f)
                    .longitude(-1.1f)
                    .seenAt(OffsetDateTime.parse("2018-10-11T16:28:03Z"))
                    .seenBy("Al G. Mein")
                    .addSeenShoesItem(new Shoe().id(1L).addSightingsItem(1L));

            when(sightingMapper.getSighting(1)).thenReturn(sighting);
        }

        @Test
        void passedThroughObjectFromMapper() {
            assertThat(dao.getSighting(1)).isSameAs(sighting);
        }

        @Test
        void unmodifiedObjectFromMapper() {
            Sighting expected = Cloner.standard().deepClone(sighting);
            assertThat(dao.getSighting(1)).isEqualTo(expected);
        }
    }

    @Test
    void getSightingsCount() {
        when(sightingMapper.getSightingsCount()).thenReturn(42);
        assertThat(dao.getSightingsCount()).isEqualTo(42);
    }
}