package ru.skypro.lessons.springboot.weblibrary.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeQueryRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportFileRepository;
import ru.skypro.lessons.springboot.weblibrary.helperMethod.HelperMethods;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Service

public class ReportFileServiceImpl implements ReportFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportFileServiceImpl.class);
    private final EmployeeQueryRepository employeeQueryRepository;
    private final ReportFileRepository reportFileRepository;

    @Override
    public Long generatesAndSavesJsonFile() throws IOException {
        LOGGER.info("Вызов метода сохранения файла");
        String fileName = "report.json";
        File file = new File(fileName);
        file.createNewFile();
        ReportDTO report = new ReportDTO(employeeQueryRepository.sortDepartment(), employeeQueryRepository.countEmployeeInDepartment(),
                employeeQueryRepository.maxSalary(), employeeQueryRepository.minSalary(), employeeQueryRepository.averageSalary());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(report);
        HelperMethods.writeTextToFile(json, fileName);
        ReportFile reportFile = new ReportFile();
        reportFile.setFileName(fileName);
        reportFileRepository.save(reportFile);
        LOGGER.debug("Файл сохранен");
        return reportFile.getId();
    }

    @Override
    public String getSomeFileById(int id) {
        LOGGER.info("Вызов метода для нахождения файла по id = {}", id);
        Optional<ReportFile> employeeOptional = reportFileRepository.findById(id);
        employeeOptional.orElseThrow(() -> new IncorrectResultSizeDataAccessException(id));
        ReportFile someFile = employeeOptional.get();
        LOGGER.debug("Файл найден");
        return someFile.getFileName();
    }
}
