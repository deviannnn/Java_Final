package vn.edu.tdtu.springecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
