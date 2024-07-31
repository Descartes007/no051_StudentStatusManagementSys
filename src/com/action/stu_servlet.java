package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DB;
import com.orm.TAdmin;
import com.orm.Tstu;
import com.service.liuService;

public class stu_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		if(type.endsWith("stuAdd"))
		{
			stuAdd(req, res);
		}
		if(type.endsWith("stuDel"))
		{
			stuDel(req, res);
		}
		if(type.endsWith("stuMana"))
		{
			stuMana(req, res);
		}
		if(type.endsWith("stuEdit"))
		{
			stuEdit(req, res);
		}
		if(type.endsWith("stuDetail"))
		{
			stuDetail(req, res);
		}
		if(type.endsWith("stuAll"))
		{
			stuAll(req, res);
		}
		if(type.endsWith("stuSearchByXuehao"))
		{
			stuSearchByXuehao(req, res);
		}
		if(type.endsWith("stuTongji"))
		{
			stuTongji(req, res);
		}
	}
	
	
	public void stuAdd(HttpServletRequest req,HttpServletResponse res)
	{
		String xuehao=req.getParameter("xuehao");
		String name1=req.getParameter("name1");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		
		int banji_id=Integer.parseInt(req.getParameter("banji_id"));
		String ruxueshijian=req.getParameter("ruxueshijian");
		String biyeshijian=req.getParameter("biyeshijian");
		String xuezhi=req.getParameter("xuezhi");
		
		String xuexiao=req.getParameter("xuexiao");
		String del="no";
		if(liuService.jianchaxuhao(xuehao.trim())==true)
		{
			req.setAttribute("message", "学号重复。请重新输入");
			req.setAttribute("path", "admin/stu/stuAdd.jsp");
			String targetURL = "/common/success.jsp";
			dispatch(targetURL, req, res);
		}
		else
		{
			String sql="insert into t_stu values(?,?,?,?,?,?,?,?,?,?)";
			Object[] params={xuehao,name1,sex,age,banji_id,ruxueshijian,biyeshijian,xuezhi,xuexiao,del};
			DB mydb=new DB();
			mydb.doPstm(sql, params);
			mydb.closed();
			
			req.setAttribute("message", "操作成功");
			req.setAttribute("path", "stu?type=stuMana");
			
	        String targetURL = "/common/success.jsp";
			dispatch(targetURL, req, res);
		}
	}
	
	public void stuDel(HttpServletRequest req,HttpServletResponse res)
	{
		String sql="update t_stu set del='yes' where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "stu?type=stuMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void stuMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setXuehao(rs.getString("xuehao"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setBanji_id(rs.getInt("banji_id"));
				stu.setRuxueshijian(rs.getString("ruxueshijian"));
				stu.setBiyeshijian(rs.getString("biyeshijian"));
				stu.setXuezhi(rs.getString("xuezhi"));
				stu.setXuexiao(rs.getString("xuexiao"));
				stu.setBanji_name(liuService.getBanjiName(rs.getInt("banji_id")));
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		if(stuList.size()==0)
		{
			req.setAttribute("msg", 0);
		}
		else
		{
			req.setAttribute("msg", 1);
		}
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu/stuMana.jsp").forward(req, res);
	}
	
	
	public void stuSearchByXuehao(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no' and xuehao like '%"+req.getParameter("xuehao").trim()+"%'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setXuehao(rs.getString("xuehao"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setBanji_id(rs.getInt("banji_id"));
				stu.setRuxueshijian(rs.getString("ruxueshijian"));
				stu.setBiyeshijian(rs.getString("biyeshijian"));
				stu.setXuezhi(rs.getString("xuezhi"));
				stu.setXuexiao(rs.getString("xuexiao"));
				stu.setBanji_name(liuService.getBanjiName(rs.getInt("banji_id")));
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		if(stuList.size()==0)
		{
			req.setAttribute("msg", 0);
		}
		else
		{
			req.setAttribute("msg", 1);
		}
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu/stuMana.jsp").forward(req, res);
	}
	
	
	public void stuEdit(HttpServletRequest req,HttpServletResponse res)
	{
		String xuehao=req.getParameter("xuehao");
		String name1=req.getParameter("name1");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		int banji_id=Integer.parseInt(req.getParameter("banji_id"));
		String ruxueshijian=req.getParameter("ruxueshijian");
		String biyeshijian=req.getParameter("biyeshijian");
		String xuezhi=req.getParameter("xuezhi");
		String xuexiao=req.getParameter("xuexiao");
		
		String sql="update t_stu set xuehao=?,name1=?,sex=?,age=?,banji_id=?,ruxueshijian=?,biyeshijian=?,xuezhi=?,xuexiao=? where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={xuehao,name1,sex,age,banji_id,ruxueshijian,biyeshijian,xuezhi,xuexiao};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "stu?type=stuMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	public void stuDetail(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		Tstu stu=new Tstu();
		String sql="select * from t_stu where del='no' and id ="+req.getParameter("id").trim();
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setXuehao(rs.getString("xuehao"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setBanji_id(rs.getInt("banji_id"));
				stu.setRuxueshijian(rs.getString("ruxueshijian"));
				stu.setBiyeshijian(rs.getString("biyeshijian"));
				stu.setXuezhi(rs.getString("xuezhi"));
				stu.setXuexiao(rs.getString("xuexiao"));
				stu.setBanji_name(liuService.getBanjiName(rs.getInt("banji_id")));
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("stu", stu);
		req.getRequestDispatcher("admin/stu/stuDetail.jsp").forward(req, res);
	}
	
	
	public void stuAll(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setXuehao(rs.getString("xuehao"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setBanji_id(rs.getInt("banji_id"));
				stu.setRuxueshijian(rs.getString("ruxueshijian"));
				stu.setBanji_name(liuService.getBanjiName(rs.getInt("banji_id")));
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu_xuanke/stuAll.jsp").forward(req, res);
	}
	
	
	public void stuTongji(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		
		
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no' and banji_id=?";
		Object[] params={req.getParameter("banji_id")};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setXuehao(rs.getString("xuehao"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setBanji_id(rs.getInt("banji_id"));
				stu.setRuxueshijian(rs.getString("ruxueshijian"));
				stu.setBiyeshijian(rs.getString("biyeshijian"));
				stu.setXuezhi(rs.getString("xuezhi"));
				stu.setXuexiao(rs.getString("xuexiao"));
				stu.setBanji_name(liuService.getBanjiName(rs.getInt("banji_id")));
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu/stuMana.jsp").forward(req, res);
	}
	
	public void dispatch(String targetURI,HttpServletRequest request,HttpServletResponse response) 
	{
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(targetURI);
		try 
		{
		    dispatch.forward(request, response);
		    return;
		} 
		catch (ServletException e) 
		{
                    e.printStackTrace();
		} 
		catch (IOException e) 
		{
			
		    e.printStackTrace();
		}
	}
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
	
	public void destroy() 
	{
		
	}
}
