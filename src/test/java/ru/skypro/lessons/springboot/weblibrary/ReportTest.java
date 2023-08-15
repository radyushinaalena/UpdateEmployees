package ru.skypro.lessons.springboot.weblibrary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.ReportFile;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportFileRepository;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "postgres", password = "admin", roles = "ADMIN")
@SpringBootTest
@AutoConfigureMockMvc
public class ReportTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportFileRepository reportFileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("addJsonFile")
    @Test
    void addJsonFileTest() throws Exception {

        ReportDTO report = new ReportDTO(1,2,3,4,5);

        mockMvc.perform(MockMvcRequestBuilders.post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("getSomeFileByID")
    @Test
    void getSomeFileByIDTest() throws Exception {
        int id = 1;
        Optional<ReportFile> employeeOptional = reportFileRepository.findById(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/report/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeOptional)))
                .andExpect(status().isForbidden());
    }
}
