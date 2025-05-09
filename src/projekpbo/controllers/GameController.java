
package projekpbo.controllers;

import projekpbo.views.PlayerView;
import projekpbo.helper.DBhelper;

import javax.swing.*;
import java.util.*;
import java.sql.*;
import projekpbo.models.KataModel;

public class GameController {
    private PlayerView view;
    private String kataRahasia;
    private StringBuilder kataTerbuka;
    private Set<Character> hurufSudahDitebak;
    private int kesalahan;
    
    public GameController(PlayerView view){
        this.view = view;
        muatUlang();
    }
    
    public void muatUlang(){
        hurufSudahDitebak = new HashSet<>();
        kesalahan= 0;
        
        try {
            DBhelper db = new DBhelper();
            List<KataModel> daftarKata= db.getAllData();
            
            if(!daftarKata.isEmpty()){
                Random rand = new Random();
                KataModel kataTerpilih = daftarKata.get(rand.nextInt(daftarKata.size()));
                kataRahasia = kataTerpilih.getKata().toUpperCase();
                kataTerbuka = new StringBuilder("_".repeat(kataRahasia.length()));
            }else{
                kataRahasia= "Default";
                kataTerbuka= new StringBuilder("_".repeat(kataRahasia.length()));
            }
        } catch (Exception e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(view, "Gagal memuat kata dari database");
           kataRahasia ="ERROR";
           kataTerbuka = new StringBuilder("_".repeat(kataRahasia.length()));
        }
        updateView();
    }
    public void prosesTebak(char huruf){
        huruf=  Character.toUpperCase(huruf);
        
        if (hurufSudahDitebak.contains(huruf)){
            JOptionPane.showMessageDialog(view,"Huruf sudah ditebak");
            return;
        }
        hurufSudahDitebak.add(huruf);
        boolean benar= false;
        
        for(int i = 0; i< kataRahasia.length(); i++){
            if(kataRahasia.charAt(i)== huruf){
                kataTerbuka.setCharAt(i, huruf);
                benar= true;
            }
        }
        if(!benar){
            kesalahan++;
        }
        updateView();
        if(kataTerbuka.toString().equals(kataRahasia)){
            JOptionPane.showMessageDialog(view, "Selamat kamu berhasi menebak kata");
        }else if(kesalahan >=5){
            JOptionPane.showMessageDialog(view, "Kamu kalah! Kata yang benar: " + kataRahasia);
        }
    }
    
    private void updateView(){
        view.setKataTerbuka(kataTerbuka.toString());
        view.setHurufTebakan(hurufSudahDitebak.toString());
        view.setJumlahKesalahan(kesalahan);
    }

}
