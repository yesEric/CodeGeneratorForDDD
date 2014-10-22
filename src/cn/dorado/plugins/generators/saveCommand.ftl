package ${commandObjectPackage};

import java.io.Serializable;


/**
 *  初始化一个${entityClassName}的命令.
 */
public class Save${entityClassName}Command implements Serializable{


<#--生成字段-->
<#list fields as field>
   private ${field.type.presentableText} ${field.name};
</#list>


    public Save${entityClassName}Command(){

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
