<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="${entityName?uncap_first}s.${entityName?uncap_first}"/></title>

</head>
<div class="col-sm-10">




    <display:table name="${entityName?uncap_first}s" class="table table-condensed table-striped table-hover" requestURI=""
                   id="${entityName?uncap_first}s" export="true" pagesize="25">

        <display:column property="${entityName?uncap_first}.${entityName}Id" sortable="true" href="edit?from=list" media="html"
                        paramId="id" paramProperty="${entityName?uncap_first}Id" titleKey="${entityName?uncap_first}.${entityName?uncap_first}Id"  />
        <#list fields as field>

           <display:column property="${field.name}" sortable="true" titleKey="${entityName?uncap_first}.${field.name}"/>

        </#list>

        <display:setProperty name="paging.banner.item_name">
        <fmt:message key="${entityName?uncap_first}s.${entityName?uncap_first}"/>
        </display:setProperty>

        <display:setProperty name="export.excel.filename"><fmt:message
                key="${entityName?uncap_first}s.${entityName?uncap_first}"/>.xls</display:setProperty>
        <display:setProperty name="export.csv.filename"><fmt:message key="${entityName?uncap_first}s.${entityName?uncap_first}"/>.csv</display:setProperty>
        <display:setProperty name="export.pdf.filename"><fmt:message key="${entityName?uncap_first}s.${entityName?uncap_first}"/>.pdf</display:setProperty>
    </display:table>

    <div id="actions" class="btn-group">
        <dtag:sec action="/${entityName?uncap_first}/add">
        <a class="btn btn-primary" href="<c:url value='add'/>">
            <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
        </dtag:sec>
    </div>
</div>