package entry;

public class Entry implements Entryable {
    private String name;
    private String username;
    private String password;
    private String note;
    private Category category;
    private String website;

    public Entry(String name, String username, String password, String note, Category category, String website) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.note = note;
        this.category = category;
        this.website = website;
    }

    public Entry(String name, String username, String password, Category category) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = category;
    }

    public Entry(String name, String username, String password, String note, Category category) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.note = note;
        this.category = category;
    }

    public Entry(String name, String username, String password, Category category, String website) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = category;
        this.website = website;
    }

    public Entry(){
        this.name = "DEFAULT";
        this.username = "john_doe";
        this.password = "password123";
        this.note = "NO NOTES";
        this.category = Category.UNASSIGNED;
        this.website = "no website";
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getName();
    }
}
