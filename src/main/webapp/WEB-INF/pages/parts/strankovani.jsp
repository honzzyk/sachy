<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${pocetStran == 2}">
		<c:choose>
			<c:when test="${empty param.page}">
				<b>1</b>
				<span>|</span>
				<a href="${param.nazevStranky}?page=2<c:if test="${not empty param.text}">&text=${param.text}</c:if>">2</a>
			</c:when>
			<c:otherwise>
				<a href="${param.nazevStranky}<c:if test="${not empty param.text}">text=${param.text}</c:if>">1</a>
				<span>|</span>
				<b>2</b>
			</c:otherwise>
		</c:choose> 

	</c:when>
	<c:when test="${pocetStran>2}">
		<c:choose>
			<c:when test="${param.page == 1 || empty param.page}">
				<b>1</b>
			</c:when>
			<c:otherwise>
				<a href="${param.nazevStranky}<c:if test="${not empty param.text}">?text=${param.text}</c:if>">1</a>
			</c:otherwise> 
		</c:choose>
		<span>|</span>
		<c:forEach begin="2" end="${pocetStran-1}" var="i">
			<c:choose>
				<c:when test="${param.page == i}">
					<b>${i}</b>
				</c:when>
				<c:otherwise>
					<a href="${param.nazevStranky}?page=${i}<c:if test="${not empty param.text}">&text=${param.text}</c:if>">${i}</a>
				</c:otherwise>
			</c:choose>
			<span>|</span>
		</c:forEach>
		<c:choose>
			<c:when test="${param.page == pocetStran}">
				<b>${pocetStran}</b>
			</c:when>
			<c:otherwise>
				<a href="${param.nazevStranky}?page=${pocetStran}<c:if test="${not empty param.text}">&text=${param.text}</c:if>">${pocetStran}</a>
			</c:otherwise>
		</c:choose>


	</c:when>
</c:choose>
