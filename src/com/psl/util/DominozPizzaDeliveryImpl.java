package com.psl.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.psl.bean.ConnectionProvider;
import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;

public class DominozPizzaDeliveryImpl implements DominozPizzaDelivery {

	@Override
	public void populateData(List<Dish> dishs, Set<Location> locations) {

		try {
			Connection con = ConnectionProvider.getConn();

			String s = "Select * from dish";
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(s);

			while (set.next()) {
				int dishid = set.getInt("dishid");
				String dishname = set.getString("dishname");
				int dishprice = set.getInt("dishprice");
				int dishtime = set.getInt("dishtime");
				dishs.add(new Dish(dishid, dishname, dishprice, dishtime, new HashSet()));

			}
			String l = "Select * from location";
			Statement stmt1 = con.createStatement();
			ResultSet lset = stmt.executeQuery(l);

			while (lset.next()) {

				locations.add(new Location(lset.getInt("locationcode"), lset.getInt("locationdist"),
						lset.getInt("locationtime")));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void calculateLocationForDistance(List<Dish> dishs, Set<Location> locations) {
		for (Dish d : dishs) {
			for (Location l : locations) {
				if (d.getTimeToCook() + l.getLocationTime() <= 30) {
					d.getLocSetForDish().add(l);
				}
			}
		}
	}

	@Override
	public List<Order> calculateOrder(List<Dish> dishs, Set<Location> locations) {

		List<Order> list = new ArrayList<Order>();
		try {
			Connection con = ConnectionProvider.getConn();
			String l1 = "Select * from order1";
			Statement stmt1 = con.createStatement();
			ResultSet lset = stmt1.executeQuery(l1);

			while (lset.next()) {
				int dishid = lset.getInt("dishid");
				int locationcode =lset.getInt("locationcode");
				for (Dish d : dishs) {
					for (Location l : locations) {
						if (d.getDishId() == dishid && l.getLocationCode() == locationcode) {
							if (d.getTimeToCook() + l.getLocationTime() <= 30) {
								double cost = d.getCost() + l.getLocationDistance() * 1;
								list.add(new Order(dishid, locationcode, cost));
							}
						}
					}
				}
				
				
				
			}
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list;

	}

	@Override
	public void freeDelivery(List<Order> orders, List<Dish> dishs, Set<Location> locations) {
		for (Order o : orders) {
			for (Dish d : dishs) {
				for (Location l : locations) {
					if (o.getDishId() == d.getDishId() && o.getLocationCode() == l.getLocationCode()) {
						if (d.getCost() > 200 && l.getLocationDistance() <= 10) {
							o.setTotalCost(d.getCost());
							System.out.println(o);
						}
					}
				}
			}
		}
	}

}
