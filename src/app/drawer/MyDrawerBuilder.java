package app.drawer;

import app.form.Bill;
import app.form.Chart;
import app.form.Customer;
import app.form.DetailProduct;
import app.form.ProductAttributes;
import app.form.Products;
import app.form.Sell;
import app.form.Staff;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import app.form.Home;
import app.form.Voucher;
import app.main.Main;
import raven.swing.AvatarIcon;
import app.tabbed.WindowsTabbed;

public class MyDrawerBuilder extends SimpleDrawerBuilder { // Kế thừa từ SimpleDrawerBuilder để tạo drawer

    @Override
    public SimpleHeaderData getSimpleHeaderData() { // Phương thức ghi đè để lấy dữ liệu header
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/app/image/profile.jpg"), 80, 80, 1000)) // Điều chỉnh kích thước ảnh là 80x80
                .setTitle("Tshirt"); // Thiết lập tiêu đề là "Tshirt"
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() { // Phương thức ghi đè để lấy tùy chọn menu
        String menus[][] = { // Mảng chứa các menu và submenu
            {"~TRANG CHÍNH~"},
            {"Trang chủ"},
            {"Bán"},
            {"~ỨNG DỤNG~"},
            {"Sản phẩm", "Sản phẩm", "Sản phẩm chi tiết", "Thuộc tính sản phẩm"},
            {"Hóa đơn"},
            {"Khuyến mãi"},
            {"Quản lí nhân viên"},
            {"Nhân viên"},
            {"~KHÁC~"},
            {"Charts"},
            {"Đăng xuất"}};

        String icons[] = { // Mảng chứa các biểu tượng tương ứng với menu
            "home.svg",
            "sell.svg",
            "product.svg",
            "bill.svg",
            "voucher.svg",
            "staff.svg",
            "customer.svg",
            "chart.svg",
            "logout.svg"};

        return new SimpleMenuOption()
                .setMenus(menus) // Thiết lập các menu
                .setIcons(icons) // Thiết lập các biểu tượng cho menu
                .setBaseIconPath("app/drawer/icon") // Đường dẫn cơ sở cho các biểu tượng
                .setIconScale(0.45f) // Tỷ lệ thu nhỏ của biểu tượng
                .addMenuEvent(new MenuEvent() { // Thêm sự kiện cho menu
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        if (index == 0) {
                            WindowsTabbed.getInstance().addTab("Trang chủ", new Home()); // Mở tab Home
                        } else if (index == 1) {
                            WindowsTabbed.getInstance().addTab("Bán", new Sell()); // Mở tab Sell
                        } else if (index == 2) {
                            if (subIndex == 1) {
                                WindowsTabbed.getInstance().addTab("Sản Phẩm", new Products()); // Mở tab Products
                            } else if (subIndex == 2) {
                                WindowsTabbed.getInstance().addTab("Sản phẩm chi tiết", new DetailProduct()); // Mở tab Detail Product
                            } else if (subIndex == 3) {
                                WindowsTabbed.getInstance().addTab("Thuộc tính sản phẩm", new ProductAttributes()); // Mở tab Product Attributes
                            }
                        } else if (index == 3) {
                            WindowsTabbed.getInstance().addTab("Hóa đơn", new Bill()); // Mở tab Bill
                        } else if (index == 4) {
                            WindowsTabbed.getInstance().addTab("Khuyến mãi", new Voucher()); // Mở tab Voucher
                        } else if (index == 5) {
                            WindowsTabbed.getInstance().addTab("Quản lí nhân viên", new Staff()); // Mở tab Staff
                        } else if (index == 6) {
                            WindowsTabbed.getInstance().addTab("Nhân viên", new Customer()); // Mở tab Customer
                        } else if (index == 7) {
                            WindowsTabbed.getInstance().addTab("Chart", new Chart()); // Mở tab Chart
                        } else if (index == 8) {
                            Main.main.login(); // Gọi phương thức đăng nhập
                        }
                        System.out.println("Menu selected " + index + " " + subIndex); // In ra menu đã chọn
                    }
                })
                .setMenuValidation(new MenuValidation() { // Thiết lập xác thực menu
                    @Override
                    public boolean menuValidation(int index, int subIndex) {
                        // Xác thực menu, có thể điều chỉnh điều kiện
                        return true; // Mặc định cho phép tất cả các menu
                    }
                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() { // Phương thức ghi đè để lấy dữ liệu footer
        return new SimpleFooterData()
                .setTitle("Trang shop Tshirt") // Thiết lập tiêu đề footer
                .setDescription("Version 1.1.0"); // Thiết lập mô tả phiên bản
    }

    @Override
    public int getDrawerWidth() { // Phương thức ghi đè để lấy chiều rộng drawer
        return 275; // Thiết lập chiều rộng drawer là 275
    }
    //thư viện swing-glasspane-popup-1.3.0.jar với swing-toast-notifications-1.0.1.jar
    // thư viện theme màu sắc alf flatfat
    
}
