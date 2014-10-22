package ${dtoPackage};

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;
import ${entityPackageName}.${entityClassName};

/**
 *  the DTO of the ${entityClassName?cap_first}.
 */
public class ${dto} implements Serializable{


<#--生成字段-->
<#list fields as field>
   private ${field.type.presentableText} ${field.name};
</#list>


    public  ${dto}(){

     }

    public ${dto}(final ${entityClassName}  ${entityClassName?uncap_first} ){

        try {
			BeanUtils.copyProperties(this,  ${entityClassName?uncap_first});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
     }

<#--生成字段的Setter和Getter-->
<#list fields as field>
   public final void set${field.name?cap_first}(final ${field.type.presentableText} ${field.name}){
       this.${field.name}=${field.name};
   }

   public final ${field.type.presentableText} get${field.name?cap_first}(){
       return ${field.name};
   }
</#list>
}
