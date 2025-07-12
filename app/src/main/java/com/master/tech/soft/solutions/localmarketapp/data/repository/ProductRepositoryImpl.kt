package com.master.tech.soft.solutions.localmarketapp.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    firestore: FirebaseFirestore
) : ProductRepository {

    private val productsCollection = firestore.collection("products")
    private val TAG = "ProductRepository"

    override fun listenToProducts(onUpdate: (List<Product>) -> Unit, onError: (Exception) -> Unit): ListenerRegistration {
        return productsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                onUpdate(products)
            } else {
                onUpdate(emptyList())
            }
        }
    }

    override suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val snapshot = productsCollection.get().await()
            val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
            Log.d(TAG, "Fetched ${products.size} products from Firestore")
            products
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching products", e)
            emptyList()
        }
    }

    override suspend fun getProductById(id: String): Product? = withContext(Dispatchers.IO) {
        try {
            val snapshot = productsCollection.document(id).get().await()
            val product = snapshot.toObject(Product::class.java)
            if (product != null) {
                Log.d(TAG, "Fetched product with id: $id")
            } else {
                Log.e(TAG, "Product with id $id not found")
            }
            product
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching product by id: $id", e)
            null
        }
    }


    override suspend fun addProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        try {
            productsCollection.document(product.id).set(product).await()
            Log.d(TAG, "Added product with id: ${product.id}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error adding product with id: ${product.id}", e)
            false
        }
    }

    override suspend fun updateProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        try {
            productsCollection.document(product.id).set(product).await()
            Log.d(TAG, "Updated product with id: ${product.id}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error updating product with id: ${product.id}", e)
            false
        }
    }

    override suspend fun deleteProduct(id: String): Boolean = withContext(Dispatchers.IO) {
        try {
            productsCollection.document(id).delete().await()
            Log.d(TAG, "Deleted product with id: $id")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting product with id: $id", e)
            false
        }
    }
}

