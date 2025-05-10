package projekpbo.controllers;

import projekpbo.views.PlayerView;
import projekpbo.helper.DBhelper;
import projekpbo.models.KataModel;
import projekpbo.models.GameState;

import javax.swing.*;
import java.util.*;

public class GameController {
    private PlayerView view;
    private GameState gameState;
    private boolean gameSelesai = false;

    public GameController(PlayerView view) {
        this.view = view;
        muatUlang();
    }

    public void muatUlang() {
        gameSelesai = false;

        try {
            DBhelper db = new DBhelper();
            List<KataModel> daftarKata = db.getAllData();

            String kata;
            if (!daftarKata.isEmpty()) {
                Random rand = new Random();
                KataModel kataTerpilih = daftarKata.get(rand.nextInt(daftarKata.size()));
                kata = kataTerpilih.getKata().toUpperCase();
            } else {
                kata = "DEFAULT";
            }

            gameState = new GameState(kata);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal memuat kata dari database");
            gameState = new GameState("ERROR");
        }

        updateView();
    }

    public void prosesTebak(String tebakan) {
        if (gameSelesai || tebakan.isEmpty()) return;

        tebakan = tebakan.toUpperCase();

        if (gameState.getKataDitebak().contains(tebakan)) {
            JOptionPane.showMessageDialog(view, "Kata sudah pernah ditebak.");
            return;
        }

        boolean benar = gameState.tebakKata(tebakan);

        if (gameState.isMenang(tebakan)) {
            gameSelesai = true;
            updateView();
            JOptionPane.showMessageDialog(view, "Selamat! Kamu berhasil menebak kata!");
        } else if (gameState.isKalah()) {
            gameSelesai = true;
            updateView();
            JOptionPane.showMessageDialog(view, "Kamu kalah! Kata yang benar: " + gameState.getKataAsli());
        } else {
            updateView();
        }
    }

    private void updateView() {
        view.setKataTerbuka(gameState.getKataTerbukaDariHuruf());

        // Menyusun huruf yang ditebak secara abjad
        StringBuilder huruf = new StringBuilder();
        for (char c : gameState.getHurufDitebak()) {
            huruf.append(c).append(" ");
        }

        view.setHurufTebakan(huruf.toString().trim());
        view.setJumlahKesalahan(gameState.getKesempatan());
    }
}
