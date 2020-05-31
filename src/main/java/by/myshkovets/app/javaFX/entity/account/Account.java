package by.myshkovets.app.javaFX.entity.account;

import by.myshkovets.app.javaFX.entity.Entity;

public class Account extends Entity {

    private String name;
    private String surname;
    private String mail;
    private String login;
    private String password;
    private Role role;
    private static final Builder BUILDER = new Builder();

    public  Account(Builder builder){
        super(builder.id);
        this.name = builder.name;
        this.surname = builder.surname;
        this.mail = builder.mail;
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;

    }

    public static Builder builder(){
        BUILDER.nullify();
        return BUILDER;
    }
    public static class Builder{

        private int id;
        private String name;
        private String surname;
        private String mail;
        private String login;
        private String password;
        private Role role;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;

        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;

        }

        public Builder mail(String mail) {
            this.mail = mail;
            return this;

        }

        public Builder login(String login){
            this.login = login;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder role(Role role){
            this.role = role;
            return this;
        }

        public Account build(){return new Account(this);}
        private void nullify(){
            this.id = 0;
            this.name = null;
            this.surname = null;
            this.mail = null;
            this.login = null;
            this.password = null;
            this.role = null;
        }
    }

    public String getName() { return name;}

    public String getSurname() { return surname;}

    public String getMial() { return mail;}

    public String getLogin() { return login;}

    public String getPassword() { return password;}

    public Role getRole() { return role;}

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mail=" + mail +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


    public String getInfo(){
        return "{имя-'"+ name +'\'' +
                ", фамилия-'" + surname + "'}";
    }
}
