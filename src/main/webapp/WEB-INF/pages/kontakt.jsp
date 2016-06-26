<jsp:include page="./parts/hlavicka.jsp">
	<jsp:param name="drobeckova_navigace" value="
	<a href='${pageContext.request.contextPath}/kontakt'>Kontakt</a>
	" />
	<jsp:param name="titulek" value="Kontakt" />
</jsp:include>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>

<h2>Kde nás najdete</h2>

<p>
	DDM Nová Paka U Teplárny 1258 (<a href="http://www.mapy.cz/s/9Ju1" title="DDM na Mapy.cz">odkaz na mapu</a>)
</p>
<ul>
	<li>Od října do května každé pondělí od 15 hod. (šachový kroužek pro děti)</li>
	<li>Od října do května Každý pátek přibližně od 19 hod.</li>
</ul>

<jsp:include page="./parts/paticka.jsp" />
















