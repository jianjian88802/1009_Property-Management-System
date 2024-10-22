package com.cissst.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cissst.dao.IInspectionDao;
import com.cissst.entity.Inspection;
import com.cissst.util.DBUtil;

public class InspectionDaoImpl implements IInspectionDao{

	public List<Inspection> getAllInspection() {
		String sql = "select id,person,type,date_format(itime,'%Y-%m-%d') itime,conductor,party,result,memo" +
				" from inspection order by itime";
		Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Inspection> list = new ArrayList<Inspection>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				Inspection i = new Inspection();
				i.setId(rs.getInt("id"));
				i.setPerson(rs.getString("person"));
				i.setType(rs.getString("type"));
				i.setItime(rs.getString("itime"));
				i.setConductor(rs.getString("conductor"));
				i.setParty(rs.getString("party"));
				i.setResult(rs.getString("result"));
				i.setMemo(rs.getString("memo"));
				list.add(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	public void save(Inspection i) {
		
		String sql = "insert into inspection(person,type,itime,conductor,party,result,memo) " +
				"values(?,?,str_to_date(?,'%Y-%m-%d'),?,?,?,?) ";
		Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, i.getPerson());
			stmt.setString(2, i.getType());
			stmt.setString(3, i.getItime());
			stmt.setString(4, i.getConductor());
			stmt.setString(5, i.getParty());
			stmt.setString(6, i.getResult());
			stmt.setString(7, i.getMemo());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public Inspection getInspectionById(String id) {
		String sql = "select id,person,type,date_format(itime,'yyyy-MM-dd') itime,conductor,party,result,memo " +
				"from inspection where id = ?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Inspection i = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()){
				i = new Inspection();
				i.setId(rs.getInt("id"));
				i.setPerson(rs.getString("person"));
				i.setType(rs.getString("type"));
				i.setItime(rs.getString("itime"));
				i.setConductor(rs.getString("conductor"));
				i.setParty(rs.getString("party"));
				i.setResult(rs.getString("result"));
				i.setMemo(rs.getString("memo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return i;
	}

	public void update(Inspection i) {
		String sql = " update inspection set person = ?,type = ?,itime = str_to_date(?,'%Y-%m-%d'),conductor = ?,party = ?,result = ?,memo = ?" +
				" where id = ? ";
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, i.getPerson());
			stmt.setString(2, i.getType());
			stmt.setString(3, i.getItime());
			stmt.setString(4, i.getConductor());
			stmt.setString(5, i.getParty());
			stmt.setString(6, i.getResult());
			stmt.setString(7, i.getMemo());
			stmt.setInt(8, i.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void delete(String id) {
		String sql ="delete  from inspection where id= ?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
}
