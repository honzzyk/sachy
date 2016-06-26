<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/diskuze'>Diskuze</a>
	" />
	<jsp:param name="titulek" value="Diskuze" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2>Diskuze</h2>
<p>
	(<a href="#formular">Přidat příspěvek</a>)
</p>

<c:if test="${empty prispevky}">
	<p>Žádné příspěvky. Buďte první!</p>
</c:if>
<c:forEach var="k" items="${prispevky}">
	<div class="komentar">
		<p class="zahlaviKomentare"><c:out value="${k.autor}" />
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			(${k.ip})	| 
			<a href="${pageContext.request.contextPath}/admin/prispevekDelete/${k.id}">Odstranit příspěvek</a>
			</sec:authorize>
			<span class="datumKomentare"><fmt:formatDate pattern="d. M. Y" value="${k.datum}" /></span>
		</p>
		 
		<p><c:out value="${k.text}" escapeXml="false" /></p>
	</div>
</c:forEach>
<p class="center">
	<jsp:include page="parts/strankovani.jsp">
		<jsp:param name="nazevStranky" value="diskuze" />
	</jsp:include>
</p>
<h3>Přidat příspěvek</h3>
<form:form id="formular" commandName="prispevek" action="prispevekAdd#formular" method="POST">
	<table> 
		<tr>
			<td>Autor:</td>
			<td><form:input path="autor" required="required"  /></td>
			<td><form:errors path="autor" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Text:</td>
			<td><form:textarea path="text" rows="9" cols="70" required="required" /></td>
			<td><form:errors path="text" cssClass="error" /></td>
		</tr>
	</table>
	<form:hidden path="id" />
	<input type="submit" value="Vložit příspěvek" />
</form:form>




<jsp:include page="./parts/paticka.jsp" />



