<jsp:include page="./parts/hlavicka-admin.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<h2 style="margin-top: 0">Přidání uživatele</h2>
<p style="color: red">${msg}</p>

<form:form id="formular" commandName="uzivatel" method="POST">
	<table>
		<tr>
			<td>Jméno:</td>
			<td><form:input path="jmeno" required="required" /> <form:errors path="jmeno" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Heslo:</td>
			<td><form:input type="password" path="heslo" required="required" /> <form:errors path="heslo" cssClass="error" /></td>

		</tr>
	</table>
	<input type="submit" value='Přidat uživatele' />
</form:form>


<jsp:include page="./parts/paticka-admin.jsp" />

