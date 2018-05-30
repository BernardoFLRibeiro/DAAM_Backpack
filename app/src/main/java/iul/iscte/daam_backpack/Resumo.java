package iul.iscte.daam_backpack;

import android.graphics.Bitmap;

public class Resumo {

    private String Nome;
    private String Cadeira;
    private String Universidade;
    private int TotalFotos;
    private String UserId;

    public Resumo(){
    }

    public Resumo(String nome, String cadeira, String universidade, int totalFotos, String userid){
        this.Nome = nome;
        this.Cadeira = cadeira;
        this.Universidade = universidade;
        this.TotalFotos = totalFotos;
        this.UserId = userid;

    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setCadeira(String cadeira) {
        Cadeira = cadeira;
    }

    public void setUniversidade(String universidade) {
        Universidade = universidade;
    }

    public String getNome() {
        return Nome;
    }

    public String getCadeira() {
        return Cadeira;
    }

    public String getUniversidade() {
        return Universidade;
    }

    public int getTotalFotos() {
        return TotalFotos;
    }

    public void setTotalFotos(int totalFotos) {
        TotalFotos = totalFotos;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }
}
