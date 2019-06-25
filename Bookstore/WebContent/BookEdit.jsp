<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Ứng dụng quản lý kho sách</title>
</head>
<body>
	<center>
		<h1>QUẢN LÝ SÁCH</h1>
		<h2>
			<a href="/Bookstore/new">Thêm mới Sách</a> &nbsp;&nbsp;&nbsp; <a
				href="/Bookstore/list">Danh sách Sách</a>

		</h2>
	</center>
	<hr>
	<div align="center">
		<form action="update" method="post">
			<table border="1" cellpadding="5">
				<caption>
					<h2>  Cập nhật Sách</h2>
                    
				</caption>
				 	<input type="hidden" name="id" value="<c:out value='${book.id}' />" />
				 <tr>
					<th>Tên sách:</th>
					<td><input type="text" name="title" size="45"
						value="<c:out value='${book.title}' />" />
					</td>
				</tr>
				<tr>
					<th>Tác giả:</th>
					<td><input type="text" name="author" size="45"
						value="<c:out value='${book.author}' />" />
					</td>
				</tr>
				<tr>
					<th>Giá bán:</th>
					<td><input type="text" name="price" size="5"
						value="<c:out value='${book.price}' />" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Lưu sửa" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>