package com.example.OrderService.model;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    private String ordernumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineIteamsList;

}
