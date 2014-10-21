package cn.dorado.plugins.components;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.psi.PsiNameHelper;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Eric on 2014-10-20.
 */
public class SelectedTargetClassPanel {
    private JPanel rootPanel;
    private JTextField dTOClassNameTextField;
    private JTextField dtoPackageNameTextField;
    private JButton selectDtoPackageButton;

    private final SelectTargetClassPanelDataSource dataSource ;
    public SelectedTargetClassPanel(@NotNull final SelectTargetClassPanelDataSource dataSource) {
      this.dataSource=dataSource;
        initClassNameFile();

        selectDtoPackageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PackageChooserDialog packageChooserDialog=new PackageChooserDialog("",dataSource.getProject());
                packageChooserDialog.show();
                if(packageChooserDialog.isOK()){
                    dtoPackageNameTextField.setText(packageChooserDialog.getSelectedPackage().getQualifiedName());
                }

            }
        });
    }

    public String getClassName(){
        return this.dTOClassNameTextField.getText();
    }
    public String getPackageName(){
        return this.dtoPackageNameTextField.getText();
    }
    private void initClassNameFile(){
        dTOClassNameTextField.setText(dataSource.getDefaultClassName());
    }

    public ValidationInfo doValidate() {
        String className = StringUtil.trimLeading(StringUtil.trimTrailing(dTOClassNameTextField.getText()));
        String packageName = StringUtil.trimLeading(StringUtil.trimTrailing(dtoPackageNameTextField.getText()));
        if (StringUtil.isEmpty(className)) {
            return new ValidationInfo("Class name is empty", dTOClassNameTextField);
        }
        if (StringUtil.isEmpty(packageName)) {
            return new ValidationInfo("Package name is empty", dTOClassNameTextField);
        }

        if (! PsiNameHelper.getInstance(dataSource.getProject()).isIdentifier(className)) {
            return new ValidationInfo("Class name is not a valid identifier", dTOClassNameTextField);
        }

        return null;
    }



    public JPanel getRootPanel() {
        return rootPanel;
    }
}
