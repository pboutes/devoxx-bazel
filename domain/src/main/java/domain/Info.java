package domain;


public class Info {

    private String data;
    private String from;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private Info(String data, String from) {
        this.data = data;
        this.from = from;
    }

    public static Info of(String data, String from) {
        return new Info(data, from);
    }
}
