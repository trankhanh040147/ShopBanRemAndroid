package com.shopeeapp.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.shopeeapp.dbhelper.AccountDbHelper;
import com.shopeeapp.dbhelper.BillDbHelper;
import com.shopeeapp.dbhelper.CartDbHelper;
import com.shopeeapp.dbhelper.DiscountDbHelper;
import com.shopeeapp.dbhelper.NotificationDbHelper;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.dbhelper.ProductImageDbHelper;
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.dbhelper.PromoDbHelper;
import com.shopeeapp.dbhelper.ReviewDbHelper;
import com.shopeeapp.dbhelper.RoleDbHelper;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.ProductImage;
import com.shopeeapp.entity.ProductType;
import com.shopeeapp.entity.Promo;
import com.shopeeapp.entity.Store;

import java.util.ArrayList;

public class CreateData {
    public CreateData(Context context){
        createProductImage(context);
        createPromo(context);
        createDiscount(context);
        createStore(context);
        createProductType(context);
        createAccount(context);
        createBill(context);
        createRole(context);
        createCart(context);
        createNotification(context);
        createReview(context);
        createUser(context);
        createProduct(context);
    }

        private void createProductImage(Context context) {
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(context);
        SQLiteDatabase db = productImageDbHelper.getWritableDatabase();
        productImageDbHelper.onUpgrade(db, 1, 2);
        productImageDbHelper.onCreate(db);

        ProductImage productImage1 = new ProductImage(1, 1, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aohoodie.png", null);
        ProductImage productImage2 = new ProductImage(2, 2, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoacdu.png", null);
        ProductImage productImage3 = new ProductImage(3, 3, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoackaki.png", null);
        ProductImage productImage4 = new ProductImage(4, 4, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoacjean.png", null);
        ProductImage productImage5 = new ProductImage(5, 5, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoacthun.png", null);
        ProductImage productImage6 = new ProductImage(6, 6, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoacda.jpg", null);
        ProductImage productImage7 = new ProductImage(7, 7, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoacnhung.png", null);
        ProductImage productImage8 = new ProductImage(8, 8, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aothunngantay.png", null);
        ProductImage productImage9 = new ProductImage(9, 9, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/somidai.png", null);
        ProductImage productImage10 = new ProductImage(10, 10, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aopolo.png", null);
        ProductImage productImage11 = new ProductImage(11, 11, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/somingan.jpg", null);
        ProductImage productImage12 = new ProductImage(12, 12, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/somidai.png", null);
        ProductImage productImage13 = new ProductImage(13, 13, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/shortkaki.jpg", null);
        ProductImage productImage14 = new ProductImage(14, 14, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/shortjean.png", null);
        ProductImage productImage15 = new ProductImage(15, 15, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/shortthun.png", null);
        ProductImage productImage16 = new ProductImage(16, 16, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/shorttay.jpg", null);
        ProductImage productImage17 = new ProductImage(17, 17, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/jean.png", null);
        ProductImage productImage18 = new ProductImage(18, 18, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/jogger.png", null);
        ProductImage productImage19 = new ProductImage(19, 19, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/quankaki.png", null);
        ProductImage productImage20 = new ProductImage(20, 20, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/quantay.jpg", null);

        ProductImage productImage21 = new ProductImage(21, 21, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothunnutayngan1.jpg?alt=media&token=1ebe2cee-46eb-4959-aeee-a72a1429dc09", null);
        ProductImage productImage22 = new ProductImage(22, 22 , "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothuntaydai1.jpg?alt=media&token=d18da450-479c-4618-b60e-7e4191b770a4", null);
        ProductImage productImage23 = new ProductImage(23, 23, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aosomi1.jpg?alt=media&token=6f54be3b-2422-4fc7-8522-fd8e61ef5f68", null);
        ProductImage productImage24 = new ProductImage(24, 24, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aosomidai1.jpg?alt=media&token=3e061851-64f8-4e08-a4d3-ea29cb0ff9cc", null);
        ProductImage productImage25 = new ProductImage(25, 25, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aokieu1.jpg?alt=media&token=f58e68a9-185e-4c57-b6cf-51126b88edbb", null);
        ProductImage productImage26 = new ProductImage(26, 26, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/shortjeannu1.jpg?alt=media&token=12155483-180a-487d-9366-cb5f8ff7acab", null);
        ProductImage productImage27 = new ProductImage(27, 27, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/shortjeannu1.jpg?alt=media&token=12155483-180a-487d-9366-cb5f8ff7acab", null);
        ProductImage productImage28 = new ProductImage(28, 28, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/jeancatinh1.jpg?alt=media&token=17679180-aac0-4222-84ba-91081ffca969", null);
        ProductImage productImage29 = new ProductImage(29, 29, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/jogger1.jpg?alt=media&token=4945c165-3f3d-4a01-bbed-2020b3953902", null);
        ProductImage productImage30 = new ProductImage(30, 30, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/quanthunnu1.jpg?alt=media&token=df0871db-77b6-483d-bb91-99264658cdba", null);
        ProductImage productImage31 = new ProductImage(31, 31, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/chanvay1.jpg?alt=media&token=bbc74f71-7cfa-4f0e-a944-70f66fb6677d", null);
        ProductImage productImage32 = new ProductImage(32, 32, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/dam1.jpg?alt=media&token=e8a23b6c-6857-44f9-ab01-1852ed6c6e29", null);
        ProductImage productImage33 = new ProductImage(33, 33, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/yem1.jpg?alt=media&token=35bcc43b-274e-49c2-be19-57b99978888d", null);
        ProductImage productImage34 = new ProductImage(34, 34, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aocroptop1.jpg?alt=media&token=ec190b9d-174a-4ef1-b802-2767d30ab373", null);
        ProductImage productImage35 = new ProductImage(35, 35, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothundoi1.jpg?alt=media&token=d05aeaf4-f9f4-490e-aa51-7c67732fcf9f", null);
        ProductImage productImage36 = new ProductImage(36, 36, "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aosomidoi1.jpg?alt=media&token=309e40f5-bfe1-4d40-86a7-d97a39218a68", null);

        ProductImage productImage37 = new ProductImage(37, 37, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/balophukien.png", null);
        ProductImage productImage38 = new ProductImage(38, 38, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/tuixachphukien.png", null);
        ProductImage productImage39 = new ProductImage(39, 39, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/nonsnapack.png", null);
        ProductImage productImage40 = new ProductImage(40, 40, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/nonbucket.png", null);
        ProductImage productImage41 = new ProductImage(41, 41, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/nonphot.png", null);
        ProductImage productImage42 = new ProductImage(42, 42, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/nonluoitrai.png", null);
        ProductImage productImage43 = new ProductImage(43, 43, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/thatlung.png", null);
        ProductImage productImage44 = new ProductImage(44, 44, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/dongho.png", null);
        ProductImage productImage45 = new ProductImage(45, 45, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/vophukien.jpg", null);
        ProductImage productImage46 = new ProductImage(46, 46, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/dongutreem.jpg", null);
        ProductImage productImage47 = new ProductImage(47, 47, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/tatreem.jpg", null);
        ProductImage productImage48 = new ProductImage(48, 48, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aothunnho.jpg", null);
        ProductImage productImage49 = new ProductImage(49, 49, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/quansorttreem.jpg", null);
        ProductImage productImage50 = new ProductImage(50, 50, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/damcongchuatreem.png", null);
        ProductImage productImage51 = new ProductImage(51, 51, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aohoangtutreem.jpg", null);
        ProductImage productImage52 = new ProductImage(52, 52, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/dothethaotreem.jpg", null);
        ProductImage productImage53 = new ProductImage(53, 53, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aodai.png", null);
        ProductImage productImage54 = new ProductImage(54, 54, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aokhoaclen.jpg", null);
        ProductImage productImage55 = new ProductImage(55, 55, "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Image_192x192/aopolo.png", null);


        ArrayList<ProductImage> productImages = new ArrayList<>();
        productImages.add(productImage1);
        productImages.add(productImage2);
        productImages.add(productImage3);
        productImages.add(productImage4);
        productImages.add(productImage5);
        productImages.add(productImage6);
        productImages.add(productImage7);
        productImages.add(productImage8);
        productImages.add(productImage9);
        productImages.add(productImage10);
        productImages.add(productImage11);
        productImages.add(productImage12);
        productImages.add(productImage13);
        productImages.add(productImage14);
        productImages.add(productImage15);
        productImages.add(productImage16);
        productImages.add(productImage17);
        productImages.add(productImage18);
        productImages.add(productImage19);
        productImages.add(productImage20);
        productImages.add(productImage21);
        productImages.add(productImage22);
        productImages.add(productImage23);
        productImages.add(productImage24);
        productImages.add(productImage25);
        productImages.add(productImage26);
        productImages.add(productImage27);
        productImages.add(productImage28);
        productImages.add(productImage29);
        productImages.add(productImage30);
        productImages.add(productImage31);
        productImages.add(productImage32);
        productImages.add(productImage33);
        productImages.add(productImage34);
        productImages.add(productImage35);
        productImages.add(productImage36);
        productImages.add(productImage37);
        productImages.add(productImage38);
        productImages.add(productImage39);
        productImages.add(productImage40);
        productImages.add(productImage41);
        productImages.add(productImage42);
        productImages.add(productImage43);
        productImages.add(productImage44);
        productImages.add(productImage45);
        productImages.add(productImage46);
        productImages.add(productImage47);
        productImages.add(productImage48);
        productImages.add(productImage49);
        productImages.add(productImage50);
        productImages.add(productImage51);
        productImages.add(productImage52);
        productImages.add(productImage53);
        productImages.add(productImage54);
        productImages.add(productImage55);

        for (ProductImage item : productImages) {
            productImageDbHelper.insert(item);
        }
    }

    public void createPromo(Context context) {
        PromoDbHelper promoDbHelper = new PromoDbHelper(context);
        SQLiteDatabase db = promoDbHelper.getWritableDatabase();
        promoDbHelper.onUpgrade(db, 1, 2);
        promoDbHelper.onCreate(db);

        Promo promo1 = new Promo(1, 1, null, null, null);
        Promo promo2 = new Promo(2, 8, null, null, null);
        Promo promo3 = new Promo(3, 21, null, null, null);
        Promo promo4 = new Promo(4, 86, null, null, null);
        Promo promo5 = new Promo(5, 35, null, null, null);
        Promo promo6 = new Promo(6, 37, null, null, null);
        Promo promo7 = new Promo(7, 5, null, null, null);
        Promo promo8 = new Promo(8, 6, null, null, null);
        Promo promo9 = new Promo(9, 9, null, null, null);
        Promo promo10 = new Promo(10, 39, null, null, null);

        ArrayList<Promo> promos = new ArrayList<>();
        promos.add(promo1);
        promos.add(promo2);
        promos.add(promo3);
        promos.add(promo4);
        promos.add(promo5);
        promos.add(promo6);
        promos.add(promo7);
        promos.add(promo8);
        promos.add(promo9);
        promos.add(promo10);

        for (Promo item : promos) {
            promoDbHelper.insert(item);
        }
    }

    private void createDiscount(Context context) {
        DiscountDbHelper discountDbHelper = new DiscountDbHelper(context);
        SQLiteDatabase db = discountDbHelper.getWritableDatabase();
        discountDbHelper.onUpgrade(db, 1, 2);
        discountDbHelper.onCreate(db);

        Discount discount1 = new Discount(1, 1, Float.parseFloat("20"), "OK");
        Discount discount2 = new Discount(2, 2, Float.parseFloat("10"), "OK");
        Discount discount3 = new Discount(3, 3, Float.parseFloat("10"), "OK");
        Discount discount4 = new Discount(4, 4, Float.parseFloat("10"), "OK");
        Discount discount5 = new Discount(5, 5, Float.parseFloat("10"), "OK");
        Discount discount6 = new Discount(6, 6, Float.parseFloat("10"), "OK");
        Discount discount7 = new Discount(7, 7, Float.parseFloat("10"), "OK");
        Discount discount8 = new Discount(8, 8, Float.parseFloat("10"), "OK");
        Discount discount9 = new Discount(9, 9, Float.parseFloat("20"), "OK");
        Discount discount10 = new Discount(10, 10, Float.parseFloat("20"), "OK");
        Discount discount11 = new Discount(11, 11, Float.parseFloat("20"), "OK");
        Discount discount12 = new Discount(12, 12, Float.parseFloat("20"), "OK");
        Discount discount13 = new Discount(13, 13, Float.parseFloat("10"), "OK");
        Discount discount14 = new Discount(14, 14, Float.parseFloat("10"), "OK");
        Discount discount15 = new Discount(15, 15, Float.parseFloat("10"), "OK");
        Discount discount16 = new Discount(16, 16, Float.parseFloat("20"), "OK");
        Discount discount17 = new Discount(17, 17, Float.parseFloat("20"), "OK");
        Discount discount18 = new Discount(18, 18, Float.parseFloat("20"), "OK");
        Discount discount19 = new Discount(19, 19, Float.parseFloat("10"), "OK");
        Discount discount20 = new Discount(20, 20, Float.parseFloat("10"), "OK");

        ArrayList<Discount> discounts = new ArrayList<>();
        discounts.add(discount1);
        discounts.add(discount2);
        discounts.add(discount3);
        discounts.add(discount4);
        discounts.add(discount5);
        discounts.add(discount6);
        discounts.add(discount7);
        discounts.add(discount8);
        discounts.add(discount9);
        discounts.add(discount10);
        discounts.add(discount11);
        discounts.add(discount12);
        discounts.add(discount13);
        discounts.add(discount14);
        discounts.add(discount15);
        discounts.add(discount16);
        discounts.add(discount17);
        discounts.add(discount18);
        discounts.add(discount19);
        discounts.add(discount20);

        for (Discount item : discounts) {
            discountDbHelper.insert(item);
        }
    }

    private void createStore(Context context){
        StoreDbHelper storeDbHelper = new StoreDbHelper(context);
        SQLiteDatabase db = storeDbHelper.getWritableDatabase();
        storeDbHelper.onUpgrade(db, 1, 2);
        storeDbHelper.onCreate(db);

        Store store1 = new Store(1, "Angle Lam", "3261712481", "mail@123.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Linh Trung, Thu Duc", null);
        Store store2 = new Store(2, "Thoca house", "1412344112", "mail123@abc.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Binh Thanh, HCM", null);
        Store store3 = new Store(3, "Dottie", "82376324742", "mail@abc.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 1, HCM", null);
        Store store4 = new Store(4, "Toto Shop", "932627482", "mail@gmail.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 2, HCM", null);
        Store store5 = new Store(5, "Nha Kho Liti", "835247262", "fruitland@gmail.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 3, HCM", null);
        Store store6 = new Store(6, "Len Clothing", "365729474", "shop@abc.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 7, HCM", null);
        Store store7 = new Store(7, "Happy Zoo Store", "935274819", "contact@chang.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 4, HCM", null);
        Store store8 = new Store(8, "Laimut", "91637283626", "contact@vietphuc.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 5, HCM", null);
        Store store9 = new Store(9, "Ren By Tee", "382617382", "contact@thuanviet.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 12, HCM", null);
        Store store10 = new Store(10, "Lookbook", "912736279", "contact@buyit.com", "https://firebasestorage.googleapis.com/v0/b/shopeeapp-b1352.appspot.com/o/stores%2F768fe8f9-93c9-419c-bc59-0551f1673a8c?alt=media&token=8e522635-e5d9-4def-a761-b6219de8ec1b", "Quan 10, HCM", null);

        ArrayList<Store> stores = new ArrayList<>();
        stores.add(store1);
        stores.add(store2);
        stores.add(store3);
        stores.add(store4);
        stores.add(store5);
        stores.add(store6);
        stores.add(store7);
        stores.add(store8);
        stores.add(store9);
        stores.add(store10);

        for (Store item : stores){
            storeDbHelper.insert(item);
        }

    }

    private void createProductType(Context context){
        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(context);
        SQLiteDatabase db = productTypeDbHelper.getWritableDatabase();
        productTypeDbHelper.onUpgrade(db, 1, 2);
        productTypeDbHelper.onCreate(db);

        ProductType productType1 = new ProductType(1, "Ao Khoac", "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Type_96x96/aokhoac.jpg?alt=media&token=bfb06bb9-a837-489b-8458-a2c1e487ac07", null);
        ProductType productType2 = new ProductType(2, "Do Nam", "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Type_96x96/donam_type.jpg?alt=media&token=bfb06bb9-a837-489b-8458-a2c1e487ac07", null);

        ProductType productType3 = new ProductType(3, "Do Nu", "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/donu.jpg?alt=media&token=abed971c-4d2e-4fc3-bd38-15776d6cd8e9", null);
        ProductType productType4 = new ProductType(4, "Do Doi", "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/dodoi.jpg?alt=media&token=51244430-eb96-4342-85d7-0ea00f896a6e", null);

        ProductType productType5 = new ProductType(5, "Do Tre Em", "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Type_96x96/dotreem_type.png?alt=media&token=bfb06bb9-a837-489b-8458-a2c1e487ac07", null);
        ProductType productType6 = new ProductType(6, "Do Nguoi Gia", "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Type_96x96/donguoilon_type.png?alt=media&token=bfb06bb9-a837-489b-8458-a2c1e487ac07", null);
        ProductType productType7 = new ProductType(7, "Phu Kien", "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_Type_96x96/phukien_type.jpg?alt=media&token=bfb06bb9-a837-489b-8458-a2c1e487ac07", null);

        ArrayList<ProductType> productTypes = new ArrayList<>();
        productTypes.add(productType1);
        productTypes.add(productType2);
        productTypes.add(productType3);
        productTypes.add(productType4);
        productTypes.add(productType5);
        productTypes.add(productType6);
        productTypes.add(productType7);

        for (ProductType item : productTypes){
            productTypeDbHelper.insert(item);
        }
    }

    private void createAccount(Context context){
        AccountDbHelper accountDbHelper = new AccountDbHelper(context);
        SQLiteDatabase db = accountDbHelper.getWritableDatabase();
        accountDbHelper.onUpgrade(db, 1, 2);
        accountDbHelper.onCreate(db);
    }

    private void createBill(Context context){
        BillDbHelper billDbHelper = new BillDbHelper(context);
        SQLiteDatabase db = billDbHelper.getWritableDatabase();
        billDbHelper.onUpgrade(db, 1, 2);
        billDbHelper.onCreate(db);
    }

    private void createCart(Context context){
        CartDbHelper cartDbHelper = new CartDbHelper(context);
        SQLiteDatabase db = cartDbHelper.getWritableDatabase();
        cartDbHelper.onUpgrade(db, 1, 2);
        cartDbHelper.onCreate(db);
    }

    private void createNotification(Context context){
        NotificationDbHelper notificationDbHelper = new NotificationDbHelper(context);
        SQLiteDatabase db = notificationDbHelper.getWritableDatabase();
        notificationDbHelper.onUpgrade(db, 1, 2);
        notificationDbHelper.onCreate(db);
    }

    private void createReview(Context context){
        ReviewDbHelper reviewDbHelper = new ReviewDbHelper(context);
        SQLiteDatabase db = reviewDbHelper.getWritableDatabase();
        reviewDbHelper.onUpgrade(db, 1, 2);
        reviewDbHelper.onCreate(db);
    }

    private void createRole(Context context){
        RoleDbHelper roleDbHelper = new RoleDbHelper(context);
        SQLiteDatabase db = roleDbHelper.getWritableDatabase();
        roleDbHelper.onUpgrade(db, 1, 2);
        roleDbHelper.onCreate(db);
    }

    private void createUser(Context context){
        UserDbHelper userDbHelper = new UserDbHelper(context);
        SQLiteDatabase db = userDbHelper.getWritableDatabase();
        userDbHelper.onUpgrade(db, 1, 2);
        userDbHelper.onCreate(db);
    }

    private void createProduct(Context context){
        ProductDbHelper productDbHelper = new ProductDbHelper(context);
        SQLiteDatabase db = productDbHelper.getWritableDatabase();
        productDbHelper.onUpgrade(db, 1, 2);
        productDbHelper.onCreate(db);

        Product product1 = new Product(1, 1, 1, "Ao hoodie", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aohoodie.png?alt=media&token=b6583425-04c4-4e67-bdbc-01279dc3280b", null, Float.parseFloat("0"), null);
        Product product2 = new Product(2, 2, 1, "Ao khoac du", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoacdu.png", null, Float.parseFloat("0"), null);
        Product product3 = new Product(3, 3, 1, "Ao khoac kaki", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoackaki.png", null, Float.parseFloat("0"), null);
        Product product4 = new Product(4, 4, 1, "Ao khoac jean", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoacjean.png", null, Float.parseFloat("0"), null);
        Product product5 = new Product(5, 5, 1, "Ao khoac thun", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoacthun.png", null, Float.parseFloat("0"), null);
        Product product6 = new Product(6, 6, 1, "Ao khoac da", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoacda.jpg", null, Float.parseFloat("0"), null);
        Product product7 = new Product(7, 7, 1, "Ao khoac nhung", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoacnhung.png", null, Float.parseFloat("0"), null);
        Product product8 = new Product(8, 8, 2, "Ao thun tay ngan", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aothunngantay.png", null, Float.parseFloat("0"), null);
        Product product9 = new Product(9, 9, 2, "Ao thun tay dai", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/somidai.png", null, Float.parseFloat("0"), null);
        Product product10 = new Product(10, 10, 2, "Ao polo", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aopolo.png", null, Float.parseFloat("0"), null);
        Product product11 = new Product(11, 9, 2, "So mi ngan", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/somingan.jpg", null, Float.parseFloat("0"), null);
        Product product12 = new Product(12, 8, 2, "So mi dai", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/somidai.png", null, Float.parseFloat("0"), null);
        Product product13 = new Product(13, 7, 2, "Short kaki", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/shortkaki.jpg", null, Float.parseFloat("0"), null);
        Product product14 = new Product(14, 6, 2, "Short jean", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/shortjean.png", null, Float.parseFloat("0"), null);
        Product product15 = new Product(15, 5, 2, "Short thun", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/shortthun.png", null, Float.parseFloat("0"), null);
        Product product16 = new Product(16, 4, 2, "Short tay", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/shorttay.jpg", null, Float.parseFloat("0"), null);
        Product product17 = new Product(17, 3, 2, "Jean", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/jean.png", null, Float.parseFloat("0"), null);
        Product product18 = new Product(18, 2, 2, "Jogger", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/jogger.png", null, Float.parseFloat("0"), null);
        Product product19 = new Product(19, 1, 2, "Quan kaki", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/quankaki.png", null, Float.parseFloat("0"), null);
        Product product20 = new Product(20, 10, 2, "Quan Tay", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/quantay.jpg", null, Float.parseFloat("0"), null);

        Product product21 = new Product(21, 1, 3, "Ao thun tay ngan nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothuntayngannu.jpg?alt=media&token=8d75cb5a-4d24-4b11-a2ad-9ba1f45e0f4a", null, Float.parseFloat("0"), null);
        Product product22 = new Product(22, 1, 3, "Ao thun tay dai nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothuntaydainu.jpg?alt=media&token=2fbff4d0-3767-4b12-b25a-9a8817bffaba", null, Float.parseFloat("0"), null);
        Product product23= new Product(23, 2, 3, "So mi nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aosominu.jpg?alt=media&token=4a8aad7a-1bfe-44bd-ba1b-62f03a7cc569", null, Float.parseFloat("0"), null);
        Product product24 = new Product(24, 2, 3, "So mi dai nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aosomidai.jpg?alt=media&token=4ed3ba6e-3815-49be-8c34-c570fd967555", null, Float.parseFloat("0"), null);
        Product product25 = new Product(25, 3, 3, "Ao kieu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aokieunu.jpg?alt=media&token=65778d95-4389-4623-bd45-839019daba94", null, Float.parseFloat("0"), null);
        Product product26 = new Product(26, 3, 3, "Short kaki nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/shortkakinu.jpg?alt=media&token=02060f24-75eb-4df0-8cff-b1c02f25baa4", null, Float.parseFloat("0"), null);
        Product product27 = new Product(27, 4, 3, "Short jean nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/shortjeannujpg.jpg?alt=media&token=045d33e1-9e81-4618-87c2-abf2543a42eb", null, Float.parseFloat("0"), null);
        Product product28 = new Product(28, 4, 3, "Jean ca tinh", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/jeancatinh.jpg?alt=media&token=2b4de830-c7ba-4f7e-9e73-392e04bb14e9", null, Float.parseFloat("0"), null);
        Product product29 = new Product(29, 5, 3, "Jogger nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/Joggernu.jpg?alt=media&token=9f1231a4-e153-4b75-856e-6b069905c74c", null, Float.parseFloat("0"), null);
        Product product30 = new Product(30, 5, 3, "Quan thun nu", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/quanthunnu.jpg?alt=media&token=bf3c03a2-a8ed-4aee-a0d2-529d2f62a811", null, Float.parseFloat("0"), null);
        Product product31 = new Product(31, 6, 3, "Chan vay", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/chanvay.jpg?alt=media&token=96bbca62-8ad8-46af-8b8b-04325c0180e0", null, Float.parseFloat("0"), null);
        Product product32 = new Product(32, 6, 3, "Dam", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/dam.jpg?alt=media&token=f27dcd28-fa6a-43f8-ac67-8fb6839ff157", null, Float.parseFloat("0"), null);
        Product product33 = new Product(33, 7, 3, "Yem", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/yem.jpg?alt=media&token=85eb53bb-e5b9-4878-9e1c-6eb5d074bdb1", null, Float.parseFloat("0"), null);
        Product product34 = new Product(34, 7, 3, "Ao croptop", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aocroptop.jpg?alt=media&token=3e03d466-36aa-4ab3-b435-9608e70f6853", null, Float.parseFloat("0"), null);
        Product product35 = new Product(35, 8, 4, "Ao thun doi", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/aothundoi.jpg?alt=media&token=e18381bc-3089-458e-94d6-e00de6396b99", null, Float.parseFloat("0"), null);
        Product product36 = new Product(36, 8, 4, "An so mi doi", Double.parseDouble("700000"), "https://firebasestorage.googleapis.com/v0/b/androi-image-fp.appspot.com/o/somidoi.png?alt=media&token=4389b8c8-95b1-4e58-ad87-af1beec3f1f9", null, Float.parseFloat("0"), null);

        Product product37 = new Product(37, 9, 7, "Balo", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/balophukien.png", null, Float.parseFloat("0"), null);
        Product product38 = new Product(38, 9, 7, "Tui Xach", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/tuixachphukien.png", null, Float.parseFloat("0"), null);
        Product product39 = new Product(39, 10, 7, "Non snapback", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/nonsnapack.png", null, Float.parseFloat("0"), null);
        Product product40 = new Product(40, 10, 7, "Non bucket", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/nonbucket.png", null, Float.parseFloat("0"), null);
        Product product41 = new Product(41, 10, 7, "Non phot", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/nonphot.png", null, Float.parseFloat("0"), null);
        Product product42 = new Product(42, 9, 7, "Non luoi trai", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/nonluoitrai.png", null, Float.parseFloat("0"), null);
        Product product43 = new Product(43, 9, 7, "Day nit", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/thatlung.png", null, Float.parseFloat("0"), null);
        Product product44 = new Product(44, 8, 7, "Dong do", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/dongho.png", null, Float.parseFloat("0"), null);
        Product product45 = new Product(45, 7, 7, "Vo", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/vophukien.jpg", null, Float.parseFloat("0"), null);
        Product product46 = new Product(46, 6, 5, "Ao ngu", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/dongutreem.jpg", null, Float.parseFloat("0"), null);
        Product product47 = new Product(47, 5, 5, "Ta", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/tatreem.jpg", null, Float.parseFloat("0"), null);
        Product product48 = new Product(48, 4, 5, "Ao thun nho", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aothunnho.jpg", null, Float.parseFloat("0"), null);
        Product product49 = new Product(49, 3, 5, "Quan short", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/quansorttreem.jpg", null, Float.parseFloat("0"), null);
        Product product50 = new Product(50, 2, 5, "Dam cong chua", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/damcongchuatreem.png", null, Float.parseFloat("0"), null);
        Product product51 = new Product(51, 1, 5, "Ao hoang tu", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aohoangtutreem.jpg", null, Float.parseFloat("0"), null);
        Product product52 = new Product(52, 1, 5, "Do the thao", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/dothethaotreem.jpg", null, Float.parseFloat("0"), null);
        Product product53 = new Product(53, 2, 6, "Ao dai", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aodai.png", null, Float.parseFloat("0"), null);
        Product product54 = new Product(54, 3, 6, "Ao khoac len", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aokhoaclen.jpg", null, Float.parseFloat("0"), null);
        Product product55 = new Product(55, 3, 6, "Ao thun polo", Double.parseDouble("700000"), "https://raw.githubusercontent.com/ThaiHaiDev/StoreImage/main/Image_Android/Product_96x96/aopolo.png", null, Float.parseFloat("0"), null);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        products.add(product11);
        products.add(product12);
        products.add(product13);
        products.add(product14);
        products.add(product15);
        products.add(product16);
        products.add(product17);
        products.add(product18);
        products.add(product19);
        products.add(product20);
        products.add(product21);
        products.add(product22);
        products.add(product23);
        products.add(product24);
        products.add(product25);
        products.add(product26);
        products.add(product27);
        products.add(product28);
        products.add(product29);
        products.add(product30);
        products.add(product31);
        products.add(product32);
        products.add(product33);
        products.add(product34);
        products.add(product35);
        products.add(product36);
        products.add(product37);
        products.add(product38);
        products.add(product39);
        products.add(product40);
        products.add(product41);
        products.add(product42);
        products.add(product43);
        products.add(product44);
        products.add(product45);
        products.add(product46);
        products.add(product47);
        products.add(product48);
        products.add(product49);
        products.add(product50);
        products.add(product51);
        products.add(product52);
        products.add(product53);
        products.add(product54);
        products.add(product55);

        for (Product item : products){
            productDbHelper.insert(item);
        }
    }

}
