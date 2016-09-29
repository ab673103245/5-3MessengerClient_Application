package sang;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class User implements Serializable {
    private String name;
    private int age;
    private String nickname;

    public User(String name, int age, String nickname) {
        this.name = name;
        this.age = age;
        this.nickname = nickname;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
