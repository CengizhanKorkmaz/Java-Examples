/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Logic.Action;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author BaydoganMirac
 */
public class welcomeGui{
    JPanel welcomeJP = new JPanel();
    Action actions = new Action();
    public String userName ;
    ImageIcon Limage = new ImageIcon(new ImageIcon(getClass().getResource("mainBG.png")).getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT));
    ImageIcon Bimage = new ImageIcon(new ImageIcon(getClass().getResource("basla.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT));
    JLabel imageL = new JLabel(Limage);
    JButton login = new JButton(Bimage);
    public welcomeGui(gameGui game) {
        welcomeJP.setBounds(0,0,1000,650);
        login.setOpaque(false);
        login.setContentAreaFilled(false);
        login.setBorder(null);
        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.connect();
                actions.resetDB();
                actions.queryQuestion(game);
                actions.loginScreen(game);
                actions.changeScreen(welcomeJP, game.gameP);
            }           
        });
        welcomeJP.add(imageL);
        welcomeJP.add(login);
        
    }
    
    
}
