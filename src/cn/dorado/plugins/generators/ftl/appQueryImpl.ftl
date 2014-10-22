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
 * ${appQueryClassName}'s implementation.
 */
@Service("${appQueryClassName?uncap_first}")
public class ${appQueryImplClassName} implements ${appQueryClassName} {
	/**
	 * ${entityPackage}'s Repository..
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
	 * Cover the Entity list to ValueObject list..
	 *
	 * @param ${entityClassName?uncap_first}s
	 *           a collection of  ${entityClassName?uncap_first}
	 * @return covered list.
	 */
	private List<${dto}> transform(final List<${entityClassName}> ${entityClassName?uncap_first}s) {
		List<${dto}> ${entityClassName?uncap_first}DataList = new ArrayList<${dto}>();
		for (${entityClassName} ${entityClassName?uncap_first} : ${entityClassName?uncap_first}s) {
			${entityClassName?uncap_first}DataList.add(new ${dto}(${entityClassName?uncap_first}));
		}
		return ${entityClassName?uncap_first}DataList;
	}


}
