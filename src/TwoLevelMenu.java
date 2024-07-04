import java.util.Scanner;

//This is the menu class
public class TwoLevelMenu {
    private int level;
    private int choice;
    private final String[] titles;
    private final String[][] items;

    //Constructor
    public TwoLevelMenu(String[] titles, String[][] items) {
        this.titles = titles;
        this.items = items;
        this.level = 0;
        this.choice = 0;
    }

    public boolean runMenu() {
        showMenu();
        choice = getChoiceMenu();
        if (choice == 0 && level == 0) {
            return false;
        }
        if (choice == 0) {
            level = 0;
            return runMenu();
        }
        if (level == 0) {
            level = choice;
            return runMenu();
        }
        return true;
    }

    private int getChoiceMenu() {
        System.out.print("Выберете пункт меню: ");
        Scanner scMenu = new Scanner(System.in);
        if (scMenu.hasNextByte()) {
            byte choiceMenu = scMenu.nextByte();
            if (validate(choiceMenu)) {
                return choiceMenu;
            }
        }
        System.out.println("Некорректный выбор");
        return getChoiceMenu();
    }

    private void showMenu() {
        System.out.println(titles[level]);
        for (int i = 0; i < items[level].length; i++) {
            System.out.println(items[level][i]);
        }
    }

    public boolean validate(byte choiceMenu) {
        return choiceMenu >= 0 && choiceMenu < items[level].length;
    }

    //Getters
    public int getLevel() {
        return level;
    }

    public int getChoice() {
        return choice;
    }

    public String[] getTitles() {
        return titles;
    }

    public String[][] getItems() {
        return items;
    }

}
