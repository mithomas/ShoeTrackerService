package de.mthix.footloose.service;

import com.rits.cloning.Cloner;
import de.mthix.footloose.model.Sighting;
import de.mthix.footloose.service.dao.SightingDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


class FootlooseServiceControllerTest {

    @InjectMocks
    private FootlooseServiceController controller;

    @Mock
    private SightingDao dao;

    @BeforeEach
    void initController() {
        controller = new FootlooseServiceController();
        initMocks(this);
    }

    @Nested
    class GetSightingById {

        private ResponseEntity<Sighting> response;

        /**
         * Includes the not-found-case via {@code NoSuchElementException}; this is handled via {@link FootlooseServiceController}.
         */
        @ParameterizedTest
        @ValueSource(classes = {RuntimeException.class, NoSuchElementException.class})
        void daoException(Class<? extends RuntimeException> exceptionClass) throws IllegalAccessException, InstantiationException {
            RuntimeException ex = exceptionClass.newInstance();
            when(dao.getSighting(0L)).thenThrow(ex);
            assertThatThrownBy(() -> controller.getSightingById(0L)).isSameAs(ex);
        }

        @Nested
        class Found {

            private Sighting sighting;

            @BeforeEach
            void initAndCallController() {
                sighting = new Sighting();
                when(dao.getSighting(0L)).thenReturn(sighting);
                response = controller.getSightingById(0L);
            }

            @Test
            void httpStatus() {
                assertThat(response.getStatusCode()).isEqualTo(OK);
            }

            @Test
            void body() {
                assertThat(response.getBody()).isEqualTo(sighting);
            }
        }
    }

    @Nested
    class GetSightings {

        private ResponseEntity<List<Sighting>> response;

        @ParameterizedTest
        @ValueSource(classes = RuntimeException.class)
        void daoException(Class<? extends RuntimeException> exceptionClass) throws IllegalAccessException, InstantiationException {
            RuntimeException ex = exceptionClass.newInstance();
            when(dao.getSightings()).thenThrow(ex);
            assertThatThrownBy(() -> controller.getSightings()).isSameAs(ex);
        }

        @Nested
        class Null {

            @BeforeEach
            void initAndCallController() {
                when(dao.getSightings()).thenReturn(null);
                response = controller.getSightings();
            }

            @Test
            void httpStatus() {
                assertThat(response.getStatusCode()).isEqualTo(OK);
            }

            @Test
            void body() {
                assertThat(response.getBody()).isEmpty();
            }
        }

        @Nested
        class NotNull {

            @ParameterizedTest
            @ValueSource(ints = {0, 1, 2, 5, 10})
            void httpStatus(int sightingCount) {
                List<Sighting> sightings = buildSightings(sightingCount);
                when(dao.getSightings()).thenReturn(sightings);
                assertThat(controller.getSightings().getStatusCode()).isEqualTo(OK);
            }

            @ParameterizedTest
            @ValueSource(ints = {0, 1, 2, 5, 10})
            void body(int sightingCount) {
                List<Sighting> sightings = buildSightings(sightingCount);
                when(dao.getSightings()).thenReturn(sightings);
                assertThat(controller.getSightings().getBody()).containsExactlyInAnyOrderElementsOf(sightings);
            }

            private List<Sighting> buildSightings(int count) {
                return IntStream.range(0, count + 1).mapToObj(id -> new Sighting().id((long) id)).collect(Collectors.toList());
            }
        }
    }

    @Nested
    class PostSighting {

        private ResponseEntity<Sighting> response;
        private Sighting sighting;

        @BeforeEach
        void initAndCallController() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI("/testSightings");
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            sighting = new Sighting().latitude(10.0f).longitude(-10.0f);
            when(dao.createSighting(sighting)).thenAnswer(invocation -> {
                Sighting result = Cloner.shared().deepClone(invocation.getArgument(0, Sighting.class));
                return result.id(42L);
            });
            response = controller.postSighting(sighting);
        }

        @Test
        void httpStatus() {
            assertThat(response.getStatusCode()).isEqualTo(CREATED);
        }

        @Test
        void locationHeader() {
            assertThat(response.getHeaders().getLocation()).isEqualTo(URI.create("http://localhost/testSightings/42"));
        }

        @Test
        void body() {
            assertThat(response.getBody()).isEqualTo(sighting.id(42L));
        }
    }
}
