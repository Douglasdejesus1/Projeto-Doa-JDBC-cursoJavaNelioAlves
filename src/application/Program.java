package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(26);
		System.out.println("Test1");
		System.out.println(seller);
		System.out.println("\nTest2");
		sellerDao = DaoFactory.createSellerDao();
		Department department = new Department(4, null);
		List<Seller>list=sellerDao.findByDepartment(department);
		for(Seller obj: list) {
			System.out.println(obj);
		}
		
	}

}
