package cn.dorado.plugins.generators;

import cn.dorado.plugins.gui.EntitySelectionPanel;
import cn.dorado.plugins.gui.WebCodePanel;
import cn.dorado.plugins.utils.PsiUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by Eric on 2014-10-22.
 */
public class WebCodeGenerator {
    private Configuration configuration;


    final private PsiClass psiClass;
    private final WebCodePanel webCodePanel;
    private final Project project;
    private final  VirtualFile preferredSourceRoot;
    private final  VirtualFile preferredWebRoot;
    public WebCodeGenerator(WebCodePanel webCodePanel, Project project,@NotNull VirtualFile preferredSourceRoot,@NotNull VirtualFile preferredWebRoot) {

        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "ftl"));

        this.psiClass = webCodePanel.getPsiClass();
        this.webCodePanel = webCodePanel;
        this.project = project;
        this.preferredSourceRoot=preferredSourceRoot;
        this.preferredWebRoot=preferredWebRoot;
    }
    public void generate(){
        generateStrutAction();
        generateListPage();
        generateFormPage();

    }
    private void generateListPage(){
        System.out.println("Generate List Page Begin....");

        String entityName=psiClass.getName();

        String templateFile="listPage.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectoryForWeb("WEB-INF/pages/"+entityName.toLowerCase());

        final String fileName = "list.jsp";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("entityName", entityName);
            map.put("fields", psiClass.getAllFields());

            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate List Page End....");
    }
    private void generateFormPage(){
        System.out.println("Generate Form Page Begin....");
        String entityName=psiClass.getName();

        String templateFile="formPage.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectoryForWeb("WEB-INF/pages/"+entityName.toLowerCase());

        final String fileName = "form.jsp";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }

        List methods = new ArrayList();
        for (PsiMethod psiMethod : psiClass.getAllMethods()) {

            String methodName = psiMethod.getName();
            if (isBusinessMethod(methodName)) {
                methods.add(methodName);
            }
        }

        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("entityName", entityName);
            map.put("fields", psiClass.getAllFields());
            map.put("methods", methods);

            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


        System.out.println("Generate Form Page End....");
    }
    private void generateStrutAction(){
        System.out.println("Generate Struts Begin....");

        String entityName=psiClass.getName();
        List methods = new ArrayList();
        for (PsiMethod psiMethod : psiClass.getAllMethods()) {

            String methodName = psiMethod.getName();
            if (isBusinessMethod(methodName)) {
                methods.add(methodName);
            }
        }


        String templateFile="strutsAction.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory("cn.dorado.webapp.action");

        final String fileName = entityName + "Action.java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("entityName", entityName);
            map.put("methods", methods);

            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


        System.out.println("Generate Struts End....");
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

    @NotNull
    private PsiDirectory findOrCreateParentDirectory(final String packageName) {

        final PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(preferredSourceRoot);
        assert (baseDir != null);

        // Ensure that the directory for the target package exists
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            public void run() {
                PsiUtils.createMissingDirectoriesForPackage(baseDir, packageName);
            }
        });

        PsiDirectory directory = PsiUtils.findDirectoryForPackage(baseDir, packageName);
        assert (directory != null);

        return directory;
    }

    @NotNull
    private PsiDirectory findOrCreateParentDirectoryForWeb(final String dirName) {

        final PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(preferredWebRoot);
        assert (baseDir != null);

        // Ensure that the directory for the target package exists
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            public void run() {
                PsiUtils.createMissingDirectoriesForFolder(baseDir, dirName);
            }
        });

        PsiDirectory directory = PsiUtils.findDirectoryForDir(baseDir, dirName);
        assert (directory != null);

        return directory;
    }

    private boolean shouldOverwriteFile(PsiFile existingFile) {
        String msg = "File " + existingFile.getVirtualFile().getPresentableUrl() + " already exists. Overwrite?";
        return Messages.showYesNoDialog(project, msg, "File Already Exists",
                "Overwrite", "Cancel", Messages.getWarningIcon()) == Messages.YES;
    }
}
