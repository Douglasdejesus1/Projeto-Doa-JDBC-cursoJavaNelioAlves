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
		System.out.println("Test1 find by Id");
		System.out.println(seller);
		System.out.println("\nTest2 find by department");
		sellerDao = DaoFactory.createSellerDao();
		Department department = new Department(4, null);
		List<Seller>list=sellerDao.findByDepartment(department);
		for(Seller obj: list) {
			System.out.println(obj);
		}
		System.out.println("\nTest3 find all");
		sellerDao = DaoFactory.createSellerDao();
		
		list=sellerDao.findAll();
		for(Seller obj: list) {
			System.out.println(obj);
		}
		
	}

}
