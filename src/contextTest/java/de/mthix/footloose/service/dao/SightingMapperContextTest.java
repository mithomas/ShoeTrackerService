package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Shoe;
import de.mthix.footloose.model.Sighting;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("contextTest")
@Transactional
class SightingMapperContextTest {

    @Autowired
    private SightingMapper sightingMapper;

    @Test
    public void createSighting() {
        Sighting sighting = new Sighting()
                .latitude(4f)
                .longitude(-4f)
                .seenAt(OffsetDateTime.parse("2019-11-11T23:36:18Z"))
                .seenBy("Kurt C. Hose")
                .addSeenShoesItem(new Shoe());

        Long newId = sightingMapper.getSightingsCount()+1L;
        int insertedRows = sightingMapper.createSighting(sighting);

        assertThat(insertedRows).isEqualTo(1);
        assertThat(sighting).isEqualTo(new Sighting()
                .id(newId)
                .latitude(4f)
                .longitude(-4f)
                .seenAt(OffsetDateTime.parse("2019-11-11T23:36:18Z"))
                .seenBy("Kurt C. Hose")
                .addSeenShoesItem(new Shoe()));
    }

    @Test
    public void getSightings() {
        assertThat(sightingMapper.getSightings()).containsExactlyInAnyOrder(
                new Sighting().id(1L)
                        .latitude(1.1f)
                        .longitude(-1.1f)
                        .seenAt(OffsetDateTime.parse("2018-10-11T16:28:03Z"))
                        .seenBy("Al G. Mein")
                        .addSeenShoesItem(new Shoe().id(1L).addSightingsItem(1L)),
                new Sighting().id(2L)
                        .latitude(2.1f)
                        .longitude(-2.1f)
                        .seenAt(OffsetDateTime.parse("2019-01-12T14:57:42Z"))
                        .seenBy("A. Q. Mulator")
                        .addSeenShoesItem(new Shoe().id(2L).addSightingsItem(2L)),
                new Sighting().id(3L)
                        .latitude(3.1f)
                        .longitude(-3.1f)
                        .seenAt(OffsetDateTime.parse("2019-02-01T00:12:42Z"))
                        .seenBy("H. B. Nichts")
                        .addSeenShoesItem(new Shoe().id(3L).addSightingsItem(3L)));
    }

    @Test
    public void getSightingsCount() {
        assertThat(sightingMapper.getSightingsCount()).isEqualTo(3);
    }

    @Nested
    class GetSighting {

        @Test
        public void existing() {
            assertThat(sightingMapper.getSighting(1)).isEqualTo(
                    new Sighting().id(1L)
                            .latitude(1.1f)
                            .longitude(-1.1f)
                            .seenAt(OffsetDateTime.parse("2018-10-11T16:28:03Z"))
                            .seenBy("Al G. Mein")
                            .addSeenShoesItem(new Shoe().id(1L).addSightingsItem(1L)));
        }

        @Test
        public void notExisting() {
            assertThat(sightingMapper.getSighting(100)).isNull();
        }
    }
}