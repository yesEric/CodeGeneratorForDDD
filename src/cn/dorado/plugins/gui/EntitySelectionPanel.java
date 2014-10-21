package cn.dorado.plugins.gui;

import cn.dorado.plugins.components.SelectTargetClassPanelDataSource;
import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Eric on 2014-10-21.
 */
public class EntitySelectionPanel{
    private JPanel rootPanel;
    private JTextField entityClassTextField;
    private JTextField dtoTextField;
    private JTextField dtoPackageTextField;
    private JButton dtoPackageBtn;
    private JTextField repositoryClassNameTextField;
    private JTextField repositoryPackageTextField;
    private JButton repositoryPackageBtn;
    private JCheckBox addCheckBox;
    private JCheckBox removeCheckBox;
    private JCheckBox allCheckBox;
    private JCheckBox getCheckBox;
    private JTextField appQueryTextField;
    private JTextField appPackageTextField;
    private JButton appPackageBtn;
    private JTextField appServiceTextField;
    private JCheckBox appGetCheckBox;
    private JCheckBox appListCheckBox;
    private JPanel entityMethodsPanel;
    private JTextField repositoryImplTextField;
    private JTextField repositoryImplPackageTextField;
    private JButton repositoryImplPackageBtn;
    private JTextField appQueryImplTextField;
    private JTextField appImplPackageTextField;
    private JTextField appServiceImplTextField;
    private JButton appImplPackageBtn;
    private JList methodList;
    private JList commandObjectList;
    private JTextField commandObjectPackageTextField;
    private JButton commandObjectPackageBtn;
    private  SelectTargetClassPanelDataSource dataSource ;
    public EntitySelectionPanel(@NotNull final SelectTargetClassPanelDataSource dataSource) {
        this.dataSource=dataSource;
       initUI();
    }
   private void initUI(){
       String entityName=dataSource.getDefaultClassName();
       String packageRoot="cn.dorado.";
       this.entityClassTextField.setText(entityName);
       //Repository
       this.repositoryClassNameTextField.setText(entityName+"Repository");
       this.repositoryPackageTextField.setText(packageRoot+"domain."+entityName.toLowerCase());
       this.repositoryImplTextField.setText(entityName+"RepositoryHibernate");
       this.repositoryImplPackageTextField.setText(packageRoot+"infra.domain.persistence.hibernate");
       this.addCheckBox.setSelected(true);
       this.removeCheckBox.setSelected(true);
       this.allCheckBox.setSelected(true);
       this.getCheckBox.setSelected(true);
       //Application Query
       this.appServiceTextField.setText(entityName+"ApplicationService");
       this.appServiceImplTextField.setText(entityName+"ApplicationServiceimpl");

       this.appQueryTextField.setText(entityName+"QueryService");
       this.appQueryImplTextField.setText(entityName+"QueryServiceImpl");
       this.appGetCheckBox.setSelected(true);

       this.appListCheckBox.setSelected(true);

       //获得实体的方法，并生成CheckBox
       PsiMethod[] psiMethods=dataSource.getMatchedClass().getAllMethods();
       Vector methods=new Vector();
       Vector commandList=new Vector();
       for(PsiMethod psiMethod:psiMethods){

           String methodName=psiMethod.getName();
           if(isBusinessMethod(methodName)){
           methods.add(methodName);
           commandList.add(methodName+entityName+"Command");
           }

       }
       methodList.setListData(methods);
       commandObjectList.setListData(commandList);



       this.appPackageTextField.setText(packageRoot + "application." + entityName.toLowerCase());
       this.appImplPackageTextField.setText(packageRoot + "infra.appimpl." + entityName.toLowerCase());
       //DTO
       this.dtoTextField.setText(entityName+"Data");
       this.dtoPackageTextField.setText(packageRoot+"application."+entityName.toLowerCase()+".representation");
       //Command Object
       this.commandObjectPackageTextField.setText(packageRoot+"application."+entityName.toLowerCase()+".command");



   }
    public JPanel getRootPanel() {
        return rootPanel;
    }

    private boolean isBusinessMethod(String methodName){
        if(methodName.startsWith("set")){
            return false;
        }
        if(methodName.startsWith("get")){
            return false;
        }
        List systemMethods=new ArrayList();
        systemMethods.add("Object");
        systemMethods.add("hashCode");
        systemMethods.add("equals");
        systemMethods.add("toString");
        systemMethods.add("clone");
        systemMethods.add("notify");
        systemMethods.add("notifyAll");
        systemMethods.add("wait");
        systemMethods.add("finalize");
        systemMethods.add("registerNatives");
        if(systemMethods.contains(methodName)){
            return false;
        }
        return true;
    }



}
