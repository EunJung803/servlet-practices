package jstlel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class _03Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserVo> list = new ArrayList<>();
		
		UserVo vo1 = new UserVo();
		vo1.setNo(1L);
		vo1.setName("둘리");
		list.add(vo1);
		
		UserVo vo2 = new UserVo();
		vo2.setNo(2L);
		vo2.setName("마이콜");
		list.add(vo2);
		
		UserVo vo3 = new UserVo();
		vo3.setNo(3L);
		vo3.setName("또치");
		list.add(vo3);
	
		UserVo vo4 = new UserVo();
		vo4.setNo(4L);
		vo4.setName("도우너");
		list.add(vo4);
	
		request.setAttribute("list", list);

		String contents = "가\n나\n다\n라";
		request.setAttribute("contents", contents);
		
		request
			.getRequestDispatcher("/WEB-INF/views/03.jsp")
			.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
