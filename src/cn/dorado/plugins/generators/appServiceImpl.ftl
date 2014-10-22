package ${appImplPackage};

import ${appPackage}.${appServiceClassName};
<#list methodList as methodName>
import ${commandObjectPackage}.${methodName?cap_first}${entityClassName}Command;

</#list>
import ${commandObjectPackage}.Save${entityClassName}Command;
import ${commandObjectPackage}.Remove${entityClassName}Command;

import ${dtoPackage}.${dto};
import cn.dorado.domain.common.model.Status;
import ${repositoryPackage}.${repositoryClassName};
import ${entityPackage}.${entityClassName};
import cn.dorado.domain.common.exception.DomainValidationException;
import cn.dorado.domain.common.exception.IllegalStatusException;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${entityClassName}的应用服务对象实现类.
 */
@Service("${appServiceClassName?uncap_first}")
public class ${appServiceImplClassName} implements ${appServiceClassName} {
	/**
	 * ${entityClassName}对象的资源库.
	 */
	@Autowired
	private ${repositoryClassName} ${repositoryClassName?uncap_first};



	@Override
	public ${dto} save${entityClassName}(Save${entityClassName}Command save${entityClassName}Command)
			throws DomainValidationException {
		//TODO:cover command to entity
		return new ${dto}( ${repositoryClassName?uncap_first}.add(${entityClassName?uncap_first}));
	}


        public void remove${entityClassName}(Remove${entityClassName}Command remove${entityClassName}Command)
                throws IllegalStatusException{
                //TODO: 增加删除逻辑

                ${entityClassName} ${entityClassName?uncap_first} =
                       ${repositoryClassName?uncap_first}.${entityClassName?uncap_first}OfId(remove${entityClassName}Command
                				.getId());
                		if (${entityClassName?uncap_first}.getStatus().equals(Status.DRAFT)) {
                			${repositoryClassName?uncap_first}.remove(${entityClassName?uncap_first});
                		} else {
                			throw new IllegalStatusException("error.status.remove");
                		}

                }




        <#list methodList as methodName>

                @Override
                public ${dto} ${methodName?uncap_first}${entityClassName}(
                        ${methodName?cap_first}${entityClassName}Command ${methodName?uncap_first}${entityClassName}Command)
                        throws IllegalStatusException, DomainValidationException{

                        ${entityClassName} ${entityClassName?uncap_first} =
                                              ${repositoryClassName?uncap_first}.${entityClassName?uncap_first}OfId(remove${entityClassName}Command
                                       				.getId());
                                 		${entityClassName?uncap_first}.${methodName?uncap_first}();

                                 		return new ${dto}( ${repositoryClassName?uncap_first}.add(${entityClassName?uncap_first}));
                        }

              </#list>
}
