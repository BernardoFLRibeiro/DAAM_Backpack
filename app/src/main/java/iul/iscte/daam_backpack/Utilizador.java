package iul.iscte.daam_backpack;

public class Utilizador {

    private String nome, email, university, password;

    public Utilizador(){}

    public Utilizador(String nome, String email, String password, String university) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.university = university;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUniversity() {
        return university;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
