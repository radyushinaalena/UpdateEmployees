package ru.skypro.lessons.springboot.weblibrary.controller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportFileService;
import java.io.IOException;

@RestController
@RequestMapping("/report")
@Getter
@AllArgsConstructor

public class ReportController {

    private final ReportFileService reportFileService;

    @PostMapping
    public Long addReport() throws IOException {
        return reportFileService.generatesAndSavesJsonFile();
    }

    @GetMapping("/{id}")
    public String getReportByID(@PathVariable Integer id) {
        return reportFileService.getSomeFileById(id);
    }
}
