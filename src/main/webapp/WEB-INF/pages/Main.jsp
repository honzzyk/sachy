<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="<a href='${pageContext.request.contextPath}'>Novinky</a>" />
	<jsp:param name="titulek" value="Novinky" />
</jsp:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${empty novinky}">
	<br>
	<p>Žádné novinky.</p>
</c:if>
<c:forEach var="n" items="${novinky}" varStatus="loop"> 
	<h2>${n.nadpis}</h2>
	${n.text}
	<i style="display:block; text-align: right; color:#383838; font-size: 94%">Přidáno <fmt:formatDate pattern="d. M. Y" value="${n.datum}" /></i>
	<p>
		<c:if test="${muzeEditovat[loop.index] eq true}">

			<p style="text-align:right;">
				<a href="${pageContext.request.contextPath}/admin/novinkaEdit/${n.id}">Upravit novinku</a> | 
				<a href="${pageContext.request.contextPath}/admin/novinkaDelete/${n.id}">Smazat novinku</a>
			</p>
		</c:if>
	</p>  
</c:forEach>  
<p class="center">
	<jsp:include page="parts/strankovani.jsp">
		<jsp:param name="nazevStranky" value="${pageContext.request.contextPath}" />
	</jsp:include>
	
	
	
	
</p>
     
   
   

<jsp:include page="./parts/paticka.jsp" />
























