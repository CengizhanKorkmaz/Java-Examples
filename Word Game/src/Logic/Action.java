/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import GUI.gameGui;
import java.awt.Color;
import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author BaydoganMirac
 */
public class Action {
    static Timer timer = new Timer();
    static TimerTask counter = null;
    public static int Flag;
    static boolean  running = false;
    int time = 3*60;
    String username;
    public void loginScreen(gameGui gg){
        running = true;
        username = JOptionPane.showInputDialog(null,"Lütfen Kullanıcı Adı Giriniz", "Bilgi Ekranı", JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showConfirmDialog(null, "Merhabalar "+ username, "Hoşgeldiniz", JOptionPane.PLAIN_MESSAGE);
        gg.userName.setText("Hoşgeldin "+username);
        counter = new TimerTask(){
            
           @Override
           public void run(){
               if(running == true){
               time -= 1;
               String a = Integer.toString(time);
               gg.counter.setText(a);
              if(time > 120){
                gg.counter.setForeground(Color.GREEN);
              }else if(120 >= time && time >= 50){
                gg.counter.setForeground(Color.ORANGE);
              }else{
                gg.counter.setForeground(Color.RED);   
              }
              if(time == 0){
                  timer.cancel();
              }
             }
           }
        };
        timer.schedule(counter,0,1000);
    }
    // Giriş Ekranından Oyun Ekranına Geçiş
    public void changeScreen(JPanel a, JPanel b){
        a.setVisible(false);
        b.setVisible(true);
    }
    // Veri Tabanı Bağlantısı
 public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:DB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    /**
     * select all rows in the warehouses table
     */
    public void resetDB(){
        String sql = "Update questions set status=0";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void queryQuestion(gameGui gg){
        gg.index=0;
        String sql = "SELECT * FROM questions WHERE status = 0";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             gg.question.setText(rs.getString("question"));
             String DEFanswer = "";
             int strLen = rs.getString("answer").length();
             for(int i = 0; i< strLen; i++){
                 DEFanswer += " _";
             }
             gg.answer.setText(DEFanswer);
             gg.questionID = rs.getInt("id");
             gg.answerS = rs.getString("answer");
             gg.Qpoint.setText(Integer.toString(rs.getString("answer").length() * 100));
             System.out.println("question id" + gg.questionID);
             
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void isTrue(int id){
        Flag=0;
        running = false;
        String answer = JOptionPane.showInputDialog(null,"Lütfen Cevabı Giriniz");
        System.out.println("Kullanıcının Cevabı " +answer);
        String sql = "SELECT * FROM questions WHERE id ="+id+"";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
            if(answer.equals(rs.getString("answer"))){
                System.out.println(rs.getString("answer"));
                String UpdateSQL = "UPDATE questions SET status=1 WHERE id ="+ id+"";
                Flag=1;
                System.out.println(Flag);
                ResultSet updateResult = stmt.executeQuery(UpdateSQL);
                running = true;
                
            }else{
                JOptionPane.showMessageDialog(null, "Girdiğiniz Cevap Yanlış");
                Flag = 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void takeLetter(int id, gameGui gg, char ch[], int buffer[], int index){
        System.out.println(gg.index);
        String sql = "SELECT * FROM questions WHERE id ="+id+"";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {
             int strLen = rs.getString("answer").length();
            for(int i = 0; i<strLen; i++){
                if(ch[i] != rs.getString("answer").charAt(i)){
                    if(i == buffer[gg.index]){
                        ch[i] = rs.getString("answer").charAt(i);
                    }else{
                        ch[i] = '_';
                    }
                }
            }
            gg.index++;
            String newAnswer = "";
            for(int i = 0; i<strLen; i++){
                newAnswer += " "+ch[i];
            }
            gg.answer.setText(newAnswer);
            
           
        int a = Integer.parseInt(gg.Qpoint.getText());
        a -=100;
        gg.Qpoint.setText(Integer.toString(a));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   public static void shuffleArray(int[] ar)
     {
       // If running on Java 6 or older, use `new Random()` on RHS here
       Random rnd = ThreadLocalRandom.current();
       for (int i = ar.length - 1; i > 0; i--)
       {
         int index = rnd.nextInt(i + 1);
         // Simple swap
         int a = ar[index];
         ar[index] = ar[i];
         ar[i] = a;
       }
     }    
}
