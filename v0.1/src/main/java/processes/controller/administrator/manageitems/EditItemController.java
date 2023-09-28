package processes.controller.administrator.manageitems;

import connections.Connections;
import pojo.Item;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.AdminFuncMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// TODO
public class EditItemController implements Controllers {
    boolean userChanged;
    User currentUser;
    int state = 0;

    private void changeItemName(Item i) {
        System.out.println("请输入物品名称");
        i.setName(Connections.getInstance().getData());
        dao.ItemDaoImpl.getInstance().updateByID(i);
    }

    private void changeItemProducer(Item i) {
        System.out.println("请输入物品制造商");
        i.setProducer(Connections.getInstance().getData());
        dao.ItemDaoImpl.getInstance().updateByID(i);
    }

    private void changeItemType(Item i) {
        System.out.println("请输入类型");
        i.setType(Connections.getInstance().getData());
        dao.ItemDaoImpl.getInstance().updateByID(i);
    }

    private void changeItemPrimeCost(Item i) {
        System.out.println("请输入进价");
        try {
            i.setPrimeCost(Double.parseDouble(Connections.getInstance().getData()));
            dao.ItemDaoImpl.getInstance().updateByID(i);
        } catch (NumberFormatException e) {
            System.out.println("您输入的进价格式不对 应是一个小数");
        }
    }

    private void changeItemRetailCost(Item i) {
        System.out.println("请输入零售价");
        try {
            i.setRetailPrice(Double.parseDouble(Connections.getInstance().getData()));
            dao.ItemDaoImpl.getInstance().updateByID(i);
        } catch (NumberFormatException e) {
            System.out.println("您输入的零售价格式不对 应是一个小数");
        }
    }

    private void changeItemNum(Item i) {
        System.out.println("请输入物品数量");
        try {
            i.setNum(Integer.parseInt(Connections.getInstance().getData()));
            dao.ItemDaoImpl.getInstance().updateByID(i);
        } catch (NumberFormatException e) {
            System.out.println("您输入的物品数量格式不对 应是一个整数");
        }
    }

    private void changeItemDateInProduced(Item i) {
        int stateNum = 1;

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
                                throw new RuntimeException("您输入的日期不是一个有效的日期");
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
            i.setDateInProduced(date);
        } catch (ParseException e) {
            // 这通常不会发生
            throw new RuntimeException(e);
        }
        dao.ItemDaoImpl.getInstance().updateByID(i);
    }

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User u) {
        currentUser = u;
        Connections con = Connections.getInstance();
        con.sendData("进入修改物品信息功能");
        System.out.println("首先查找 请输入要修改的物品名称");
        String iname = Connections.getInstance().getData();
        ArrayList<Storable> itemList = dao.ItemDaoImpl.getInstance().queryLikeName(iname);
        for (Storable st : itemList) {
            Item item = (Item) st;
            Connections.getInstance().sendData(item.toUserString());
        }
        System.out.println("请输入要修改的物品的编号");
        int iid = 0;
        try {
            iid = Integer.parseInt(Connections.getInstance().getData());
        } catch (NumberFormatException e) {
            System.out.println("输入的不是数字!");
            e.printStackTrace();
        }
        Item i = (Item) dao.ItemDaoImpl.getInstance().searchByID(iid);

        String[] menus = new String[8];
        menus[0] = "1.修改物品名称";
        menus[1] = "2.修改物品制造商";
        menus[2] = "3.修改物品型号";
        menus[3] = "4.修改物品进价";
        menus[4] = "5.修改物品零售价";
        menus[5] = "6.修改物品数量";
        menus[6] = "7.修改物品生产日期";
        menus[7] = "0.退出";
        con.sendData(menus);

        int nextInstruct = Integer.parseInt(con.getData());
        switch (nextInstruct) {
            case 1 -> changeItemName(i);
            case 2 -> changeItemProducer(i);
            case 3 -> changeItemType(i);
            case 4 -> changeItemPrimeCost(i);
            case 5 -> changeItemRetailCost(i);
            case 6 -> changeItemNum(i);
            case 7 -> changeItemDateInProduced(i);
            case 8 -> {
                state = 0;
            }
            default -> {
                System.out.println("您输入的菜单编号错误 将返回菜单");
                state = 1;
            }
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new AdminFuncMenu();
        }

        return new AdminFuncMenu();
    }
}
