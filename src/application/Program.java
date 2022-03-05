package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department obj = new Department(3,"Douglas");
		
		Seller seller = new Seller(4, "Douglas", "douglas@gmail.com", new Date(), 2000.0, obj);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println(seller);

	}

}
