<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link href="css/MainPage.css" rel="stylesheet" />
    <link href="css/Item.css" rel="stylesheet" />
    <title>Add Item</title>
</head>

<body>
<div class="sidenav">
    <a href="#"><%=session.getAttribute("username") %></a>
    <a href="Logout">Logout</a>
    <a href="YourItems.jsp">Your items</a>
    <a href="AddItem.jsp">Add item</a>
    <a href="Search.jsp">Search</a>
</div>
<div class="new_item">
	<div class="container">
        <form id="contact" action="AddItem" method="post" enctype="multipart/form-data">
            <h3>Add Item</h3>
            <h4>Detail for your item</h4>
            <input type="hidden" name="id" value="<%=session.getAttribute("id") %>">
            <fieldset>
                <input placeholder="Product name" name="product_name" type="text" tabindex="1" required autofocus>
            </fieldset>
            <fieldset>
                <input placeholder="Price" name="price" type="number" min="1" step="any" tabindex="2" required>
            </fieldset>
            <fieldset>
                <input placeholder="Tags" name="tags" type="text" tabindex="3" required>
            </fieldset>
            <fieldset>
                <textarea placeholder="Type the product discription here...." name="discription" tabindex="4" required></textarea>
            </fieldset>
            <fieldset>
            	<label for="image">Upload an image:</label>
				<input type="file" name="image" tabindex="5" />            
			</fieldset>
            <fieldset>
                <button type="submit" name="submit"  >Submit</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>