package ${appPackage};

import java.util.List;

import ${dtoPackage}.${dto};


/**
 * 用于查询 ${entityClassName} 相关信息的应用服务。这里的查询都是只读操作.
 */
public interface ${appQueryClassName} {
	/**
	 *  get all ${entityClassName}s .
     *  @return a list of {entityClassName}.
	 */
	 List<${dto}> all${entityClassName}s();

	/**
	 * get the {entityClassName} based on its ID.
	 *
	 * @param  ${entityClassName?uncap_first}Id
	 *            the id of ${entityClassName}
	 * @return ${entityClassName} object.
	 */
	 ${dto}  ${entityClassName?uncap_first}OfId(String  ${entityClassName?uncap_first}Id);



}
