package toutpret.isep.com.toutpret.panier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.products.FragmentInterface;

public class Panier {
    private static List<ProductPanier> listProducts = new ArrayList<>();
    private static Panier INSTANCE = new Panier();
    private static double TOTAL_AMOUNT = 0.0;
    private static List<FragmentInterface> listFragments = new ArrayList<>();

    private Panier() {
    }

    public static Panier getInstance() {
        return INSTANCE;
    }

    public static List<ProductPanier> getListProducts() {
        return listProducts;
    }

    public static void add(ProductPanier p) {
        listProducts.add(p);
        TOTAL_AMOUNT += p.getPrice();

        for (FragmentInterface f : listFragments) {
            f.checkIfProductIsMine_add_update(p);
        }
    }

    public static void remove(ProductPanier p) {
        listProducts.remove(p);
        TOTAL_AMOUNT -= p.getPrice();

        for (FragmentInterface f : listFragments) {
            f.checkIfProductIsMine_remove(p);
        }
    }

    public static void update(ProductPanier p, boolean isMinus) {
        for (ProductPanier product : listProducts) {
            if (product.getId().equals(p.getId())) {
                int position = listProducts.indexOf(product);

                listProducts.set(position, p);

                if (!isMinus) {
                    TOTAL_AMOUNT += p.getPrice();
                } else {
                    TOTAL_AMOUNT -= listProducts.get(position).getPrice();
                }

                for (FragmentInterface f : listFragments) {
                    f.checkIfProductIsMine_add_update(p);
                }
                return;
            }
        }
    }

    public static String getTotalAmount() {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(TOTAL_AMOUNT);
    }

    public static void addFragment(FragmentInterface f) {
        listFragments.add(f);
    }

    public static void clean() {
        TOTAL_AMOUNT = 0.0;
        listProducts = new ArrayList<>();

        for (FragmentInterface f : listFragments) {
            f.resetProducts();
        }
    }
}
