package ${repositoryPackage};

import java.util.List;

import cn.dorado.domain.GenericDomainRepository;
import ${entityPackage}.${entityClassName};

/**
 * ${entityClassName}的资源库.
 */
public interface ${repositoryClassName} extends
		GenericDomainRepository<${entityClassName}, String> {

	/**
	 * 根据主键获取一个 ${entityClassName}对象.
	 * @param  ${entityClassName?uncap_first}Id   主键
	 * @return ${entityClassName}对象
	 */
	 ${entityClassName} ${entityClassName?uncap_first}OfId(String  ${entityClassName?uncap_first}Id);

	/**
     * 获取所有的 ${entityClassName}对象.
	 * @return ${entityClassName}对象
	 */
	 List<${entityClassName}> all${entityClassName}s();



}