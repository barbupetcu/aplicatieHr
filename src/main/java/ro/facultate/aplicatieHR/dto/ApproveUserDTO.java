package ro.facultate.aplicatieHR.dto;

public class ApproveUserDTO {

    private String name;
    private String lastName;
    private String username;
    private String email;
    private String numeDept;
    private String functie;
    private Long marca;

    public ApproveUserDTO(String name, String lastName, String username, String email, String numeDept, String functie, Long marca) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.numeDept = numeDept;
        this.functie = functie;
        this.marca = marca;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeDept() {
        return numeDept;
    }

    public void setNumeDept(String numeDept) {
        this.numeDept = numeDept;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public Long getMarca() {
        return marca;
    }

    public void setMarca(Long marca) {
        this.marca = marca;
    }
}
