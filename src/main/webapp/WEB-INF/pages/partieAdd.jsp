<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/partie'>Partie</a> &raquo;
	<a href='${pageContext.request.contextPath}/partieAdd'>Nahrání nové partie</a>
	" />
</jsp:include>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>

<h2>Nahrání nové partie</h2>
<p style="color: red;">${msg}</p>

<form method="POST" action="partieAdd" enctype="multipart/form-data">
	<p>Pro vložení nové partie vyberte soubor PGN z disku a nahrajte.</p>
	<p class="center">
		<input type="file" name="file" required="required">
	</p>
	<p>Případně můžete partii nahrát zkopírováním obsahu pgn souboru do formuláře <a href="${pageContext.request.contextPath}/partieAddString">zde</a>.</p>
	<p>U každé partie můžete zvolit, zda bude veřejná, či ne. Veřejnou uvidí všichni uživatelé, kteří na web přijdou, neveřejnou si budou moci zobrazit pouze zaregistrovaní uživatelé (tzn. pouze hráči TJ Nová Paka).</p>
	<p style="padding-left: 150px">   
		<label><input type="radio" name="verejna" id="verejna" value="true" required="required">Veřejná partie</label> <br> <label><input type="radio" name="verejna" id="verejna" value="false" checked="checked">Pouze pro přihlášené uživatele</label>
	</p>    
	<p class="center">  
	
		<input type="submit" value="Nahrát partii!" />
	</p>  
</form>


<jsp:include page="./parts/paticka.jsp" />
