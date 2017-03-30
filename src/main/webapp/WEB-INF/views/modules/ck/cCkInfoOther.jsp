<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
                rules: {
                    nub: {remote:{
                        url:"${ctx}/ck/cHgoods/checkStockNum",
                        type:"POST",
                        dateType:"json",
                        data:{
                            outId:function(){
                                var outId = $("#houseId").val();
                                if(outId==""){
                                    top.$.jBox.tip("请先选择仓库！","warning");
                                }
                                return outId;
                            },
                            goodsId:function(){
                                var goodsId = $("#goodsId").val()
                                if(goodsId==""){
                                    top.$.jBox.tip("请先选择商品！","warning");
                                }
                                return goodsId;
                            }
                        }}

                    }
                },
                messages: {
                    nub: {remote: "仓库库存不足！"}
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ck/cCkinfo/">出库信息列表</a></li>
		<shiro:hasPermission name="ck:cCkinfo:edit"><li><a href="${ctx}/ck/cCkinfo/library">出库录单</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/ck/cCkinfo/other">其他出库</a></li>
		<shiro:hasPermission name="ck:cCkinfo:edit"><li><a href="${ctx}/ck/cCkinfo/scrapped">报废录单</a></li></shiro:hasPermission>
		<shiro:hasPermission name="ck:cCkinfo:edit"><li><a href="${ctx}/ck/cCkinfo/returnOfGoods">退货录单</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cCkinfo" action="${ctx}/ck/cCkinfo/saveOther" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">仓库：</label>
			<div class="controls">
				<form:select path="house.id" class="required" id="houseId">
					<form:option value="" label="请选择" ></form:option>
					<form:options items="${houseList}" itemLabel="name" itemValue="id" htmlEscape="false"></form:options>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品：</label>
			<div class="controls">
				<form:select path="goods.id" class="required" id="goodsId">
					<form:option value="" label="请选择"></form:option>
					<form:options items="${goodsList}" itemLabel="name" itemValue="id" htmlEscape="false"></form:options>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总金额：</label>
			<div class="controls">
				<form:input path="je" class="required" number="true" maxlength="12" htmlEscape="true"></form:input>
				<span class="help-inline"><font color="red">* 保留两位小数</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="nub" class="required" digits="true" maxlength="11" htmlEscape="true"></form:input>
				<span class="help-inline"><font color="red">* 最小单位计量</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ck:cCkinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>