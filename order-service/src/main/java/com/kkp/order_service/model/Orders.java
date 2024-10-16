package com.kkp.order_service.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String orderNumber;
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItems> orderLineItems;
}
