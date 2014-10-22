package cn.dorado.plugins.generators;

import cn.dorado.plugins.components.GeneratorProperties;
import cn.dorado.plugins.gui.EntitySelectionPanel;
import cn.dorado.plugins.utils.PsiUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014-10-21.
 */
public class CodeGenerator {
    private Configuration configuration;

    Template template = null;
    private PsiClass psiClass;


    private final  EntitySelectionPanel entitySelectionPanel;
    private final Project project;
   private final GeneratorProperties generatorProperties;
    public CodeGenerator(@NotNull PsiClass psiClass,@NotNull EntitySelectionPanel entitySelectionPanel,@NotNull final GeneratorProperties generatorProperties) {
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "ftl"));
       // configuration.setDefaultEncoding("UTF-8");
        this.psiClass = psiClass;
        this.project=psiClass.getProject();
        this.entitySelectionPanel=entitySelectionPanel;
        this.generatorProperties=generatorProperties;



    }
    public void generate(){
        generateRepository();
        generateRepositoryImpl();
        generateAppQuery();
        generateAppQueryImpl();
        generateAppService();
        generateAppServiceImpl();
        generateDTO();
        generateCommand();
        generateHibernateConfig();

    }

    private void generateRepository(){
        System.out.println("Generate Repository Begin....");
        String repositoryClassName=this.entitySelectionPanel.getRepositoryClassNameTextField().getText();
        String repositoryPackage=this.entitySelectionPanel.getRepositoryPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String templateFile="repository.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(repositoryPackage);

        final String fileName = repositoryClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("repositoryClassName", repositoryClassName);
            map.put("repositoryPackage", repositoryPackage);
            map.put("entityPackage", entityPackage);
            map.put("entityClassName", entityClassName);

            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Repository End....");
    }

    private void generateRepositoryImpl(){
        System.out.println("Generate Repository Hibernate Begin....");

        String repositoryClassName=this.entitySelectionPanel.getRepositoryClassNameTextField().getText();
        String repositoryPackage=this.entitySelectionPanel.getRepositoryPackageTextField().getText();
        String repositoryImplClassName=this.entitySelectionPanel.getRepositoryImplTextField().getText();
        String repositoryImplPackage=this.entitySelectionPanel.getRepositoryImplPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String templateFile="repositoryHibernate.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(repositoryImplPackage);

        final String fileName = repositoryImplClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("repositoryClassName", repositoryClassName);
            map.put("repositoryPackage", repositoryPackage);
            map.put("repositoryImplClassName", repositoryImplClassName);
            map.put("repositoryImplPackage", repositoryImplPackage);
            map.put("entityPackage", entityPackage);
            map.put("entityClassName", entityClassName);
            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Repository Hibernate End....");
    }
    private void generateDTO(){
        System.out.println("Generate DTO Begin....");

        String dto=this.entitySelectionPanel.getDtoTextField().getText();
        String dtoPackage=this.entitySelectionPanel.getDtoPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String templateFile="dto.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(dtoPackage);

        final String fileName = dto + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);

            Map map = new HashMap();

            map.put("dto", dto);
            map.put("dtoPackage", dtoPackage);

            map.put("entityPackageName",entityPackage);
            map.put("entityClassName",entityClassName);
            map.put("fields", psiClass.getAllFields());


            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate DTO End....");
    }

    private void generateHibernateConfig(){
        System.out.println("Generate Hibernate Config Begin....");


        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String templateFile="hibernateConfig.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectoryForResource("");

        final String fileName = entityClassName + ".hbm.xml";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);

            Map map = new HashMap();
            map.put("entityPackageName",entityPackage);
            map.put("entityClassName",entityClassName);
            map.put("fields", psiClass.getAllFields());


            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate  Hibernate Config  End....");
    }
    private void generateAppQuery(){
        System.out.println("Generate Application Query Service Begin....");

        String dto=this.entitySelectionPanel.getDtoTextField().getText();
        String dtoPackage=this.entitySelectionPanel.getDtoPackageTextField().getText();
        String appQueryClassName=this.entitySelectionPanel.getAppQueryTextField().getText();
        String appPackage=this.entitySelectionPanel.getAppPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String templateFile="appQueryService.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(appPackage);

        final String fileName = appQueryClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("dto", dto);
            map.put("dtoPackage", dtoPackage);
            map.put("appQueryClassName", appQueryClassName);
            map.put("appPackage", appPackage);
            map.put("entityClassName", entityClassName);
            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Application Query Service End....");
    }

    private void generateCommand(){
        System.out.println("Generate Command Begin....");

        generateSaveCommand();
        generateRemoveCommand();
        ListModel listModel=this.entitySelectionPanel.getMethodList().getModel();

        for(int i=0;i<listModel.getSize();i++){
            String methodName=listModel.getElementAt(i).toString();

            StringBuilder sb = new StringBuilder(methodName);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            methodName = sb.toString();
            generateOtherCommand(methodName);
        }


        System.out.println("Generate Command End....");
    }
    private void generateSaveCommand(){
        System.out.println("Generate Save Command Begin....");


        String commandObjectPackage=this.entitySelectionPanel.getCommandObjectPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String templateFile="saveCommand.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(commandObjectPackage);

        final String fileName = "Save"+entityClassName+"Command.java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);

            Map map = new HashMap();


            map.put("commandObjectPackage", commandObjectPackage);

            map.put("entityClassName",entityClassName);
            map.put("fields", psiClass.getAllFields());


            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Save Command End....");
    }

    private void generateRemoveCommand(){
        System.out.println("Generate Remove Command Begin....");


        String commandObjectPackage=this.entitySelectionPanel.getCommandObjectPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String templateFile="removeCommand.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(commandObjectPackage);

        final String fileName = "Remove"+entityClassName+"Command.java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);

            Map map = new HashMap();


            map.put("commandObjectPackage", commandObjectPackage);

            map.put("entityClassName",entityClassName);



            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Remove Command End....");
    }
    private void generateOtherCommand(String commandName){
        System.out.println("Generate "+commandName+" Command Begin....");


        String commandObjectPackage=this.entitySelectionPanel.getCommandObjectPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String templateFile="otherCommand.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(commandObjectPackage);

        final String fileName = commandName+entityClassName+"Command.java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);

            Map map = new HashMap();


            map.put("commandObjectPackage", commandObjectPackage);
            map.put("entityClassName",entityClassName);

            map.put("commandName",commandName);



            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate "+commandName+" Command End....");
    }
    private void generateAppService(){

        System.out.println("Generate Application Query Service Begin....");

        String dto=this.entitySelectionPanel.getDtoTextField().getText();
        String dtoPackage=this.entitySelectionPanel.getDtoPackageTextField().getText();
        String appServiceClassName=this.entitySelectionPanel.getAppServiceTextField().getText();
        String appPackage=this.entitySelectionPanel.getAppPackageTextField().getText();
        String commandObjectPackage=this.entitySelectionPanel.getCommandObjectPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        ListModel listModel=this.entitySelectionPanel.getMethodList().getModel();
        List methodList=new ArrayList();
        for(int i=0;i<listModel.getSize();i++){
            String methodName=listModel.getElementAt(i).toString();
           methodList.add(methodName);
        }

        String templateFile="appService.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(appPackage);

        final String fileName = appServiceClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("dto", dto);
            map.put("dtoPackage", dtoPackage);
            map.put("appServiceClassName", appServiceClassName);
            map.put("appPackage", appPackage);
            map.put("methodList", methodList);
            map.put("entityClassName",entityClassName);
            map.put("commandObjectPackage",commandObjectPackage);
            map.put("fields",psiClass.getAllFields());
            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Application Query Service End....");
    }
    private void generateAppQueryImpl(){
        System.out.println("Generate Application Query Service Implementation Begin....");

        String dto=this.entitySelectionPanel.getDtoTextField().getText();
        String dtoPackage=this.entitySelectionPanel.getDtoPackageTextField().getText();
        String appQueryClassName=this.entitySelectionPanel.getAppQueryTextField().getText();
        String appPackage=this.entitySelectionPanel.getAppPackageTextField().getText();
        String repositoryClassName=this.entitySelectionPanel.getRepositoryClassNameTextField().getText();
        String repositoryPackage=this.entitySelectionPanel.getRepositoryPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String appQueryImplClassName=this.entitySelectionPanel.getAppQueryImplTextField().getText();
        String appImplPackage=this.entitySelectionPanel.getAppImplPackageTextField().getText();


        String templateFile="appQueryImpl.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(appImplPackage);

        final String fileName = appQueryImplClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("dto", dto);
            map.put("dtoPackage", dtoPackage);
            map.put("appQueryClassName", appQueryClassName);
            map.put("appPackage", appPackage);
            map.put("repositoryClassName",repositoryClassName);
            map.put("repositoryPackage",repositoryPackage);
            map.put("entityClassName", entityClassName);
            map.put("entityPackage",entityPackage);
            map.put("appQueryImplClassName",appQueryImplClassName);
            map.put("appImplPackage",appImplPackage);
            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        System.out.println("Generate Application Query Service Implementation End....");
    }



    private void generateAppServiceImpl(){
        System.out.println("Generate Application  Service Implementation Begin....");



        String dto=this.entitySelectionPanel.getDtoTextField().getText();
        String dtoPackage=this.entitySelectionPanel.getDtoPackageTextField().getText();
        String appServiceClassName=this.entitySelectionPanel.getAppServiceTextField().getText();
        String appPackage=this.entitySelectionPanel.getAppPackageTextField().getText();
        String commandObjectPackage=this.entitySelectionPanel.getCommandObjectPackageTextField().getText();
        String repositoryClassName=this.entitySelectionPanel.getRepositoryClassNameTextField().getText();
        String repositoryPackage=this.entitySelectionPanel.getRepositoryPackageTextField().getText();
        String entityClassName=this.entitySelectionPanel.getEntityClassTextField().getText();
        String entityPackage=this.entitySelectionPanel.getEntityPackageTextField().getText();
        String appServiceImplClassName=this.entitySelectionPanel.getAppServiceImplTextField().getText();
        String appImplPackage=this.entitySelectionPanel.getAppImplPackageTextField().getText();



        ListModel listModel=this.entitySelectionPanel.getMethodList().getModel();
        List methodList=new ArrayList();
        for(int i=0;i<listModel.getSize();i++){
            String methodName=listModel.getElementAt(i).toString();
            methodList.add(methodName);
        }

        String templateFile="appServiceImpl.ftl";
        final PsiDirectory parentDirectory = findOrCreateParentDirectory(appImplPackage);

        final String fileName = appServiceImplClassName + ".java";
        final PsiFile existingFile = parentDirectory.findFile(fileName);
        if (existingFile != null && !shouldOverwriteFile(existingFile)) {
            // Abort
            return;
        }
        try {
            Template template = configuration.getTemplate(templateFile);
            Map map = new HashMap();
            map.put("dto", dto);
            map.put("dtoPackage", dtoPackage);
            map.put("appServiceClassName", appServiceClassName);
            map.put("appPackage", appPackage);
            map.put("commandObjectPackage",commandObjectPackage);
            map.put("repositoryClassName",repositoryClassName);
            map.put("repositoryPackage",repositoryPackage);
            map.put("entityClassName",entityClassName);
            map.put("entityPackage",entityPackage);
            map.put("appServiceImplClassName",appServiceImplClassName);
            map.put("appImplPackage",appImplPackage);
            map.put("methodList", methodList);
            Writer writer = new FileWriter(new File(parentDirectory.getVirtualFile().getCanonicalPath()+"\\"+fileName));
            template.process(map, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }



        System.out.println("Generate Application  Service Implementation End....");
    }





    @NotNull
    private PsiDirectory findOrCreateParentDirectory(final String packageName) {
        final PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(generatorProperties.getSourceRoot());
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
    private PsiDirectory findOrCreateParentDirectoryForResource(final String packageName) {
        final PsiDirectory baseDir = PsiManager.getInstance(project).findDirectory(generatorProperties.getResourceRoot());
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


    private boolean shouldOverwriteFile(PsiFile existingFile) {
        String msg = "File " + existingFile.getVirtualFile().getPresentableUrl() + " already exists. Overwrite?";
        return Messages.showYesNoDialog(project, msg, "File Already Exists",
                "Overwrite", "Cancel", Messages.getWarningIcon()) == Messages.YES;
    }
}
