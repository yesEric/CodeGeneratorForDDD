package cn.dorado.plugins.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 生成Web层代码的类.
 */
public class WebCodeGenDialog extends DialogWrapper {
    private final Project project;
    private final WebCodePanel webCodePanel;

    public WebCodeGenDialog(@NotNull Project project) {
        super(project);
        this.project=project;
        this.webCodePanel=new WebCodePanel(project);

        this.setModal(true);
        this.setTitle("Generate Web layer code");
        this.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return webCodePanel.getRootPanel();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo validationInfo = webCodePanel.doValidate();

        if (validationInfo != null) {
            return validationInfo;
        }


        return super.doValidate();
    }

    public WebCodePanel getWebCodePanel() {
        return webCodePanel;
    }
}
