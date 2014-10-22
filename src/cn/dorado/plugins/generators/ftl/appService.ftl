package ${appPackage};
<#list methodList as methodName>
import ${commandObjectPackage}.${methodName?cap_first}${entityClassName}Command;

</#list>
import ${commandObjectPackage}.Save${entityClassName}Command;
import ${commandObjectPackage}.Remove${entityClassName}Command;
import ${dtoPackage}.${dto};
import cn.dorado.domain.common.exception.DomainValidationException;
import cn.dorado.domain.common.exception.IllegalStatusException;

/**
 * ${entityClassName}'s application service..
 */
public interface ${appServiceClassName} {

    /**
     * save the ${entityClassName} object.
     *
     * @param save${entityClassName}Command a save command object
     * @return saved  ${entityClassName} object.
     * @throws cn.dorado.domain.common.exception.DomainValidationException Domain validation Exception.
     */
    ${dto} save${entityClassName}(Save${entityClassName}Command save${entityClassName}Command)
            throws DomainValidationException;

    /**
     * Remove the ${entityClassName}.
     *
     * @param remove${entityClassName}Command a remove command object.
     * @throws IllegalStatusException IllegalStatusException
     * @throws cn.dorado.domain.common.exception.DomainValidationException Domain validation Exception
     */
    void remove${entityClassName}(Remove${entityClassName}Command remove${entityClassName}Command)
            throws IllegalStatusException;

    <#list methodList as methodName>
    /**
         *
         * @param ${methodName?uncap_first}${entityClassName}Command a command to ${methodName?uncap_first} the ${entityClassName}
         * @return 激活后的渠道对象
         * @throws cn.dorado.domain.common.exception.IllegalStatusException IllegalStatusException
         * @throws cn.dorado.domain.common.exception.DomainValidationException DomainValidationException
         */
        ${dto} ${methodName?uncap_first}${entityClassName}(
                ${methodName?cap_first}${entityClassName}Command ${methodName?uncap_first}${entityClassName}Command)
                throws IllegalStatusException, DomainValidationException;

      </#list>
}
