package vn.edu.tdtu.springecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartdetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int price;
    private int amount;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
}
