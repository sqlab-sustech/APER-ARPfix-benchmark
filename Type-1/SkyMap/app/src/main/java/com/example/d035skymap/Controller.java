package com.example.d035skymap;

public interface Controller {
    void start();

    /**
     * Stops this controller.
     *
     * <p>Called when the application or activity is inactive.  Controllers that
     * require expensive resources such as sensor readings should release them
     * when this is called.
     */
    void stop();
}
