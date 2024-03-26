package vn.edu.tdtu.springecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phone;
    private String specificAddress;
    private String city;
    private String district;
    private String ward;
    @Column(name = "date_created")
    private String dateCreated;
    private int status;
    @OneToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;
    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;
}
