package toutpret.isep.com.toutpret.products;

import toutpret.isep.com.toutpret.models.ProductPanier;

public interface FragmentInterface {
    void checkIfProductIsMine_add_update(ProductPanier p);
    void checkIfProductIsMine_remove(ProductPanier p);
    void resetProducts();
}
