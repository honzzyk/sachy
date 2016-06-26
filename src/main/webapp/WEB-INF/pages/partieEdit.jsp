<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/partie'>Partie</a> &raquo;
	<a href='${pageContext.request.contextPath}/partie/${partie.id}'>${partie.bily.prijmeni} vs. ${partie.cerny.prijmeni}</a> &raquo;
	<a href='${pageContext.request.contextPath}/partieEdit/${partie.id}'>Editace</a>
	" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h2>Editace partie ${partie.bily.prijmeni} vs. ${partie.cerny.prijmeni}</h2>

<form:form commandName="partie" method="POST">
	<form:textarea style="width: 640px; margin-top:20px;" cols="40" rows="10" path="pgn" required="required" />
	<form:errors path="pgn" cssClass="error" />
	

	<p>  
		<label>Veřejná: <form:checkbox path="verejna" />
		</label>
	</p>
	<form:errors path="verejna" cssClass="error" />

	<input type="submit" value="Upravit" />
</form:form>


<jsp:include page="./parts/paticka.jsp" />