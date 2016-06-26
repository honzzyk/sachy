<jsp:include page="./parts/hlavicka-admin.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<script>
	window.addEvent('domready', function() {
		$('text').mooEditable();
	});
</script>

<h2 style="margin-top: 0">
	<c:choose>
		<c:when test="${editace}">
		Editace novinky
	</c:when>
		<c:otherwise>
		Přidat novinku
	</c:otherwise>
	</c:choose>
</h2>
<form:form id="formular" action="${pageContext.request.contextPath}/admin/novinkaAdd" commandName="novinka" method="POST">
	<p>Nadpis novinky:</p>
	<p>
		<form:input path="nadpis" required="required" style="width:40%" />
		<form:errors path="nadpis" cssClass="error" />
	</p>
	<p>Text novinky:</p>
	<p>
		<form:textarea class="mooeditable" path="text" rows="10" cols="55" required="required" style="width:90%" />
		<form:errors path="text" cssClass="error" /> 
	</p>

	<form:hidden path="id" />
	<input type="submit" value='<c:choose><c:when test="${editace}">Upravit novinku</c:when><c:otherwise>Vložit novinku</c:otherwise></c:choose>' />    
</form:form>


<jsp:include page="./parts/paticka-admin.jsp" />

