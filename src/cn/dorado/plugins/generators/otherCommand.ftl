package ${commandObjectPackage};

import java.io.Serializable;


/**
 * ${commandName?cap_first}${entityClassName}的命令.
 */
public class ${commandName}${entityClassName}Command implements Serializable {
	/**
	 *
	 */
	private String id;

	/**
	 *
	 * @return ID.
	 */
	public final String getId() {
		return id;
	}


	/**
	 *
	 * @param id ID
	 */
	public ${commandName}${entityClassName}Command(final String id) {
		this.id = id;
	}


}
