package cn.dorado.plugins.actions;

import cn.dorado.plugins.generators.WebCodeGenerator;
import cn.dorado.plugins.gui.WebCodeGenDialog;
import cn.dorado.plugins.utils.SourceRootUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.jetbrains.jps.model.java.JavaResourceRootType;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;

/**
 * Created by Eric on 2014-10-22.
 */
public class WebCodeGenAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
//        Messages.showErrorDialog("No selected file.", "Absent Data");

        //获得当前的 Project对象
        final Project project=e.getData(CommonDataKeys.PROJECT);
        assert project!=null;

        // 获得源代码目录的列表
        List<VirtualFile> candidateSourceRoots = SourceRootUtils.getSourceRoots(project, JavaModuleSourceRootTypes.SOURCES, true);

        if (candidateSourceRoots.isEmpty()) {
            Messages.showErrorDialog(project, "No source roots have been configured. A source root is required as the target location for the generated class.", "No Source Root");
            return;
        }
        // 获得选中类所在的模块
        // 注意：如果选中的类在一个库中而不是源代码中，则模块可能是Null
        final Module module = e.getData(LangDataKeys.MODULE);

        // 获得要生成代码的目标源代码路径
        VirtualFile preferredSourceRoot = getPreferredSourceRoot(project, module,null);
        VirtualFile preferredWebRoot = getPreferredWebRoot(project, module,null);

        WebCodeGenDialog webCodeGenDialog=new WebCodeGenDialog(project);
        webCodeGenDialog.show();

        if(webCodeGenDialog.isOK()){
            WebCodeGenerator webCodeGenerator=new WebCodeGenerator(webCodeGenDialog.getWebCodePanel(),project,preferredSourceRoot,preferredWebRoot);
            webCodeGenerator.generate();
        }
        preferredSourceRoot.refresh(true,true);
        preferredWebRoot.refresh(true,true);

    }

    @Nullable
    private VirtualFile getPreferredSourceRoot(@NotNull Project project, @Nullable Module module, @Nullable VirtualFile selectedFile) {

        VirtualFile preferredSourceRoot = null;

        VirtualFile currentSourceRoot = selectedFile != null ?
                ProjectRootManager.getInstance(project).getFileIndex().getSourceRootForFile(selectedFile) : null;

        List<VirtualFile> candidateSourceRoots = null;

        if (module != null) {
            List<VirtualFile> validModuleTestSourceRoots = SourceRootUtils.getSourceRoots(module, JavaSourceRootType.SOURCE, true);
            if (validModuleTestSourceRoots.size() > 0) {
                candidateSourceRoots = validModuleTestSourceRoots;
            } else {
                List<VirtualFile> validModuleSourceRoots = SourceRootUtils.getSourceRoots(module, JavaSourceRootType.SOURCE, true);

                if (validModuleSourceRoots.size() > 0) {
                    candidateSourceRoots = validModuleSourceRoots;
                }
            }
        } else {
            List<VirtualFile> validProjectTestSourceRoots = SourceRootUtils.getSourceRoots(project, JavaSourceRootType.SOURCE, true);
            if (validProjectTestSourceRoots.size() > 0) {
                candidateSourceRoots = validProjectTestSourceRoots;
            } else {
                List<VirtualFile> validProjectSourceRoots = SourceRootUtils.getSourceRoots(project, JavaSourceRootType.SOURCE, true);

                if (validProjectSourceRoots.size() > 0) {
                    candidateSourceRoots = validProjectSourceRoots;
                }
            }
        }

        if (candidateSourceRoots != null) {
            if (currentSourceRoot != null && candidateSourceRoots.contains(currentSourceRoot)) {
                preferredSourceRoot = currentSourceRoot;
            } else {
                preferredSourceRoot = candidateSourceRoots.get(0);
            }
        }

        return preferredSourceRoot;
    }

    @Nullable
    private VirtualFile getPreferredWebRoot(@NotNull Project project, @Nullable Module module, @Nullable VirtualFile selectedFile) {

        VirtualFile preferredSourceRoot = null;

        VirtualFile currentSourceRoot = selectedFile != null ?
                ProjectRootManager.getInstance(project).getFileIndex().getSourceRootForFile(selectedFile) : null;

        List<VirtualFile> candidateSourceRoots = SourceRootUtils.getSourceRoots(module, JavaResourceRootType.TEST_RESOURCE, true);


        if (candidateSourceRoots != null) {
            if (currentSourceRoot != null && candidateSourceRoots.contains(currentSourceRoot)) {
                preferredSourceRoot = currentSourceRoot;
            } else {
                preferredSourceRoot = candidateSourceRoots.get(1);
            }
        }

        return preferredSourceRoot;
    }
}
