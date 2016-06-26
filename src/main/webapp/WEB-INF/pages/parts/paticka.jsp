<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</div>

<c:if test="${zobrazitPravySloupec eq 'true'}">
	<hr class="nezobrazit" />
	<div id="pravy_layout">
		<h2 id="novinky">
			<span class="sipka"> </span>Vyhledávání<span id="strelec"> </span>
		</h2>
	
		<form action="${pageContext.request.contextPath}/hledat" method="GET">
			<p><input id="text" name="text" type="text" required /> <input type="submit" value="Hledat partii" /></p> 
		</form>
	</div>

</c:if>
<hr class="cistic" />
</div>
</div>
<p id="pata">
	&copy; 2014 <a href="./">Šachový oddíl TJ Nová Paka</a>
</p>

</div>
</body>
</html>