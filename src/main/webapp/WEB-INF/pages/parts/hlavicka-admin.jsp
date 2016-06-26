<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="author" content="Honza Novotný" />
<meta name="keywords" content="Šachy, TJ Nová Paka" />
<meta name="description" content="Šachový oddíl TJ Nová Paka" />

<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default.css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/print.css" media="print" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/MooEditable.css">
<script src="${pageContext.request.contextPath}/scripts/mootools-yui-compressed.js"></script>
<script src="${pageContext.request.contextPath}/scripts/MooEditable.js"></script>


<title>Administrační rozhraní</title>

</head>
<body>
	<div id="horni_lista"></div>
	<!-- Prázdný div kvůli designu - horní lišta -->
	<div id="obal" style="background-image: none !important"> 
	

		<!-- za prázdným spanem je "schován" text pro zobrazení při vypnutých obrázcích  -->
		<hr class="nezobrazit" />
		<!-- pro lepší přehlednost při vypnutí CSS stylů -->

		<p style="color: white; text-align: right; margin-top: 0.8em;">

			<c:choose>
				<c:when test="${pageContext.request.userPrincipal.name != null}">
					Přihlášen: ${pageContext.request.userPrincipal.name} |
					<a style="color: white;" href="<c:url value="/j_spring_security_logout" />">Odhlásit</a>
				</c:when>
				<c:otherwise>
					<a style="color: white;" href="${pageContext.request.contextPath}/login">Přihlásit</a>
				</c:otherwise>
			</c:choose>
		</p>








		<div id="obal_text" style="margin-top: 2em">
			<div id="obal_text2">
				<!-- více divů kvůli designu -->

				<hr class="nezobrazit" />
				<div id="text_stranky">