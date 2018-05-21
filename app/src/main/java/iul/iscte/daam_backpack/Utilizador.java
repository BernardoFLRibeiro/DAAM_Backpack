package iul.iscte.daam_backpack;

public class Utilizador{

    private String nome, email, university;

    public Utilizador(String nome, String email, String university){
        this.nome = nome;
        this.email = email;
        this. university = university;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
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

    public void setUniversity(String university) {
        this.university = university;
    }
}
