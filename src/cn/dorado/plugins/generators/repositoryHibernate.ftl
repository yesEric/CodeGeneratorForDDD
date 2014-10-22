package ${repositoryImplPackage};

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ${repositoryPackage}.${repositoryClassName};
import ${entityPackage}.${entityClassName};
import cn.dorado.infra.domain.persistence.GenericRepositoryHibernate;

/**
 * ${entityClassName} Repository的Hibernate实现.
 */
@Repository("${repositoryClassName?uncap_first}")
public class ${repositoryImplClassName} extends
		GenericRepositoryHibernate<${entityClassName}, String> implements
		${repositoryClassName} {

	/**
	 *
	 */
	private static final long serialVersionUID = 7108352514799947605L;

	/**
	 * 初始构造时确定实体对象.
	 */
	public  ${repositoryImplClassName}() {
		super(${entityClassName}.class);
	}

	@Override
	public final ${entityClassName} ${entityClassName?uncap_first}OfId(final String ${entityClassName?uncap_first}Id) {
		return (${entityClassName}) this.objectOfId(${entityClassName?uncap_first}Id);
	}

	@Override
	public final List<${entityClassName}> all${entityClassName}s() {

		return this.all();
	}



}
