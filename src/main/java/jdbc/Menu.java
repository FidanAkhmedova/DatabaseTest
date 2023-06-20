package jdbc;

public class Menu {
    private String name;
    private int id;
    private int price;

    public Menu(){}
    public Menu(String name, int id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                '}';
    }

}
