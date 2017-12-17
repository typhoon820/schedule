package com.university.schedule.controller;

public class ControllerTransfer {

    private static ControllerTransfer controller = null;
    private AbstractController clazz;

    public static ControllerTransfer getInstance() {
        if (controller == null) {
            controller = new ControllerTransfer();
        }
        return controller;
    }

    private ControllerTransfer() {
    }

    public void setController(AbstractController controller) {
        clazz = controller;
    }

    public AbstractController getController() {
        return clazz;
    }

}
