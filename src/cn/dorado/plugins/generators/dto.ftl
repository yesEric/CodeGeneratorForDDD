package ${packageName};

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;
import ${entityPackageName}.${className};

/**
 *  ${className?cap_first}对象的DTO类.
 */
public class ${className?cap_first}Data implements Serializable{


<#--生成字段-->
<#list fields as field>
   private ${field.type.presentableText} ${field.name};
</#list>


    public  ${className}Data(){

     }

    public  ${className}Data(final ${className?cap_first}  ${className?uncap_first} ){

        try {
			BeanUtils.copyProperties(this,  ${className?uncap_first});
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
