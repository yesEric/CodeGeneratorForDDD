package ${appPackage};

import java.util.List;

import ${dtoPackage}.${dto};


/**
 * 用于查询 ${entityClassName} 相关信息的应用服务。这里的查询都是只读操作.
 */
public interface ${appQueryClassName} {
	/**
	 * 获得所有渠道的方法.
     *  @return 获得所有的${entityClassName}对象.
	 */
	 List<${dto}> all${entityClassName}s();

	/**
	 * 获取一个${entityClassName}对象的方法，根据ID.
	 *
	 * @param  ${entityClassName?uncap_first}Id
	 *            ${entityClassName}的ID
	 * @return ${entityClassName}对象
	 */
	 ${dto}  ${entityClassName?uncap_first}OfId(String  ${entityClassName?uncap_first}Id);



}
