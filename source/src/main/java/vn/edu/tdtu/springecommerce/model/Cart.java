package vn.edu.tdtu.springecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int status;
    @Column(name = "total_amount")
    private int totalAmount;
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;
}
