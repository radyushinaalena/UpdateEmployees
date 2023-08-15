package ru.skypro.lessons.springboot.weblibrary;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.pojo.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeQueryRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportFileRepository;
import ru.skypro.lessons.springboot.weblibrary.service.ReportFileServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
public class ReportFileServiceImplTest {

    @Mock
    private EmployeeQueryRepository employeeQueryRepository;

    @Mock
    private ReportFileRepository reportFileRepository;


    @InjectMocks
    private ReportFileServiceImpl reportFileService;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withUsername("postgres")
            .withPassword("admin");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @SneakyThrows
    @DisplayName("addJsonFileMethod")
    @Test
    public void addJsonFileMethodTest() {
        Integer expected = 0;
        ReportFile reportFile = new ReportFile();
        lenient().when(employeeQueryRepository.sortDepartment()).thenReturn(expected);
        assertNull(reportFileService.generatesAndSavesJsonFile());

        lenient().when(employeeQueryRepository.countEmployeeInDepartment()).thenReturn(expected);
        assertNull(reportFileService.generatesAndSavesJsonFile());

        lenient().when(employeeQueryRepository.maxSalary()).thenReturn(expected);
        assertNull(reportFileService.generatesAndSavesJsonFile());

        lenient().when(employeeQueryRepository.minSalary()).thenReturn(expected);
        assertNull(reportFileService.generatesAndSavesJsonFile());

        lenient().when(employeeQueryRepository.averageSalary()).thenReturn(expected);
        assertNull(reportFileService.generatesAndSavesJsonFile());

        verify(reportFileRepository, never()).save(reportFile);
    }

    @DisplayName("getSomeFileByIdNegative")
    @Test
    public void getSomeFileByIdNegativeMethodTest() {
        verify(reportFileRepository, never()).findById(anyInt());

        lenient().doThrow(IncorrectResultSizeDataAccessException.class).when(reportFileRepository).findById(anyInt());
        assertThrows(IncorrectResultSizeDataAccessException.class, () -> reportFileService.getSomeFileById(anyInt()));
    }
}
