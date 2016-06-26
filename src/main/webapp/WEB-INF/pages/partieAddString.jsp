<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/partie'>Partie</a> &raquo;
	<a href='${pageContext.request.contextPath}/partieAddString'>Nahrání nové partie</a>
	" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<h2>Nahrání nové partie</h2>
<p style="color: red;">${msg}</p>

<form:form commandName="partie" method="POST">
	<p>Pro vložení nové partie zadejte obsah PGN souboru.</p>
	<form:textarea style="width: 640px; " cols="40" rows="10" path="pgn" required="required" />
	<form:errors path="pgn" cssClass="error" />
	<p>U každé partie můžete zvolit, zda bude veřejná, či ne. Veřejnou uvidí všichni uživatelé, kteří na web přijdou, neveřejnou si budou moci zobrazit pouze zaregistrovaní uživatelé (tzn. pouze hráči TJ Nová Paka).</p>
	<p style="padding-left: 150px">
		<label><input type="radio" name="verejna" id="verejna" value="true" required="required">Veřejná partie</label> <br> <label><input type="radio" name="verejna" id="verejna" value="false" checked="checked">Pouze pro přihlášené uživatele</label>
	</p>
	<p class="center">
		<input type="submit" value="Nahrát partii!" />
	</p>
</form:form>

<jsp:include page="./parts/paticka.jsp" />