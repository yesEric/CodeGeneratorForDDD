package cn.dorado.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.dorado.application.${entityName?uncap_first}.${entityName}ApplicationService;
import cn.dorado.application.${entityName?uncap_first}.${entityName}QueryService;
<#list methods as method>
import cn.dorado.application.${entityName?uncap_first}.command.${method?cap_first}${entityName}Command;
</#list>
import cn.dorado.application.${entityName?uncap_first}.representation.${entityName}Data;

import cn.dorado.domain.common.exception.DomainValidationException;
import cn.dorado.domain.common.exception.IllegalStatusException;

import com.opensymphony.xwork2.Preparable;

/**
 * A struts action of ${entityName}.
 */
public class  ${entityName}Action extends BaseAction implements Preparable {

    /**
     * Inject ${entityName}QueryService.
     */
    @Autowired
	private ${entityName}QueryService ${entityName?uncap_first}QueryService;

    /**
     * Inject ${entityName}ApplicationService.
     */
	@Autowired
	private ${entityName}ApplicationService ${entityName?uncap_first}ApplicationService;



    /**
     * the DTO of ${entityName} to grasp the data from web page
     */
	private ${entityName}Data ${entityName?uncap_first};
    /**
     * a list of ${entityName} to show the data on the list page..
     */
	private List<${entityName}lData> ${entityName?uncap_first}s;







    /**
     * the ${entityName}'s id which used when get ${entityName}.
     */
	private String id;



	@Override
	public final void prepare() throws Exception {
		//TODO: somethings to do for prepare.

	}

    /**
     * Get ${entityName} object.
     * @return ${entityName} object..
     */
	public final ${entityName}Data get${entityName}() {
		return ${entityName?uncap_first};
	}

    /**
     * Set ${entityName} object..
     * @param a${entityName} ${entityName} object.
     */
	public final void set${entityName}(final ${entityName}Data a${entityName}) {
		this.${entityName?uncap_first} = a${entityName};
	}

    /**
     * Get a list of ${entityName}.
     * @return a list of ${entityName}.
     */
	public final List<${entityName}Data> get${entityName}s() {
		return ${entityName?uncap_first}s;
	}

    /**
     * Set a list of ${entityName}..
     * @param a${entityName}s a list of ${entityName}..
     */
	public final void set${entityName}s(final List<${entityName}Data> a${entityName}s) {
		this.${entityName?uncap_first}s = a${entityName}s;
	}

    /**
     * Get the id of ${entityName}..
     * @return ${entityName}'s id.
     */
	public final String getId() {
		return id;
	}

    /**
     * Set the id of ${entityName}.
     * @param anId ${entityName}'s id.
     */
	public final void setId(final String anId) {
		this.id = anId;
	}

    /**
     * A method to add ${entityName}.
     * @return ${entityName} Form page.
     */
	public final String add() {
		${entityName?uncap_first} = new ${entityName}Data();
		return FORM;
	}

    /**
     * A method to edit ${entityName}.
     * @return ${entityName} Form page.
     */
	public final String edit() {
		${entityName?uncap_first} = ${entityName?uncap_first}QueryService.${entityName?uncap_first}OfId(id);
		return FORM;
	}

    /**
     * A method to save ${entityName}.
     * @return ${entityName} for page.
     */
	public final String save() {
		if (${entityName?uncap_first} == null) {
			return add();
		}
		Save${entityName}Command save${entityName}Command
                = new Save${entityName?uncap_first}Command();
		if (isNotEmpty(${entityName?uncap_first}.get${entityName}Id())) {
			save${entityName}Command.setId(${entityName?uncap_first}.get${entityName}Id());
		}
        //TODO:Setup the save action.
		save${entityName}Command.setTitle(${entityName}.getTitle());


		try {
			${entityName?uncap_first} = ${entityName?uncap_first}ApplicationService.
                    save${entityName}(save${entityName}Command);
			saveMessage(getText("${entityName?uncap_first}.saved"));
		} catch (DomainValidationException dv) {
			saveValidationErrors(dv.getConstraintViolations());
		}
		return FORM;
	}

    /**
     *A Method to remove the ${entityName}.
     * @return List page..
     */
	public final String delete() {
		try {
			${entityName?uncap_first}ApplicationService.
                    remove${entityName}(new Remove${entityName}Command(
                            ${entityName?uncap_first}.get${entityName}Id()));
			List<String> args = new ArrayList<String>();
			//TODO: what's the ${entityName?uncap_first} 's title?
			args.add(${entityName?uncap_first}.getTitle());
			saveMessage(getText("${entityName?uncap_first}.deleted", args));
		} catch (IllegalStatusException e) {
			saveMessage(e.getMessage());
		}

        return list();
	}

   <#list methods as method>

    /**
     * A method to ${method} the ${entityName}.
     * @return  ${entityName} Form page.
     */
	public final String ${method}() {

		try {
			${entityName?uncap_first} = ${entityName?uncap_first}ApplicationService
					.${method}${entityName}(
                            new ${method?cap_first}${entityName}Command(
                                    ${entityName?uncap_first}.get${entityName}Id()));
			saveMessage(getText("${entityName?uncap_first}.${method}"));
		} catch (DomainValidationException dve) {
			saveValidationErrors(dve.getConstraintViolations());
		} catch (IllegalStatusException e) {
			addActionError(e.getMessage());
		}

		return FORM;
	}
  </#list>
    /**
     * Get a list of ${entityName}.
     * @return List page..
     */
	public final String list() {
		${entityName?uncap_first}s = ${entityName?uncap_first}QueryService.all${entityName}s();
		return LIST;
	}

}
