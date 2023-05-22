package com.shopeeapp.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.shopeeapp.R;
import com.shopeeapp.activity.AccountSettings;
import com.shopeeapp.activity.BillHistory;
import com.shopeeapp.activity.ChangeLanguage;
import com.shopeeapp.activity.Help;

public class MenuItem {
    public static final String MENU_ACCOUNT_SETTINGS = "Cài đặt tài khoản";
    public static final String MENU_BILL_HISTORY = "Lịch sử hóa đơn";
    public static final String MENU_LANGUAGE = "Chọn ngôn ngữ";
    public static final String MENU_HELP = "Liên hệ hỗ trợ";
    public static List<String> menuItemTitle;
    public static List<Class> menuItemClass;
    public static List<Integer> menuItemImage;

    static {
        menuItemTitle = Arrays.asList(MENU_ACCOUNT_SETTINGS, MENU_LANGUAGE, MENU_HELP, MENU_BILL_HISTORY);
        menuItemClass = Arrays.asList(AccountSettings.class, ChangeLanguage.class, Help.class, BillHistory.class);
        menuItemImage = Arrays.asList(
                R.drawable.history,
                R.drawable.translation,
                R.drawable.messaging,
                R.drawable.settings
        );
    }

    private Integer discountImageID;
    private String title;
    private Integer bgImageID;

    public MenuItem(String title, Integer discountImageID, Integer bgImageID) {
        this.title = title;
        this.discountImageID = discountImageID;
        this.bgImageID = bgImageID;
    }

    public MenuItem(String title, Integer discountImageID) {
        this(title, discountImageID, null);
    }

    @NotNull
    public static List<MenuItem> createListMenuItem() {
        List<MenuItem> menu = new ArrayList<MenuItem>();
        int num = menuItemClass.size();
        for (int i = 0; i < num; i++) {
            menu.add(new MenuItem(menuItemTitle.get(i), menuItemImage.get(i)));
        }
        return menu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDiscountImageID() {
        return discountImageID;
    }

    public void setDiscountImageID(Integer discountImageID) {
        this.discountImageID = discountImageID;
    }

    public Integer getBgImageID() {
        return bgImageID;
    }

    public void setBgImageID(Integer bgImageID) {
        this.bgImageID = bgImageID;
    }

    public static Class getLayout(Integer position) {
        return menuItemClass.get(position);
    }

    public static Class getLayout(String title) {
        int position = menuItemTitle.indexOf(title);
        return menuItemClass.get(position);
    }
}
