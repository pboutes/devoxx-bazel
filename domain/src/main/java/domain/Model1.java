package domain;


public class Model1 {

    private String data;
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



    public Model1(String data, String from) {
        this.data = data;
        this.from = from;
    }
}
