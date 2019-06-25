package jp.ivs.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBUtils {
	private static String jdbcURL="jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf-8";
    private static String jdbcUsername="root";
    private static String jdbcPassword="";
    
    // Hàm này thực hiện kết nối DB và trả về một đối tượng kiểu Connection
    // nó dữ kênh kết nối đến cơ sở dữ liệu
    protected static Connection ConnectDB() throws SQLException {
    	Connection jdbcConnection;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return jdbcConnection;
    }
     
    // Hàm thực hiện thêm một sách, được đóng gói vào trong đối tượng newbook vào bảng book trong DB   
    public static void insert(Book newbook) throws SQLException {
        // Kết nối đên DB
    	Connection dbConnect = ConnectDB();
    	// Chuẩn bị truy vấn SQL
    	String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
          // truyền tham số 
        statement.setString(1, newbook.getTitle());
        statement.setString(2, newbook.getAuthor());
        statement.setFloat(3, newbook.getPrice());
         // thực hiện truy vấn
        statement.executeUpdate();
         // đóng kết nối
        statement.close(); 
        dbConnect.close();
    }
     
    public static List<Book> getByAll() throws SQLException {
        // Tạo biến trong bộ nhớ để lưu dữ liệu lấy được từ DB
    	List<Book> listBook = new ArrayList<>();
        
    	// Kết nối đên DB
    	Connection dbConnect = ConnectDB();
    	// Chuẩn bị truy vấn SQL
        String sql = "SELECT * FROM book";
        Statement statement = dbConnect.createStatement();
        // Thực hiện câu lệnh truy vấn lựa chọn, với kết quả đã cất vào biến bangKetQua
        ResultSet bangKetQua = statement.executeQuery(sql);
        
        // Duyệt bangKetQua
        while (bangKetQua.next())   // khi vẫn còn next được (còn bản ghi)
        {
            int id = bangKetQua.getInt("book_id");  // Lấy mã
            String title = bangKetQua.getString("title");  // Lấy tiêu đề
            String author = bangKetQua.getString("author");  // lấy tác giả
            float price = bangKetQua.getFloat("price");	// lấy giá
            // Gói lại vào trong biến book
            // bằng cách tạo một đối tượng Book mới, với hàm khởi tạo có các tham số
            Book book = new Book(id, title, author, price);
            // Thêm vào danh sách các Book
            listBook.add(book);
        }
        // Xong
        // Đóng các kết nối
        bangKetQua.close();
        statement.close();
        dbConnect.close();
         
        // Trả về danh sách các sách lấy được thông qua tên hàm 
        return listBook;
    }
     
    public static void delete(int idBook) throws SQLException {
    	// Kết nối đên DB
    	Connection dbConnect = ConnectDB();
    	// Chuẩn bị truy vấn SQL
    	String sql = "DELETE FROM book where book_id = ?";
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, idBook);
        // Thực hiện truy vấn 
        statement.executeUpdate();
        // đóng kết nối
        statement.close();
        dbConnect.close();
    }
     
    public static void update(Book bookUpdate) throws SQLException{
    	// Kết nối đên DB
    	Connection dbConnect = ConnectDB();
    	// Chuẩn bị truy vấn SQL
      
    	String sql = "UPDATE book SET title = ?, author = ?, price = ?";
        sql += " WHERE book_id = ?";
             // truyền tham số 
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setString(1, bookUpdate.getTitle());
        statement.setString(2, bookUpdate.getAuthor());
        statement.setFloat(3, bookUpdate.getPrice());
        statement.setInt(4, bookUpdate.getId());
        // Thực hiện truy vấn 
        statement.executeUpdate();
        
        // Đóng kết nối
        statement.close();
        dbConnect.close();
    }
     
    public static Book getByID(int id) throws SQLException {
    	// Kết nối đên DB
    	Connection dbConnect = ConnectDB();
    	// Chuẩn bị truy vấn SQL
        Book book = null;
        String sql = "SELECT * FROM book WHERE book_id = ?";
                 
        PreparedStatement statement = dbConnect.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            float price = resultSet.getFloat("price");
             
            book = new Book(id, title, author, price);
        }
        // ĐÓng kết nối 
        resultSet.close();
        statement.close();
        dbConnect.close(); 
        return book;
    }
}