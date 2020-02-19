package org.uispec4j;

import org.uispec4j.interception.MainClassAdapter;

public class testGUI extends UISpecTestCase {
    protected void setUp() throws Exception {
        setAdapter(new MainClassAdapter(App.class, new String[0]));
    }

    public void test_close_application() {
        Window window = getMainWindow();

        Button resetButton = window.getButton("reset");
//        MenuItem exitMenu = window.getMenuBar().getMenu("FILE").getSubMenu("Exit");

        resetButton.click();
//        exitMenu.click();
    }
}
