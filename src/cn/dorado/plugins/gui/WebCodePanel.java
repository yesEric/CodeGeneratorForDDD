package cn.dorado.plugins.gui;

import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2014-10-22.
 */
public class WebCodePanel {

    private JPanel rootPanel;
    private JTextField entityClassNameTextField;
    private JButton entitySelectionButton;
    private final Project project;
    private  PsiClass psiClass;


    public WebCodePanel(@NotNull final Project project) {
        this.project=project;
        entitySelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeJavaClassChooserDialog classChooserDialog=new TreeJavaClassChooserDialog("",project);
                classChooserDialog.show();
                psiClass=classChooserDialog.getSelected();
                entityClassNameTextField.setText(psiClass.getName());
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public ValidationInfo doValidate() {

        String entityClassName=entityClassNameTextField.getText();
        if (StringUtil.isEmpty(entityClassName)) {
            return new ValidationInfo("Entity Class name is empty", entityClassNameTextField);
        }

        return null;
    }


}
