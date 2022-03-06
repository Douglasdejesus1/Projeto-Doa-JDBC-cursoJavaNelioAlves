package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
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
		/*System.out.println("\nTest4 SELLER insert");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());*/
		
		System.out.println("\nTest5 SELLER update");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update completed");
		
		System.out.println("\nTest6 SELLER delete");
		System.out.println("Enter id ofr delete test: ");
		int x = 1;
		while(x==1) {
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("delete completed");
		
		}
		sc.close();
	}

}
