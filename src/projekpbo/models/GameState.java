package projekpbo.models;

import java.util.*;

public class GameState {
    private String kataAsli;
    private Set<String> kataDitebak;
    private Set<Character> hurufDitebak;
    private int kesempatan;
    private final int maxKesempatan = 5;

    public GameState(String kataAsli) {
        this.kataAsli = kataAsli.toUpperCase();
        this.kataDitebak = new HashSet<>();
        this.hurufDitebak = new TreeSet<>(); // Urut otomatis abjad
        this.kesempatan = maxKesempatan;
    }

    public boolean tebakKata(String tebakan) {
        tebakan = tebakan.toUpperCase();

        if (kataDitebak.contains(tebakan)) {
            return false;
        }

        kataDitebak.add(tebakan);

        // Tambah semua huruf dari kata ke hurufDitebak
        for (char c : tebakan.toCharArray()) {
            if (Character.isLetter(c)) {
                hurufDitebak.add(c);
            }
        }

        if (!tebakan.equals(kataAsli)) {
            kesempatan--;
        }

        return tebakan.equals(kataAsli);
    }

    public String getKataTerbukaDariHuruf() {
        StringBuilder sb = new StringBuilder();
        for (char c : kataAsli.toCharArray()) {
            if (hurufDitebak.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    public boolean isMenang(String tebakan) {
        return tebakan.equalsIgnoreCase(kataAsli);
    }

    public boolean isKalah() {
        return kesempatan <= 0;
    }

    public int getKesempatan() {
        return kesempatan;
    }

    public Set<Character> getHurufDitebak() {
        return hurufDitebak;
    }

    public Set<String> getKataDitebak() {
        return kataDitebak;
    }

    public String getKataAsli() {
        return kataAsli;
    }

    public void reset(String kataBaru) {
        this.kataAsli = kataBaru.toUpperCase();
        this.kataDitebak.clear();
        this.hurufDitebak.clear();
        this.kesempatan = maxKesempatan;
    }
}
