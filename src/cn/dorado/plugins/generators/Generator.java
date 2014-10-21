package cn.dorado.plugins.generators;

import cn.dorado.plugins.components.GeneratorProperties;
import cn.dorado.plugins.utils.PsiUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014-10-20.
 */
public class Generator {

    private Configuration configuration;
    private String codeType = "";
    Template template = null;
    private PsiClass psiClass;
    private String packageName;

    private final GeneratorProperties generatorProperties;
    private final Project project;

    public Generator(@NotNull String codeType, @NotNull PsiClass psiClass,@NotNull String packageName,@NotNull final GeneratorProperties generatorProperties) {
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), ""));
        configuration.setDefaultEncoding("UTF-8");

        this.codeType = codeType;
        this.packageName = packageName;
        this.psiClass = psiClass;

        this.project=psiClass.getProject();
        this.generatorProperties=generatorProperties;

        try {
            template = configuration.getTemplate(codeType);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generate() {
        final PsiDirectory parentDirectory = findOrCreateParentDirectory();

        final String fileName = generatorProperties.getClassName() + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }




        Map map = new HashMap();
        map.put("className", psiClass.getName());
        map.put("packageName", packageName);

        map.put("entityPackageName",generatorProperties.getEntityPackageName());
        map.put("fields", psiClass.getAllFields());
        try {


            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


    }
    private boolean shouldOverwriteFile(PsiFile existingFile) {
        String msg = "File " + existingFile.getVirtualFile().getPresentableUrl() + " already exists. Overwrite?";
        return Messages.showYesNoDialog(project, msg, "File Already Exists",
                "Overwrite", "Cancel", Messages.getWarningIcon()) == Messages.YES;
    }
    @NotNull
    private PsiDirectory findOrCreateParentDirectory() {
        final PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(generatorProperties.getSourceRoot());
        assert (baseDir != null);

        // Ensure that the directory for the target package exists
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            public void run() {
                PsiUtils.createMissingDirectoriesForPackage(baseDir, generatorProperties.getPackageName());
            }
        });

        PsiDirectory directory = PsiUtils.findDirectoryForPackage(baseDir, generatorProperties.getPackageName());
        assert (directory != null);

        return directory;
    }


}
