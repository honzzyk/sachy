<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/moje-partie'>Mnou nahrané partie</a>
	" />
	<jsp:param name="titulek" value="Mnou nahrané partie" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page session="false"%>
<h2>Mnou nahrané partie</h2>

<c:if test="${pageContext.request.userPrincipal.name != null}">
	<ul style="margin-bottom: 1em;">
		<li><a href="partieAdd">Vložit novou partii</a></li>
	</ul>
</c:if>

<c:if test="${empty partie }">
	<p>Žádné partie nebyly nalezeny.</p>
</c:if>
<c:if test="${not empty partie }">
	<table class="tabulka_partie">
		<thead>
			<tr>
				<th class="text_vlevo">Partie</th>
				<th>Výsledek</th>
				<th>Přidáno</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="p" items="${partie}" varStatus="loopStatus">
				<tr class="${loopStatus.index % 2 == 0 ? 'licha' : 'suda'}">
					<td class="text_vlevo"><a href="${pageContext.request.contextPath}/partie/${p.id}">${p.bily.jmeno} ${p.bily.prijmeni} vs. ${p.cerny.jmeno} ${p.cerny.prijmeni} </a></td>
					<td><c:choose>
							<c:when test="${p.vysledek == 1}"> 1:0 </c:when>
							<c:when test="${p.vysledek == 0}"> &#189;:&#189; </c:when>
							<c:when test="${p.vysledek == -1}"> 0:1 </c:when>
						</c:choose></td>
					<td><fmt:formatDate pattern="d. M. Y" value="${p.datumPridani}" /></td>

					<td> 
						<a href="${pageContext.request.contextPath}/partieEdit/${p.id}"> <img src="${pageContext.request.contextPath}/images/modify.gif" alt="upravit" title="upravit partii" /></a>&nbsp;<a href="${pageContext.request.contextPath}/admin/partieDelete/${p.id}"><img src="${pageContext.request.contextPath}/images/delete.gif" title="odstranit partii" alt="odstranit" /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p class="center">
		<jsp:include page="parts/strankovani.jsp">
			<jsp:param name="nazevStranky" value="moje-partie" />
		</jsp:include>
	</p>
</c:if>

<jsp:include page="./parts/paticka.jsp" />
