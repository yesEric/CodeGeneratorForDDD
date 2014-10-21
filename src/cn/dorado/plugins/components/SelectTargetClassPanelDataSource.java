package cn.dorado.plugins.components;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Eric on 2014-10-20.
 */
public interface SelectTargetClassPanelDataSource {
    @NotNull
    Project getProject();

    @NotNull
    String getRecentsKey();

    @NotNull
    String getPackageName();

    @NotNull
    PsiClass getMatchedClass();

    @NotNull
    String getDefaultClassName();

    @NotNull
    VirtualFile getDefaultRoot();

    @NotNull
    List<VirtualFile> getCandidateRoots();

    boolean getDefaultIsExtensible();
}
