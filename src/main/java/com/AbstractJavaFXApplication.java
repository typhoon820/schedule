package com;


import javafx.application.Application;
import javafx.scene.Parent;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractJavaFXApplication extends Application {
    private static String[] savedArgs;

    private static ConfigurableApplicationContext context;

    protected Parent root;

    public static ApplicationContext getContext(){
        return context;
    }


    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), savedArgs);
        //context.getAutowireCapableBeanFactory().autowireBean(this);

        //  FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        // root = (Parent) loader.load("../views/sample.fxml");
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }


    protected static void launchApp(Class<? extends AbstractJavaFXApplication> appClass, String[] args){
        AbstractJavaFXApplication.savedArgs = args;
        Application.launch(appClass,args);
    }
}
