<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="author" content="Honza Novotný" />
<meta name="keywords" content="Šachy, TJ Nová Paka" />
<meta name="description" content="Šachový oddíl TJ Nová Paka" />

<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default.css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/print.css" media="print" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/board-min.css" />

<title><c:if test="${param.titulek != 'Novinky' && not empty param.titulek}">${param.titulek} - </c:if> <c:if test="${empty param.titulek}">Databáze partií - </c:if> Šachový oddíl TJ Nová Paka</title>

<!--[if lte IE 6]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/ie6.css" media="screen, projection" /><![endif]-->
<c:if test="${zobrazitPravySloupec eq 'true'}">
	<style type="text/css">
/* TODO odstranen pravy layout*/
#pravy_layout {
	width: 275px !important;
	width: 225px !important;
	display: block !important;
}

#text_stranky {
	width: 529px !important;
}
</style>
</c:if>
</head>
<body>
	<div id="horni_lista"></div>
	<!-- Prázdný div kvůli designu - horní lišta -->
	<div id="obal">
		<h1 id="logo">
			<a href="${pageContext.request.contextPath}/" title="Přejít na úvodní stránku webu">Šachový oddíl TJ Nová Paka <span> </span></a>
		</h1>
		<!-- za prázdným spanem je "schován" text pro zobrazení při vypnutých obrázcích  -->
		<hr class="nezobrazit" />
		<!-- pro lepší přehlednost při vypnutí CSS stylů -->
		<h2 class="nezobrazit">Navigace:</h2>
		<div id="menu">
			<ul>
				<li><a href="${pageContext.request.contextPath}/" <c:if test="${param.titulek eq 'Novinky'}">class="nynejsi_stranka"</c:if> id="prvni_odkaz">Novinky<span> </span></a></li>
				<!-- class="nynejsi_stranka" -->
				<!-- prázdný span kvůli designu - kulaté rohy v menu -->
				<li><a href="${pageContext.request.contextPath}/partie" <c:if test="${empty param.titulek || param.titulek eq 'Výsledky hledání' || fn:contains(param.titulek, 'Databáze')}">class="nynejsi_stranka"</c:if>>Databáze partií</a></li>
				<li><a href="${pageContext.request.contextPath}/diskuze" <c:if test="${param.titulek eq 'Diskuze'}">class="nynejsi_stranka"</c:if>>Diskuze</a></li>
				<li><a href="${pageContext.request.contextPath}/kontakt" id="posledni_odkaz" <c:if test="${param.titulek eq 'Kontakt'}">class="nynejsi_stranka"</c:if>>Kontakt<span> </span></a></li>
			</ul>
		</div>
		<span id="login"> <c:choose>
				<c:when test="${pageContext.request.userPrincipal.name != null}">
					Přihlášen: ${pageContext.request.userPrincipal.name} |
					<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DEFAULT')">
						<a href="${pageContext.request.contextPath}/admin">Admin rozhraní</a> |
					</sec:authorize>
					<a href="<c:url value="/j_spring_security_logout" />">Odhlásit</a>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/login">Přihlásit</a>
				</c:otherwise>
			</c:choose>

		</span>

		<div id="obal_text">
			<div id="obal_text2">
				<!-- více divů kvůli designu -->
				<div id="drobeckova_navigace_obal">
					<div id="drobeckova_navigace">
						<p>Nacházíte se: ${param.drobeckova_navigace}</p>
					</div>
				</div>
				<hr class="nezobrazit" />
				<div id="text_stranky">