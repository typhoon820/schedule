package com.university.schedule.configuration;

import com.AbstractJavaFXApplication;
import com.university.schedule.controller.AbstractController;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class SpringFxmlLoader {
    ApplicationContext context;

    private static SpringFxmlLoader loader = null;

    private SpringFxmlLoader(){
        initContext();
    }

    public static SpringFxmlLoader getLoader(){
        if (loader==null){
            loader = new SpringFxmlLoader();
        }
        return loader;

    }

    private void initContext(){
        context = AbstractJavaFXApplication.getContext();
    }

    public AbstractController getController(String path){
        initContext();
        try(InputStream fxmlStream = AbstractJavaFXApplication.class.getResourceAsStream(path)){
            System.err.println(SpringFxmlLoader.class.getResourceAsStream(path));
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(clazz -> this.context.getBean(clazz));
            loader.load(fxmlStream);
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object load(String url){
        initContext();
        try(InputStream fxmlStream = AbstractJavaFXApplication.class.getResourceAsStream(url)){
            System.err.println(SpringFxmlLoader.class.getResourceAsStream(url));
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(clazz -> this.context.getBean(clazz));
            return loader.load(fxmlStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
