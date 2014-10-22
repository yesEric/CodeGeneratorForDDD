package ${commandObjectPackage};

import java.io.Serializable;


/**
 * A command to ${commandName?cap_first} the ${entityClassName}.
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
