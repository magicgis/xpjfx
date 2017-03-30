<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>规格管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ck/cSpec/">规格列表</a></li>
		<shiro:hasPermission name="ck:cSpec:edit"><li><a href="${ctx}/ck/cSpec/form">规格添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cSpec" action="${ctx}/ck/cSpec/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<!--	<li><label>主键：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			-->
			<li><label>规格名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>规格名称</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="ck:cSpec:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cSpec">
			<tr>
				<td><a href="${ctx}/ck/cSpec/form?id=${cSpec.id}">
					${cSpec.name}
				</a></td>
				<td>
					<fmt:formatDate value="${cSpec.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cSpec.remarks}
				</td>
				<shiro:hasPermission name="ck:cSpec:edit"><td>
    				<a href="${ctx}/ck/cSpec/form?id=${cSpec.id}">修改</a>
					<a href="${ctx}/ck/cSpec/delete?id=${cSpec.id}" onclick="return confirmx('确认要删除该规格吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>