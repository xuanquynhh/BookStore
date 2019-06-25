package jp.ivs.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ivs.Model.Book;
import jp.ivs.Model.DBUtils;
@WebServlet("/")
public class BookManageServler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Xem URL người ta chọn hành động gì
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getServletPath();
		// tùy thuộc vào hành động là gì, mà gọi HÀM được viết ở ngoài doGet cho gọn
	    try {
            switch (action) 
            {
            case "/detail":		// 
                showBook(request, response);
                break;
            case "/new":		// 
                showNewForm(request, response);
                break;
            case "/insert":
                insertBook(request, response);
                break;
            case "/delete":
                deleteBook(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateBook(request, response);
                break;
            case "/":
            	listBook(request, response);
                break;
            case "/list":
                listBook(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
		
	}
	
	
	// Hàm hiện tất cả các sách trong DB
	private void listBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Lấy về tất cả các sách
		List<Book> listBook =DBUtils.getByAll();  
		
		// Sử dụng chức năng điều hướng
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllBook.jsp");
        request.setAttribute("listBook", listBook);   // Truyền dữ liệu ra trang jsp
        // thực hiện điều hướng
        dispatcher.forward(request, response);
    }
 
	
	private void showBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// Lấy mã sách từ URL
    	int id = Integer.parseInt(request.getParameter("id"));
    	// Từ đó tím sách cần hiện chi tiết 
        Book book2Show = DBUtils.getByID(id);
		
		// Sử dụng chức năng điều hướng
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookDetails.jsp");
        request.setAttribute("book", book2Show);   // Truyền dữ liệu ra trang jsp
        // thực hiện điều hướng
        dispatcher.forward(request, response);
    }
	
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Sử dụng chức năng điều hướng sang trang jssp
    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookAdd.jsp");
        // thực hiện điều hướng
    	dispatcher.forward(request, response);
    }
    
    //  hiện Form cập nhật
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        // Lấy mã sách cần cập nhật từ URL
    	int id = Integer.parseInt(request.getParameter("id"));
    	// Từ đó tím sách cần cập nhật
        Book existingBook = DBUtils.getByID(id);
        
        // Truyền thông tin ngược ra trong JSP để hiện lên form cập nhật
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookEdit.jsp");  // sử dụng chức năng điều hướng
        request.setAttribute("book", existingBook);  // truyền dữ liệu qua jsp
        dispatcher.forward(request, response);    // thực hiện điều hướng URL
 
    }
     // Thêm mới sách 
     private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Lấy thông tin
    	String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
        // Gói lại
        Book newBook = new Book(title, author, price);
        // Chèn vô DB sử dụng dịch vụ insert của lớp DB
        DBUtils.insert(newBook);
        response.sendRedirect("list");
    }
    // Cập nhật sách
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Lấy thông tin cập nhập
    	int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        float price = Float.parseFloat(request.getParameter("price"));
        // Gói lại
        Book bookUpdate = new Book(id, title, author, price);
        // truyền cho dịch vụ update của lớp DBUtils
        DBUtils.update(bookUpdate);
        response.sendRedirect("list");
    }
 
    // Hàm xóa sách
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Lấy mã sách cần xóa từ URL
    	int id = Integer.parseInt(request.getParameter("id"));
    	
    	// Gọi dịch vụ xóa sách của lớp DBUtils hỗ trợ
        DBUtils.delete(id);
        response.sendRedirect("list");
 
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}