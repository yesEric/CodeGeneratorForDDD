package ${repositoryPackage};

import java.util.List;

import cn.dorado.domain.GenericDomainRepository;
import ${entityPackage}.${entityClassName};

/**
 * ${entityClassName}'s repository.
 */
public interface ${repositoryClassName} extends
		GenericDomainRepository<${entityClassName}, String> {

	/**
	 * get ${entityClassName} based on its ID
	 * @param  ${entityClassName?uncap_first}Id   id of ${entityClassName}
	 * @return ${entityClassName} ${entityClassName} Object which load from DB.
	 */
	 ${entityClassName} ${entityClassName?uncap_first}OfId(String  ${entityClassName?uncap_first}Id);

	/**
     * To get all ${entityClassName} object from DB.
	 * @return ${entityClassName} object.
	 */
	 List<${entityClassName}> all${entityClassName}s();



}