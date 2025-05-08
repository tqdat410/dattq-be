package portfolio.dattq.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 100)
    private String label;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(length = 100)
    private String icon;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
