<%-- 
    Document   : Login
    Created on : 07/09/2016, 16:00:16
    Author     : Welyngton
--%>

<%@include file="include/header.jsp" %>
<body>
    <div class="wrapper">
      <form class="form-signin" action="LoginController?logar=true" method="POST">       
      <h2 class="form-signin-heading">Coavalieitor</h2>
      <input type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus="" />
      <input type="password" class="form-control" name="password" placeholder="Password" required=""/>      
      <button  class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
    </form>
  </div>    
 <%@include file="include/footer.jsp" %>
