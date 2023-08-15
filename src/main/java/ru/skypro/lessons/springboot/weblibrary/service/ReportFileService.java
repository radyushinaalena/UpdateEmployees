package ru.skypro.lessons.springboot.weblibrary.service;
import java.io.IOException;

public interface ReportFileService {

    Long generatesAndSavesJsonFile() throws IOException;
    String getSomeFileById(int id);

}
