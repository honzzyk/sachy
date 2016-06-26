<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/partie'>Partie</a> &raquo;
	<a href='${pageContext.request.contextPath}/partie/${partie.id}'>${partie.bily.prijmeni} vs. ${partie.cerny.prijmeni}</a>
	" />
	<jsp:param name="titulek" value="${partie.bily.jmeno} ${partie.bily.prijmeni} vs. ${partie.cerny.jmeno} ${partie.cerny.prijmeni} - Databáze partií " />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pgnyui.js">
	
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/pgnviewer.js">
	
</script>

<script>
	new PgnViewer({
		boardName : "demo",
		pgnString : "<c:out value='${partie.pgn}' />",
		pieceSize : 40

	});
</script>
<h2>${partie.bily.jmeno}
	${partie.bily.prijmeni} vs. ${partie.cerny.jmeno} ${partie.cerny.prijmeni}
	<c:choose>
		<c:when test="${partie.vysledek == 1}"> 1:0 </c:when>
		<c:when test="${partie.vysledek == 0}"> 1/2:1/2 </c:when>
		<c:when test="${partie.vysledek == -1}"> 0:1 </c:when>
	</c:choose>
</h2>

<div class="center">
	<div id="demo-container"></div>
</div>
<div id="demo-moves"></div>
<br>
<c:if test="${muzeEditovat eq 'true'}">
	<p>
		<a href="${pageContext.request.contextPath}/partieEdit/${partie.id}">Upravit partii<span style="text-decoration:underline !important; color: white ;">&nbsp; <img alt="Upravit partii" src="${pageContext.request.contextPath}/images/modify.gif" /></span></a>
		 | 
		<a href="${pageContext.request.contextPath}/admin/partieDelete/${partie.id}">Smazat partii z databáze<span style="text-decoration:underline !important; color: white ;">&nbsp; <img alt="Odstranit partii" src="${pageContext.request.contextPath}/images/delete.gif" /></span></a>
		
	</p>
</c:if>
 
<h3>Komentáře k partii</h3>  
<c:if test="${empty komentare}">
	<p>Zatím partii nikdo neokomentoval. Buďte první!</p>
</c:if>
<c:forEach var="k" items="${komentare}">
	<div class="komentar">
		<p class="zahlaviKomentare"><c:out value='${k.autor}' />
			<sec:authorize access="hasRole('ROLE_ADMIN')">(${k.ip})</sec:authorize>
			<span class="datumKomentare"><fmt:formatDate pattern="d. M. Y" value="${k.datum}" /></span>
		</p>

		<p><c:out value='${k.text}' /></p>
	</div>
</c:forEach>

<h3>Přidat komentář</h3>
<form:errors path="autor" cssClass="error" />
<form:form commandName="komentarPartie" method="POST">
	<table>
		<tr>
			<td>Autor:</td>
			<td><form:input path="autor" required="required" /></td>
			<td><form:errors path="autor" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Text:</td>
			<td><form:textarea path="text" rows="5" cols="35" required="required" /></td>
			<td><form:errors path="text" cssClass="error" /></td>
		</tr>
	</table>
	<form:hidden path="id" />
	<input type="submit" value="Vložit komentář" />
</form:form>

<jsp:include page="./parts/paticka.jsp" />