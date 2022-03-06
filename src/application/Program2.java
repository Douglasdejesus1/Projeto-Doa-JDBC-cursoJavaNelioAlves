package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		/*System.out.println("\nTeste 1 intert in Department");
		Department newDepartment = new Department(null, "Veterinary");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());*/
		
		

		System.out.println("\nTeste 2 update in Department");
		Department department = departmentDao.findById(17);
		department.setName("TI");
		departmentDao.update(department);
		System.out.println("Updete completed!");
		
		System.out.println("\nTeste 3 findById");
		department =  departmentDao.findById(17);
		System.out.println(department);
		
		System.out.println("\nTeste 4 findByFindAll");
		List<Department>list=departmentDao.findAll();
		departmentDao=DaoFactory.createDepartmentDao();
		list=departmentDao.findAll();
		for(Department obj: list) {
			System.out.println(obj);
		}
		System.out.println("\nTeste 6 delete in Department");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("delete completed");
		
		
		
	}

}
