<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="menu.${entityName?uncap_first}"/></title>

</head>
<div class="col-lg-12">
    <c:set var="delObject" scope="request"><fmt:message key="${entityName?uncap_first}s.${entityName?uncap_first}"/></c:set>
    <script type="text/javascript">var msgDelConfirm =
            "<fmt:message key="delete.confirm">
            <#noparse>
            <fmt:param value="${delObject}"/></fmt:message>";
            </#noparse>
    </script>
<s:form name="${entityName?uncap_first}Form" method="post" action="save" id="${entityName?uncap_first}form" autocomplete="off"
        cssClass="form-horizontal">
    <s:hidden key="${entityName?uncap_first}.${entityName?uncap_first}Id" id="${entityName?uncap_first}Id"/>

    <s:token/>

    <fieldset>
    <#list fields as field>
        <div class="form-group">
            <label for="${field.name}" class="col-sm-2 control-label"><fmt:message key="${entityName?uncap_first}.${field.name}"/> :</label>
            <div class="col-sm-4">
                <s:textfield cssClass="form-control" key="${entityName?uncap_first}.${field.name}" id="${field.name}"   />
            </div>
        </div>
    </#list>
    </fieldset>
    <div class="form-actions " align="right">

            <dtag:sec action="/${entityName?uncap_first}/save">
        <s:submit type="button" cssClass="btn btn-primary " method="save" key="button.save" >
            <i class="icon-ok icon-white"></i>
            <fmt:message key="button.save"/>
        </s:submit>
            </dtag:sec>



         <dtag:sec action="/${entityName?uncap_first}/delete">
            <s:submit type="button" cssClass="btn btn-danger" method="delete"
                      onclick="return confirmMessage(msgDelConfirm)" key="button.delete" >
                <i class="icon-ok icon-white"></i>
                <fmt:message key="button.delete"/>
            </s:submit>
            </dtag:sec>

       <#list methods as method>

            <dtag:sec action="/${entityName?uncap_first}/${method}">
            <s:submit type="button" cssClass="btn btn-success" method="${method}" key="button.${method}" >
                <i class="icon-ok icon-white"></i>
                <fmt:message key="button.${method}"/>
            </s:submit>
            </dtag:sec>

       </#list>


        <a class="btn btn-default" href="<c:url value="/${entityName?uncap_first}/list"/>">
            <i class="icon-ok"></i> <fmt:message key="button.cancel"/>
        </a>
    </div>
</s:form>
</div>

