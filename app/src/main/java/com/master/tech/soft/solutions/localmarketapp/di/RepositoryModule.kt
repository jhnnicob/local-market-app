package com.master.tech.soft.solutions.localmarketapp.di
import com.google.firebase.firestore.FirebaseFirestore
import com.master.tech.soft.solutions.localmarketapp.data.repository.ProductRepositoryImpl
import com.master.tech.soft.solutions.localmarketapp.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(firestore: FirebaseFirestore): ProductRepository =
        ProductRepositoryImpl(firestore)


//    @Provides
//    @Singleton
//    fun provideCartRepository(
//        api: CartApi
//    ): CartRepository = CartRepositoryImpl(api)
//
//    @Provides
//    @Singleton
//    fun provideOrderRepository(
//        api: OrderApi
//    ): OrderRepository = OrderRepositoryImpl(api)
}
