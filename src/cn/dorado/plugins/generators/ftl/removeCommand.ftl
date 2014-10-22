package ${commandObjectPackage};

import java.io.Serializable;


/**
 * a command to remove the ${entityClassName}.
 */
public class Remove${entityClassName}Command implements Serializable {
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
	public Remove${entityClassName}Command(final String id) {
		this.id = id;
	}


}
