
package projekpbo.models;

import java.util.*;

public class GameState {
    private String kataAsli;
    private Set<Character> hurufDitebak;
    private int kesalahan;
    private final int maxKesalahan = 5;
    
    public GameState(String kataAsli){
        this.kataAsli = kataAsli.toUpperCase();
        this.hurufDitebak = new HashSet<>();
        this.kesalahan= 0;
    }
    
    //menambahkan huruf ditebak
    public boolean tebakHuruf(char huruf){
        if(hurufDitebak.contains(huruf)){
            return false; //huruf sudah ditebak
        }
        hurufDitebak.add(huruf);
        if(!kataAsli.contains(String.valueOf(huruf))){
            kesalahan++;
        }
        
        return true;
    }
    
    //menampilkan kata yg belum ditebak diganti "_"
    public String getKataTersembunyi(){
        StringBuilder sb = new StringBuilder();
        for (char c : kataAsli.toCharArray()){
            if(hurufDitebak.contains(c)){
                sb.append(c).append(" ");
            }else{
                sb.append("_");
            }
        }
        return sb.toString().trim();
    }
    
    public boolean isMenang(){
        for (char c : kataAsli.toCharArray()){
            if(!hurufDitebak.contains(c)){
                return false;
            }
        }
        return true;
    }
    
    public boolean isKalah() {
        return kesalahan >= maxKesalahan;
    }

    public int getKesalahan() {
        return kesalahan;
    }

    public int getMaxKesalahan() {
        return maxKesalahan;
    }

    public Set<Character> getHurufDitebak() {
        return hurufDitebak;
    }

    public String getKataAsli() {
        return kataAsli;
    }

    public void reset(String kataBaru) {
        this.kataAsli = kataBaru.toUpperCase();
        this.hurufDitebak.clear();
        this.kesalahan = 0;
    }
}
