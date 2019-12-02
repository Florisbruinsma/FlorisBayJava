<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link href="css/Bootstrap.css" rel="stylesheet" />
    <link href="css/MainPage.css" rel="stylesheet" />
    <title>Item</title>
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
<div class="span9">
<div class="well well-small">
<div class="row-fluid">

    <div class="span5">
        <a href="#"> <img src="assets/img/a.jpg" alt="" style="width:100%"></a><!-- image here -->
    </div>
    <div class="span7">
        <h3>Name of the Item [$140.00]</h3><!-- Name + Price-->
        <hr class="soft"/>
        <!-- Discripton -->
        <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua,
        retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. 
        Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. 
        Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.</p>
    </div>
    <hr class="softn clr"/>

    <ul id="productDetail" class="nav nav-tabs">
        <li class="active"><a href="" data-toggle="tab">Product Details</a></li>
    </ul>
    <div id="myTabContent" class="tab-content tabWrapper">
        <h4>Product Information</h4>
        <table class="table table-striped">
            <tbody>
                <tr class="techSpecRow"><td class="techSpecTD1">Color:</td><td class="techSpecTD2">Black</td></tr>
                <tr class="techSpecRow"><td class="techSpecTD1">Style:</td><td class="techSpecTD2">Apparel,Sports</td></tr>
                <tr class="techSpecRow"><td class="techSpecTD1">Season:</td><td class="techSpecTD2">spring/summer</td></tr>
                <tr class="techSpecRow"><td class="techSpecTD1">Usage:</td><td class="techSpecTD2">fitness</td></tr>
                <tr class="techSpecRow"><td class="techSpecTD1">Sport:</td><td class="techSpecTD2">122855031</td></tr>
                <tr class="techSpecRow"><td class="techSpecTD1">Brand:</td><td class="techSpecTD2">Shock Absorber</td></tr>
            </tbody>
        </table>
    </div>

</div>
</div>
</div>
</div>

</body>
</html>