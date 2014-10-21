package cn.dorado.plugins.actions;

import cn.dorado.plugins.components.GeneratorProperties;
import cn.dorado.plugins.gui.CodeGenDialog;
import cn.dorado.plugins.utils.PsiUtils;
import cn.dorado.plugins.utils.SourceRootUtils;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.ClassUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.util.List;

/**
 * Created by Eric on 2014-10-21.
 */
public class CodeGenAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        //获得当前的 Project对象
        final Project project=e.getData(CommonDataKeys.PROJECT);
        assert project!=null;
        //获得当前的文件对象
        final PsiFile selectedFile=e.getData(CommonDataKeys.PSI_FILE);
        if(selectedFile==null){
            //如果当前没有选中一个文件，则抛出异常
            Messages.showErrorDialog(project, "No selected file.", "Absent Data");

        }

        //获得当前选中的类对象
        final PsiClass selectedClass=this.getSelectedClass(e);
        if (selectedClass == null) {
            //如果没有选中类，则抛出异常
            Messages.showErrorDialog(project, "No selected class.", "Absent Data");
            return;
        }

        // 获得选中类所在的模块
        // 注意：如果选中的类在一个库中而不是源代码中，则模块可能是Null
        final Module module = e.getData(LangDataKeys.MODULE);
        //定义要生成的类的名称
        String preferredClassName = selectedClass.getName();
        //获得类的包名
        String preferredPackageName = ClassUtil.extractPackageName(selectedClass.getQualifiedName());

        // 获得源代码目录的列表
        List<VirtualFile> candidateSourceRoots = SourceRootUtils.getSourceRoots(project, JavaModuleSourceRootTypes.SOURCES, true);

        if (candidateSourceRoots.isEmpty()) {
            Messages.showErrorDialog(project, "No source roots have been configured. A source root is required as the target location for the generated class.", "No Source Root");
            return;
        }

        // 获得要生成代码的目标源代码路径
        VirtualFile preferredSourceRoot = getPreferredSourceRoot(project, module, selectedFile.getVirtualFile());
        final GeneratorProperties generatorProperties = new GeneratorProperties()
                .setClassName(preferredClassName)
                .setPackageName(preferredPackageName)
                .setSourceRoot(preferredSourceRoot);
        CodeGenDialog codeGenDialog=new CodeGenDialog(project, selectedClass, candidateSourceRoots, generatorProperties);
        codeGenDialog.show();
    }

    @Nullable
    private VirtualFile getPreferredSourceRoot(@NotNull Project project, @Nullable Module module, @Nullable VirtualFile selectedFile) {

        VirtualFile preferredSourceRoot = null;

        VirtualFile currentSourceRoot = selectedFile != null ?
                ProjectRootManager.getInstance(project).getFileIndex().getSourceRootForFile(selectedFile) : null;

        List<VirtualFile> candidateSourceRoots = null;

        if (module != null) {
            List<VirtualFile> validModuleTestSourceRoots = SourceRootUtils.getSourceRoots(module, JavaSourceRootType.TEST_SOURCE, true);
            if (validModuleTestSourceRoots.size() > 0) {
                candidateSourceRoots = validModuleTestSourceRoots;
            } else {
                List<VirtualFile> validModuleSourceRoots = SourceRootUtils.getSourceRoots(module, JavaSourceRootType.SOURCE, true);

                if (validModuleSourceRoots.size() > 0) {
                    candidateSourceRoots = validModuleSourceRoots;
                }
            }
        } else {
            List<VirtualFile> validProjectTestSourceRoots = SourceRootUtils.getSourceRoots(project, JavaSourceRootType.TEST_SOURCE, true);
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
    private PsiClass getSelectedClass(@NotNull AnActionEvent e) {
        final PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        assert file != null;

        final Project project = e.getData(CommonDataKeys.PROJECT);
        assert project != null;

        final DataContext dataContext = e.getDataContext();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        final PsiElement psiElement = PsiUtils.getCurrentElement(dataContext, project, editor);

        PsiClass selectedClass = (psiElement != null) ? PsiUtils.findClassFromElement(psiElement) : null;

        if (selectedClass == null) {
            selectedClass = PsiUtils.getClassFromFile(file);
        }

        return selectedClass;
    }
}
