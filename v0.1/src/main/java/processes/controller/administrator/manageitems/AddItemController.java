package processes.controller.administrator.manageitems;

import connections.Connections;
import pojo.Item;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageItemsMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemController implements Controllers {
    boolean userChanged;
    User currentUser;
    int state = 0;  // Stay here

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user)  {
        currentUser = user;
        System.out.println("开始添加物品");
        Item item = new Item();
        System.out.println("请输入物品名称");
        item.setName(Connections.getInstance().getData());
        System.out.println("请输入物品制造商");
        item.setProducer(Connections.getInstance().getData());
        System.out.println("请输入类型");
        item.setType(Connections.getInstance().getData());
        int stateNum = 1;   // original
        double primePrice = 0;
        double retailPrice = 0;
        int num = 0;
        while (stateNum > 0) {
            try {
                if (stateNum == 1) {
                    System.out.println("请输入进价");
                    primePrice = Double.parseDouble(Connections.getInstance().getData());
                    System.out.println("请输入零售价");
                    retailPrice = Double.parseDouble(Connections.getInstance().getData());
                    System.out.println("请输入数量");
                    num = Integer.parseInt(Connections.getInstance().getData());
                    stateNum = 0;
                } else if (state == 2) {
                    System.out.println("请再次输入数字");
                    System.out.println("请再次输入进价");
                    primePrice = Double.parseDouble(Connections.getInstance().getData());
                    System.out.println("请再次输入零售价");
                    retailPrice = Double.parseDouble(Connections.getInstance().getData());
                    System.out.println("请再次输入数量");
                    num = Integer.parseInt(Connections.getInstance().getData());
                    stateNum = 0;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                stateNum = 2;
            }
        }
        item.setPrimeCost(primePrice);
        item.setRetailPrice(retailPrice);
        item.setNum(num);
        stateNum = 1;

        int year = 0, month = 0, day = 0;
        while (stateNum >= 1) {
            try {
                if (stateNum == 1) {
                    System.out.println("请输入生产日期信息");
                    System.out.println("请输入生产年份");
                    year = Integer.parseInt(Connections.getInstance().getData());
                    System.out.println("请输入生产月份");
                    month = Integer.parseInt(Connections.getInstance().getData());
                    System.out.println("请输入生产日");
                    day = Integer.parseInt(Connections.getInstance().getData());
                    stateNum = 0;
                } else if (stateNum == 2) {
                    System.out.println("请再次输入生产年份");
                    year = Integer.parseInt(Connections.getInstance().getData());
                    System.out.println("请再次输入生产月份");
                    month = Integer.parseInt(Connections.getInstance().getData());
                    System.out.println("请再次输入生产日");
                    day = Integer.parseInt(Connections.getInstance().getData());
                    stateNum = 0;
                }
                switch (month) {
                    case 1, 3, 5, 7, 8, 10, 12:
                        if (day > 31 || day < 1)
                            throw new RuntimeException("您收入的日期不是一个有效的日期");
                    case 4, 6, 9, 11:
                        if (day > 30 || day < 1)
                            throw new RuntimeException("您输入的日期不是一个有效的日期");
                    case 2:
                        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                            if (day > 29 || day < 1)
                                throw new RuntimeException("您输入的日期不是一个有效的日期");
                        } else {
                            if (day > 28 || day < 1)
                                throw  new RuntimeException("您输入的日期不是一个有效的日期");
                        }
                        break;
                    default:
                        throw new RuntimeException("您输入的日期不是一个有效的日期");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                stateNum = 2;
            } catch (RuntimeException e) {
                e.printStackTrace();
                stateNum = 2;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(year + "-" + month + "-" + day);
            item.setDateInProduced(date);
        } catch (ParseException e) {
            // 这通常不会发生
            throw new RuntimeException(e);
        }
        dao.ItemDaoImpl.getInstance().insert(item);


        System.out.println("是否要继续添加? 是请输入y");
        String prompt = Connections.getInstance().getData();
        if (prompt.equals("y")) {
            System.out.println("继续添加：");
            state = 1;
        } else {
            System.out.println("停止添加 返回菜单");
            state = 0;
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new ManageItemsMenu();
        } else if (state == 1) {
            return new AddItemController();
        }


        return new ManageItemsMenu();
    }
}
