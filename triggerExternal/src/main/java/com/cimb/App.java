package com.cimb;

import org.uispec4j.*;
import org.uispec4j.MenuItem;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.toolkit.UISpecDisplay;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.uispec4j.UISpecTestCase.ADAPTER_CLASS_PROPERTY;

/**
 * Hello world!
 *
 */
public class App 
{
    private static UISpecAdapter adapter;

    public static void main( String[] args )
    {
        UISpec4J.init();

        UISpecDisplay.instance().reset();

        URL url = null;
        try {
            url = new URL(" file:D:/workspace/development/RPA/testSwing.jar");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JarClassLoader loader = new JarClassLoader(url);
        String main = null;
        try {
            main = loader.getMainClassName();
            Class<?> clazz = loader.loadClass(main);
            adapter = new MainClassAdapter(clazz, new String[0]);

            Window window = getMainWindow();

//            Desktop desktop = window.getDesktop();
//            Window[] windows = desktop.getWindows();

//            Component[] components = window.getSwingComponents(JButton.class, "Send");  // This works. Means can search by text on the button
//            TextBox textBox = window.getInputTextBox();
            Component[] components = window.getSwingComponents(JTextField.class);   // find by type, but without name, cannot precisely locate a UI item
            JTextField textField = (JTextField)components[0];
            textField.setText("This is some test text.");

//            for (Component component : components) {
//                UIComponent uiComponent = (UIComponent)component;
//                System.out.println(uiComponent.getName());
//            }
            MenuItem exitMenu = window.getMenuBar().getMenu("FILE").getSubMenu("Exit");
            exitMenu.click();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = null;
        UISpecDisplay.instance().rethrowIfNeeded();
        UISpecDisplay.instance().reset();
    }

    private static void retrieveAdapter() throws UISpecTestCase.AdapterNotFoundException {
        String adapterClassName = System.getProperty(ADAPTER_CLASS_PROPERTY);
        if (adapterClassName == null) {
            throw new UISpecTestCase.AdapterNotFoundException();
        }
        try {
            adapter = (UISpecAdapter)Class.forName(adapterClassName).newInstance();
        }
        catch (Exception e) {
            throw new UISpecTestCase.AdapterNotFoundException(adapterClassName, e);
        }
    }

    private static UISpecAdapter getAdapter() throws UISpecTestCase.AdapterNotFoundException {
        if (adapter == null) {
            retrieveAdapter();
        }
        return adapter;
    }

    /**
     * Returns the Window created by the adapter.
     *
     * @throws UISpecTestCase.AdapterNotFoundException if the <code>uispec4j.adapter</code> property does not refer
     *                                  to a valid adapter
     */
    public static Window getMainWindow() throws UISpecTestCase.AdapterNotFoundException {
        return getAdapter().getMainWindow();
    }
}
