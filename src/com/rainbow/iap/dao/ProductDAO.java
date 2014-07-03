package com.rainbow.iap.dao;

import com.rainbow.iap.entity.Product;
import com.rainbow.iap.entity.json.PurchaseMethodType;

public interface ProductDAO
{
	public Product getProduct(String productId, PurchaseMethodType purchaseMethod);
}
