package cn.qqtextwar.utils.oaml.oaml;

public class ValueMapping {
    private String before;
    private String after;

    public ValueMapping(String before, String after) {
        this.before = before;
        this.after = after;
    }
    public ValueMapping(){

    }
    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        after = after;
    }

    @Override
    public String toString() {
        return "ValueMapping{" +
                "before='" + before + '\'' +
                ", After='" + after + '\'' +
                '}';
    }
}
