package com.ash.GenericTracker.service;

import com.ash.GenericTracker.entity.*;
import com.ash.GenericTracker.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EntryCascadeDeleteTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private EntryRowRepository entryRowRepository;

    @Autowired
    private EntryValueRepository entryValueRepository;

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    private User testUser;
    private Bucket testBucket;
    private Parameter testParameter;
    private Entry testEntry;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .name("Test User")
                .password("password")
                .build();
        entityManager.persist(testUser);

        testBucket = Bucket.builder()
                .bucketName("Test Bucket")
                .user(testUser)
                .build();
        entityManager.persist(testBucket);

        testParameter = Parameter.builder()
                .parameterName("Test Param")
                .bucketId(testBucket)
                .build();
        entityManager.persist(testParameter);

        testEntry = Entry.builder()
                .bucketId(testBucket)
                .userId(testUser)
                .entryDate(LocalDate.now())
                .notes("Test Entry")
                .build();
        entityManager.persist(testEntry);
    }

    @Test
    void testDeleteEntry_cascadesToEntryRows() {
        EntryRow row1 = EntryRow.builder()
                .entry(testEntry)
                .rowIndex(0)
                .build();
        entityManager.persist(row1);

        EntryRow row2 = EntryRow.builder()
                .entry(testEntry)
                .rowIndex(1)
                .build();
        entityManager.persist(row2);

        entityManager.flush();
        entityManager.clear();

        assertThat(entryRowRepository.count()).isEqualTo(2);

        entryRepository.deleteById(testEntry.getId());
        entityManager.flush();

        assertThat(entryRowRepository.count()).isEqualTo(0);
    }

    @Test
    void testDeleteEntry_cascadesToEntryValues() {
        EntryValue value1 = EntryValue.builder()
                .entry(testEntry)
                .parameter(testParameter)
                .valueText("Test Value 1")
                .build();
        entityManager.persist(value1);

        EntryValue value2 = EntryValue.builder()
                .entry(testEntry)
                .parameter(testParameter)
                .valueText("Test Value 2")
                .build();
        entityManager.persist(value2);

        entityManager.flush();
        entityManager.clear();

        assertThat(entryValueRepository.count()).isEqualTo(2);

        entryRepository.deleteById(testEntry.getId());
        entityManager.flush();

        assertThat(entryValueRepository.count()).isEqualTo(0);
    }

    @Test
    void testDeleteEntry_cascadesToEntryRowsAndThenToEntryValues() {
        EntryRow row = EntryRow.builder()
                .entry(testEntry)
                .rowIndex(0)
                .build();
        entityManager.persist(row);
        entityManager.flush();

        EntryValue value1 = EntryValue.builder()
                .entry(testEntry)
                .entryRow(row)
                .parameter(testParameter)
                .valueText("Value 1")
                .build();
        entityManager.persist(value1);

        EntryValue value2 = EntryValue.builder()
                .entry(testEntry)
                .entryRow(row)
                .parameter(testParameter)
                .valueText("Value 2")
                .build();
        entityManager.persist(value2);

        entityManager.flush();
        entityManager.clear();

        assertThat(entryRowRepository.count()).isEqualTo(1);
        assertThat(entryValueRepository.count()).isEqualTo(2);

        entryRepository.deleteById(testEntry.getId());
        entityManager.flush();

        assertThat(entryRowRepository.count()).isEqualTo(0);
        assertThat(entryValueRepository.count()).isEqualTo(0);
    }

    @Test
    void testDeleteEntry_entryIsDeleted() {
        entityManager.flush();
        entityManager.clear();

        assertThat(entryRepository.existsById(testEntry.getId())).isTrue();

        entryRepository.deleteById(testEntry.getId());
        entityManager.flush();

        assertThat(entryRepository.existsById(testEntry.getId())).isFalse();
    }
}
