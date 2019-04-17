package domain;

public class ModelGen1 {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ModelGen1(String data) {
        this.data = data;
        System.out.println("Hello");
    }
}
