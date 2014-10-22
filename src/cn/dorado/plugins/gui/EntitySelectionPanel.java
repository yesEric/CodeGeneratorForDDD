package cn.dorado.plugins.gui;

import cn.dorado.plugins.components.SelectTargetClassPanelDataSource;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Eric on 2014-10-21.
 */
public class EntitySelectionPanel {
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
    private JTextField entityPackageTextField;
    private SelectTargetClassPanelDataSource dataSource;

    public EntitySelectionPanel(@NotNull final SelectTargetClassPanelDataSource dataSource) {
        this.dataSource = dataSource;
        initUI();
    }

    private void initUI() {
        String entityName = dataSource.getDefaultClassName();
        String packageRoot = "cn.dorado.";
        this.entityClassTextField.setText(entityName);
        this.entityPackageTextField.setText(dataSource.getPackageName());
        //Repository
        this.repositoryClassNameTextField.setText(entityName + "Repository");
        this.repositoryPackageTextField.setText(packageRoot + "domain." + entityName.toLowerCase());
        this.repositoryImplTextField.setText(entityName + "RepositoryHibernate");
        this.repositoryImplPackageTextField.setText(packageRoot + "infra.domain.persistence.hibernate");
        this.addCheckBox.setSelected(true);
        this.removeCheckBox.setSelected(true);
        this.allCheckBox.setSelected(true);
        this.getCheckBox.setSelected(true);
        //Application Query
        this.appServiceTextField.setText(entityName + "ApplicationService");
        this.appServiceImplTextField.setText(entityName + "ApplicationServiceimpl");

        this.appQueryTextField.setText(entityName + "QueryService");
        this.appQueryImplTextField.setText(entityName + "QueryServiceImpl");
        this.appGetCheckBox.setSelected(true);

        this.appListCheckBox.setSelected(true);

        //获得实体的方法，并生成CheckBox
        PsiMethod[] psiMethods = dataSource.getMatchedClass().getAllMethods();
        Vector methods = new Vector();
        Vector commandList = new Vector();
        for (PsiMethod psiMethod : psiMethods) {

            String methodName = psiMethod.getName();
            if (isBusinessMethod(methodName)) {
                methods.add(methodName);
                commandList.add(methodName + entityName + "Command");
            }

        }
        methodList.setListData(methods);
        commandObjectList.setListData(commandList);


        this.appPackageTextField.setText(packageRoot + "application." + entityName.toLowerCase());
        this.appImplPackageTextField.setText(packageRoot + "infra.appimpl." + entityName.toLowerCase());
        //DTO
        this.dtoTextField.setText(entityName + "Data");
        this.dtoPackageTextField.setText(packageRoot + "application." + entityName.toLowerCase() + ".representation");
        //Command Object
        this.commandObjectPackageTextField.setText(packageRoot + "application." + entityName.toLowerCase() + ".command");


    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private boolean isBusinessMethod(String methodName) {
        if (methodName.startsWith("set")) {
            return false;
        }
        if (methodName.startsWith("get")) {
            return false;
        }
        List systemMethods = new ArrayList();
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
        if (systemMethods.contains(methodName)) {
            return false;
        }
        return true;
    }

    public JTextField getEntityClassTextField() {
        return entityClassTextField;
    }

    public JTextField getDtoTextField() {
        return dtoTextField;
    }

    public JTextField getDtoPackageTextField() {
        return dtoPackageTextField;
    }

    public JTextField getRepositoryClassNameTextField() {
        return repositoryClassNameTextField;
    }

    public JTextField getRepositoryPackageTextField() {
        return repositoryPackageTextField;
    }

    public JCheckBox getAddCheckBox() {
        return addCheckBox;
    }

    public JCheckBox getRemoveCheckBox() {
        return removeCheckBox;
    }

    public JCheckBox getAllCheckBox() {
        return allCheckBox;
    }

    public JCheckBox getGetCheckBox() {
        return getCheckBox;
    }

    public JTextField getAppQueryTextField() {
        return appQueryTextField;
    }

    public JTextField getAppPackageTextField() {
        return appPackageTextField;
    }

    public JTextField getAppServiceTextField() {
        return appServiceTextField;
    }

    public JCheckBox getAppGetCheckBox() {
        return appGetCheckBox;
    }

    public JPanel getEntityMethodsPanel() {
        return entityMethodsPanel;
    }

    public JTextField getRepositoryImplTextField() {
        return repositoryImplTextField;
    }

    public JTextField getRepositoryImplPackageTextField() {
        return repositoryImplPackageTextField;
    }

    public JTextField getAppQueryImplTextField() {
        return appQueryImplTextField;
    }

    public JTextField getAppImplPackageTextField() {
        return appImplPackageTextField;
    }

    public JTextField getAppServiceImplTextField() {
        return appServiceImplTextField;
    }

    public JList getMethodList() {
        return methodList;
    }

    public JList getCommandObjectList() {
        return commandObjectList;
    }

    public JTextField getCommandObjectPackageTextField() {
        return commandObjectPackageTextField;
    }

    public SelectTargetClassPanelDataSource getDataSource() {
        return dataSource;
    }

    public JCheckBox getAppListCheckBox() {
        return appListCheckBox;
    }

    public JTextField getEntityPackageTextField() {
        return entityPackageTextField;
    }

    public ValidationInfo doValidate() {


        String repository=repositoryClassNameTextField.getText();
        if (StringUtil.isEmpty(repository)) {
            return new ValidationInfo("repository name is empty", repositoryClassNameTextField);
        }
        String repositoryPackage=repositoryPackageTextField.getText();
        if (StringUtil.isEmpty(repositoryPackage)) {
            return new ValidationInfo("repositoryPackage name is empty", repositoryPackageTextField);
        }
        String repositoryImpl=repositoryImplTextField.getText();
        if (StringUtil.isEmpty(repositoryImpl)) {
            return new ValidationInfo("repositoryImpl name is empty", repositoryImplTextField);
        }
        String repositoryImplPackage=repositoryImplPackageTextField.getText();
        if (StringUtil.isEmpty(repositoryImplPackage)) {
            return new ValidationInfo("repositoryImplPackage name is empty", repositoryImplPackageTextField);
        }
        String appQuery=appQueryTextField.getText();
        if (StringUtil.isEmpty(appQuery)) {
            return new ValidationInfo("appQuery name is empty", appQueryTextField);
        }
        String appQueryImpl=appQueryImplTextField.getText();
        if (StringUtil.isEmpty(appQueryImpl)) {
            return new ValidationInfo("appQueryImpl name is empty", appQueryImplTextField);
        }
        String appService=appServiceTextField.getText();
        if (StringUtil.isEmpty(appService)) {
            return new ValidationInfo("appService name is empty", appServiceTextField);
        }
        String appServiceImpl=appServiceImplTextField.getText();
        if (StringUtil.isEmpty(appServiceImpl)) {
            return new ValidationInfo("appServiceImpl name is empty", appServiceImplTextField);
        }
        String appPackage=appPackageTextField.getText();
        if (StringUtil.isEmpty(appPackage)) {
            return new ValidationInfo("appPackage name is empty", appPackageTextField);
        }
        String appImplPackage=appImplPackageTextField.getText();
        if (StringUtil.isEmpty(appImplPackage)) {
            return new ValidationInfo("appImplPackage name is empty", appImplPackageTextField);
        }
        String dto=dtoTextField.getText();
        if (StringUtil.isEmpty(dto)) {
            return new ValidationInfo("dto name is empty", dtoTextField);
        }
        String dtoPackage=dtoPackageTextField.getText();
        if (StringUtil.isEmpty(dtoPackage)) {
            return new ValidationInfo("dtoPackage name is empty", dtoPackageTextField);
        }
        String commandObjectPackage=commandObjectPackageTextField.getText();
        if (StringUtil.isEmpty(commandObjectPackage)) {
            return new ValidationInfo("commandObjectPackage name is empty", commandObjectPackageTextField);
        }

        return null;
    }


}
