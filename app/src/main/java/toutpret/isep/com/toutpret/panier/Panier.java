package toutpret.isep.com.toutpret.panier;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.models.Product;

public class Panier {
    private static List<Product> listProducts = new ArrayList<>();
    private static Panier INSTANCE = new Panier();

    private Panier() {
    }

    public static Panier getInstance() {
        return INSTANCE;
    }

    public static List<Product> getListProducts() {
        return listProducts;
    }

    public static void add(Product p) {
        listProducts.add(p);
    }

    public static void remove(Product p) {
        listProducts.remove(p);
    }

    public static void update(Product p) {
        for (Product product : listProducts) {
            if (product.getId().equals(p.getId())) {
                int position = listProducts.indexOf(product);

                listProducts.set(position, p);
                return;
            }
        }
    }
}
