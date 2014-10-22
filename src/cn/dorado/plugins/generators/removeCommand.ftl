package ${commandObjectPackage};

import java.io.Serializable;


/**
 * 删除${entityClassName}的命令.
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
