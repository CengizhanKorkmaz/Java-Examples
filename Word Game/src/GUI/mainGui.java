/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.SQLException;
import javax.swing.*;

/**
 *
 * @author BaydoganMirac
 */
public class mainGui {
    JFrame mainJF = new JFrame("Kelime Oyunu");
    JPanel mainJP = new JPanel();
    gameGui gg =null;
    welcomeGui wg = null;
    public mainGui() throws SQLException {
        mainJF.setResizable(false);
        mainJF.setSize(1000,650);
        gg = new gameGui();
        wg  = new welcomeGui(gg);
        
        mainJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJF.setVisible(true);
        mainJF.getContentPane().add(mainJP);
        mainJP.setLayout(null);
        mainJP.add(gg.gameP);
        mainJP.add(wg.welcomeJP);
    }

}
