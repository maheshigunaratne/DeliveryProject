package Model;


public class Packages {
    String weight;
    String volume;
    Long price;

    public Packages() {
    }

    public Packages(String volume, String weight, Long price) {
        this.weight = weight;
        this.price = price;
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
