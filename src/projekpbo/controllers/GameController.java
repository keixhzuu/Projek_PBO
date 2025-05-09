
package projekpbo.controllers;

import projekpbo.views.PlayerView;
import projekpbo.helper.DBhelper;

import javax.swing.*;
import java.util.*;

import projekpbo.models.KataModel;

public class GameController {
    private PlayerView view;
    private String kataRahasia;
    private StringBuilder kataTerbuka;
    private Set<Character> hurufSudahDitebak;
    private int kesempatan;
    private boolean gameSelesai = false;

    
    public GameController(PlayerView view){
        this.view = view;
        muatUlang();
    }
    
    public void muatUlang() {
        hurufSudahDitebak = new HashSet<>();
        kesempatan = 5;
        gameSelesai = false;
    
        try {
            DBhelper db = new DBhelper();
            List<KataModel> daftarKata = db.getAllData();

            if (!daftarKata.isEmpty()) {
                Random rand = new Random();
                KataModel kataTerpilih = daftarKata.get(rand.nextInt(daftarKata.size()));
                kataRahasia = kataTerpilih.getKata().toUpperCase();
            } else {
                kataRahasia = "DEFAULT";
            }
            kataTerbuka = new StringBuilder("_".repeat(kataRahasia.length()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal memuat kata dari database");
            kataRahasia = "ERROR";
            kataTerbuka = new StringBuilder("_".repeat(kataRahasia.length()));
        }
        updateView();
    }

    public void prosesTebak(String tebakan) {
        if (gameSelesai) return;

        tebakan = tebakan.toUpperCase();

        // Tambahkan huruf ke set huruf yang ditebak
        for (char c : tebakan.toCharArray()) {
            hurufSudahDitebak.add(c);
        }

        // Jika tebakan tepat
        if (tebakan.equals(kataRahasia)) {
            kataTerbuka = new StringBuilder(kataRahasia);
            gameSelesai = true;
            updateView();
            JOptionPane.showMessageDialog(view, "Selamat kamu berhasil menebak kata!");
            return;
        }

        // Kalau salah, tampilkan huruf yang cocok di posisi yang sama (Wordle-style)
        kataTerbuka = new StringBuilder();
        for (int i = 0; i < kataRahasia.length(); i++) {
            if (i < tebakan.length() && tebakan.charAt(i) == kataRahasia.charAt(i)) {
                kataTerbuka.append(tebakan.charAt(i));
            } else {
                kataTerbuka.append("_");
            }
        }

        // Kurangi kesempatan hanya sekali per tebakan
        kesempatan--;

        if (kesempatan == 0) {
            gameSelesai = true;
            updateView();
            JOptionPane.showMessageDialog(view, "Kamu kalah! Kata yang benar: " + kataRahasia);
            return;
        }

        updateView();
    }   

    private void updateView(){
        view.setKataTerbuka(kataTerbuka.toString());

        // Ubah set huruf ke string terurut
        List<Character> hurufList = new ArrayList<>(hurufSudahDitebak);
        Collections.sort(hurufList);  // urutkan agar rapi

        StringBuilder sb = new StringBuilder();
        for (char c : hurufList) {
            sb.append(c).append(" ");
        }
        view.setHurufTebakan(sb.toString().trim());

        view.setJumlahKesalahan(kesempatan);
    }

}
