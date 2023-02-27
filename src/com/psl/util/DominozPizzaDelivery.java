package com.psl.util;

import java.util.List;
import java.util.Set;

import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;

public interface DominozPizzaDelivery {

	void populateData(List<Dish> dishs,Set<Location> locations);
	void calculateLocationForDistance(List<Dish> dishs,Set<Location> locations );
	List<Order> calculateOrder(List<Dish> dishs,Set<Location> locations);
	void freeDelivery(List<Order> orders,List<Dish> dishs,Set<Location> locations);
	
}
