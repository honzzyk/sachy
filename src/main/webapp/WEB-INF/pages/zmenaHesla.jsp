<jsp:include page="./parts/hlavicka-admin.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<h2 style="margin-top: 0">Změna hesla</h2>
<p style="color: red">${msg}</p>

<form action="${pageContext.request.contextPath}/admin/zmenaHesla" method="POST">
	<table>
		<tr>
			<td>Aktuální heslo:</td>
			<td><input type="password" id="aktualni" name="aktualni" required /></td>
		</tr>
		<tr>
			<td>Nové heslo:</td>
			<td><input type="password" id="novy" name="novy" required /></td>
		</tr>
		<tr>
			<td>Nové heslo ještě jednou:</td>
			<td><input type="password" id="novy2" name="novy2" required /></td>  
		</tr>

	</table>
	<input type="submit" value="Změnit heslo">
</form>
 

<jsp:include page="./parts/paticka-admin.jsp" />

