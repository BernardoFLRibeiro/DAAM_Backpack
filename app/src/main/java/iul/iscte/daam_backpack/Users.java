package iul.iscte.daam_backpack;

public class Users {

    public String nome, image, status;

    public Users(){

    }

    public Users(String nome, String image, String status){
        this.nome = nome;
        this.image = image;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

