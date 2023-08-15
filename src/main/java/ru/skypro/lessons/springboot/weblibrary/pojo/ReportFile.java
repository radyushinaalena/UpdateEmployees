package ru.skypro.lessons.springboot.weblibrary.pojo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table (name = "report")

public class ReportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "file_name", columnDefinition = "text")
    private String fileName;

    public ReportFile() {
    }

}
