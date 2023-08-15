package ru.skypro.lessons.springboot.weblibrary;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import ru.skypro.lessons.springboot.weblibrary.pojo.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeQueryRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportFileRepository;
import ru.skypro.lessons.springboot.weblibrary.service.ReportFileServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ReportFileServiceImplTest {

    @Mock
    private EmployeeQueryRepository employeeQueryRepository;

    @Mock
    private ReportFileRepository reportFileRepository;


    @InjectMocks
    private ReportFileServiceImpl reportFileService;


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
