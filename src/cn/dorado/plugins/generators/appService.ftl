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
 * ${entityClassName} 的应用服务，执行命令.
 */
public interface ${appServiceClassName} {

    /**
     * 保存渠道信息.
     *
     * @param save${entityClassName}Command 初始化渠道的命令
     * @return 保存后的渠道信息
     * @throws cn.dorado.domain.common.exception.DomainValidationException 模型校验异常
     */
    ${dto} save${entityClassName}(Save${entityClassName}Command save${entityClassName}Command)
            throws DomainValidationException;

    /**
     * 删除实体.
     *
     * @param remove${entityClassName}Command 删除命令
     * @throws IllegalStatusException 非法状态下删除实体抛出的异常
     * @throws cn.dorado.domain.common.exception.DomainValidationException 模型校验异常
     */
    void remove${entityClassName}(Remove${entityClassName}Command remove${entityClassName}Command)
            throws IllegalStatusException;

    <#list methodList as methodName>
    /**
         *
         * @param ${methodName?uncap_first}${entityClassName}Command 激活命令
         * @return 激活后的渠道对象
         * @throws cn.dorado.domain.common.exception.IllegalStatusException 激活渠道时的状态异常
         * @throws cn.dorado.domain.common.exception.DomainValidationException 模型校验异常
         */
        ${dto} ${methodName?uncap_first}${entityClassName}(
                ${methodName?cap_first}${entityClassName}Command ${methodName?uncap_first}${entityClassName}Command)
                throws IllegalStatusException, DomainValidationException;

      </#list>
}
