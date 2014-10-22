<?xml version="1.0"?>

<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping default-access="field">
    <class name="${entityPackageName}.${entityClassName}" table="${entityClassName?uncap_first}">

        <id name="id" column="id" >

            <generator  class="cn.dorado.infra.domain.persistence.hibernate.UUIDKey"/>
        </id>
    <#--生成字段-->
    <#list fields as field>
     <property name="${field.name}" column="${field.name}"/>
    </#list>

    </class>
</hibernate-mapping>