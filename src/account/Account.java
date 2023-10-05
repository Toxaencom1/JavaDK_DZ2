package account;

import java.util.Objects;

public class Account {
    private String name;
    private String pass;
    private String ip;
    private String port;

    public Account(String name, String pass, String ip, String port) {
        this.name = name;
        this.pass = pass;
        this.ip = ip;
        this.port = port;
    }

    public Account() {
        this.name = null;
        this.pass = null;
        this.ip = null;
        this.port = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name)
                && Objects.equals(pass, account.pass)
                && Objects.equals(ip, account.ip)
                && Objects.equals(port, account.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pass, ip, port);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
