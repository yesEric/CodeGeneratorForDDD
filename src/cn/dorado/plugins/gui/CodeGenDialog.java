package cn.dorado.plugins.gui;

import cn.dorado.plugins.components.GeneratorProperties;
import cn.dorado.plugins.components.SelectTargetClassPanelDataSource;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * Created by Eric on 2014-10-21.
 */
public class CodeGenDialog extends DialogWrapper implements SelectTargetClassPanelDataSource {



    private static final String RECENTS_KEY = "TargetSelectionDialog.RecentsKey";

    private final Project project;
    private final PsiClass matchedClass;
    private final EntitySelectionPanel selectedTargetClassPanel;
    private final List<VirtualFile> candidateSourceRoots;
    private final GeneratorProperties generatorProperties;

    public CodeGenDialog(@NotNull Project project,
                                 @NotNull PsiClass matchedClass,
                                 @NotNull List<VirtualFile> candidateSourceRoots,
                                 @NotNull GeneratorProperties generatorProperties) {
        super(project);
        this.generatorProperties=generatorProperties;
        this.project=project;
        this.matchedClass=matchedClass;
        this.candidateSourceRoots=candidateSourceRoots;
        this.selectedTargetClassPanel=new EntitySelectionPanel(this);
        this.setTitle("Generate DDD Code");
        this.setModal(true);

        this.init();



    }
    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo validationInfo = selectedTargetClassPanel.doValidate();

        if (validationInfo != null) {
            return validationInfo;
        }


        return super.doValidate();
    }

    public EntitySelectionPanel getSelectedTargetClassPanel() {
        return selectedTargetClassPanel;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return selectedTargetClassPanel.getRootPanel();
    }

    @NotNull
    @Override
    public Project getProject() {
        return project;
    }

    @NotNull
    @Override
    public String getRecentsKey() {
        return RECENTS_KEY;
    }

    @NotNull
    @Override
    public String getPackageName() {
        return generatorProperties.getPackageName();
    }

    @NotNull
    @Override
    public PsiClass getMatchedClass() {
        return matchedClass;
    }

    @NotNull
    @Override
    public String getDefaultClassName() {
        return generatorProperties.getClassName();
    }

    @NotNull
    @Override
    public VirtualFile getDefaultRoot() {
        return generatorProperties.getSourceRoot();
    }

    @NotNull
    @Override
    public List<VirtualFile> getCandidateRoots() {
        return this.candidateSourceRoots;
    }

    @Override
    public boolean getDefaultIsExtensible() {
        return generatorProperties.isExtensible();
    }
}
