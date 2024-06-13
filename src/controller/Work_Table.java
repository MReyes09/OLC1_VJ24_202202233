
package controller;

public class Work_Table {
    private String path;
    private String console;
    private String content;

    public Work_Table(String path, String console, String content) {
        this.path = path;
        this.console = console;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }  
    
}
