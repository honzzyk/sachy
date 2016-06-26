<jsp:include page="./parts/hlavicka-admin.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<h2 style="margin-top: 0">Vítejte v administračním rozhraní.</h2>
<p>Máte tyto možnosti:</p>

<ul>
	<li><a href="${pageContext.request.contextPath}/admin/novinkaAdd">Přidat novinku</a></li>

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><a href="${pageContext.request.contextPath}/admin/userAdd">Přidat nového uživatele</a></li>
	</sec:authorize>

	<li><a href="${pageContext.request.contextPath}/partieAdd">Přidat novou partii</a></li>
	<li><a href="${pageContext.request.contextPath}/admin/zmenaHesla">Změna hesla</a></li>
</ul> 


<jsp:include page="./parts/paticka-admin.jsp" />
