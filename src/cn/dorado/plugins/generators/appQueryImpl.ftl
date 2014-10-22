package ${appImplPackage};

import ${appPackage}.${appQueryClassName};
import ${dtoPackage}.${dto};
import ${repositoryPackage}.${repositoryClassName};
import ${entityPackage}.${entityClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ${appQueryClassName} 的实现类.
 */
@Service("${appQueryClassName?uncap_first}")
public class ${appQueryImplClassName} implements ${appQueryClassName} {
	/**
	 * ${entityPackage} 领域资源库.
	 */
	@Autowired
	private ${repositoryClassName} ${repositoryClassName?uncap_first};



	@Override
	public final List<${dto}> all${entityClassName}s() {
		return transform(${repositoryClassName?uncap_first}.all${entityClassName}s());
	}

	@Override
	public final ${dto} ${entityClassName?uncap_first}OfId(final String  ${entityClassName?uncap_first}Id) {
		return new ${dto}((${repositoryClassName?uncap_first}.${entityClassName?uncap_first}OfId(${entityClassName?uncap_first}Id));
	}



	/**
	 * 转换为值对象集合.
	 *
	 * @param ${entityClassName?uncap_first}s
	 *            ${entityClassName?uncap_first}集合
	 * @return 转换后的值对象集合
	 */
	private List<${dto}> transform(final List<${entityClassName}> ${entityClassName?uncap_first}s) {
		List<${dto}> ${entityClassName?uncap_first}DataList = new ArrayList<${dto}>();
		for (${entityClassName} ${entityClassName?uncap_first} : ${entityClassName?uncap_first}s) {
			${entityClassName?uncap_first}DataList.add(new ${dto}(${entityClassName?uncap_first}));
		}
		return ${entityClassName?uncap_first}DataList;
	}


}
