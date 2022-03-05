package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC (Connection conn) {
		this.conn=conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try{
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartament().getId());
			
			int RowsAffected = st.executeUpdate();
			
			if(RowsAffected>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpeted error! No rows afcted");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try{
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
					
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartament().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
	 	PreparedStatement st = null;
	 	ResultSet rs = null;
	 	try {
	 		st=conn.prepareStatement(
	 				"SELECT seller.*,department.Name as DepName "
	 				+ "FROM seller INNER JOIN department "
	 				+ "ON seller.DepartmentId = department.Id "
	 				+"WHERE seller.Id = ?");
	 		st.setInt(1, id);
	 		rs = st.executeQuery();
	 		if(rs.next()) {
	 			/*Department dep = new Department();
	 			dep.setId(rs.getInt("DepartmentId"));
	 			dep.setName(rs.getString("depName"));*/
	 			
	 			Department dep = instanciateDepartment(rs);
	 			
	 			/*Seller obj = new Seller();
	 			obj.setId(rs.getInt("Id"));
	 			obj.setName(rs.getString("Name"));
	 			obj.setEmail(rs.getString("Email"));
	 			obj.setBirthDate(rs.getDate("BirthDate"));
	 			obj.setBaseSalary(rs.getDouble("BaseSalary"));
	 			obj.setDepartament(dep);
	 			return obj;*/
	 			Seller obj = instatiateSeller(rs, dep);
	 			return obj;
	 		}
	 		return null;
	 	}
	 	catch(SQLException e) {
	 		throw new DbException(e.getMessage());
	 	}
	 	finally {
	 		DB.closeStatement(st);
	 		DB.closeResultSet(rs);
	 	}
	}

	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
			obj.setId(rs.getInt("Id"));
			obj.setName(rs.getString("Name"));
			obj.setEmail(rs.getString("Email"));
			obj.setBirthDate(rs.getDate("BirthDate"));
			obj.setBaseSalary(rs.getDouble("BaseSalary"));
			obj.setDepartament(dep);
		return obj;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
			dep.setId(rs.getInt("DepartmentId"));
			dep.setName(rs.getString("depName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
			PreparedStatement st = null;
		 	ResultSet rs = null;
		 	try {
		 		st=conn.prepareStatement(
		 				"SELECT seller.*,department.Name as DepName "
		 				+ "FROM seller INNER JOIN department "
		 				+ "ON seller.DepartmentId = department.Id "
		 				+ "ORDER bY Id");

		 		rs = st.executeQuery();
		 		List<Seller> list = new ArrayList<>();
		 		Map<Integer, Department>map = new HashMap<>();
		 		
		 		while(rs.next()) {
		 
		 			Department dep = map.get(rs.getInt("DepartmentId"));
		 			if(dep== null) {
		 				 dep = instanciateDepartment(rs);
		 				 map.put(rs.getInt("DepartmentId"), dep);
		 			}
		 			
		 			
		 			Seller obj = instatiateSeller(rs, dep);
		 			list.add(obj);
		 		}
		 		return list;
		 	}
		 	catch(SQLException e) {
		 		throw new DbException(e.getMessage());
		 	}
		 	finally {
		 		DB.closeStatement(st);
		 		DB.closeResultSet(rs);
		 	}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
	 	ResultSet rs = null;
	 	try {
	 		st=conn.prepareStatement(
	 				"SELECT seller.*,department.Name as DepName "
	 				+ "FROM seller INNER JOIN department "
	 				+ "ON seller.DepartmentId = department.Id "
	 				+ "WHERE DepartmentId = ? "
	 				+ "ORDER bY Id");
	 		st.setInt(1, department.getId());
	 		rs = st.executeQuery();
	 		List<Seller> list = new ArrayList<>();
	 		Map<Integer, Department>map = new HashMap<>();
	 		
	 		while(rs.next()) {
	 
	 			Department dep = map.get(rs.getInt("DepartmentId"));
	 			if(dep== null) {
	 				 dep = instanciateDepartment(rs);
	 				 map.put(rs.getInt("DepartmentId"), dep);
	 			}
	 			
	 			
	 			Seller obj = instatiateSeller(rs, dep);
	 			list.add(obj);
	 		}
	 		return list;
	 	}
	 	catch(SQLException e) {
	 		throw new DbException(e.getMessage());
	 	}
	 	finally {
	 		DB.closeStatement(st);
	 		DB.closeResultSet(rs);
	 	}
	}



}
