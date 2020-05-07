package ui;

import exceptions.ListAlreadyExistException;
import model.*;
import network.ReadWebPageEx;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

// References:
// Java Tutorials - TableSelectionDemo
// Java Tutorials - SplitPaneDemo
// Java Tutorials - ListDemo



public class TodoListMenu extends JPanel {
    private TodoList generalTodoList;
    private TodoList currentTodoList;
    private ReadWebPageEx readWebPageEx;
    static JFrame frame;
    static JFrame addItemFrame;
    static JFrame addSubListFrame;

    private JTextField priorityField;
    private JTextField taskNameField;
    private JTextField dueDateField;
    private JTextField taskDescriptionField;
    private JTextField sublistField;
    private JTextField parentListField;
    private DefaultListModel subJlistModel;
    private JTable mainListTable;
    private String[][] mainListData = {{"", "", "", "", ""}};
    private String currentList = "Default list";
    private final int statusCol = 3;
    private final String[] mainListColumns = {"TaskName", "Description", "Priority", "Status", "DueDate"};


    // EFFECTS: constructs a TodoListMenu
    public TodoListMenu() throws IOException {
        generalTodoList = new TodoList("general todoList");
        currentTodoList = generalTodoList;
        //readWebPageEx = new ReadWebPageEx();
        //readWebPageEx.setUp();
        //generalTodoList.load("generalTodoListData.ser");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayGUI();
            }
        });
    }

    private void displayGUI() {
        frame = new JFrame("My TodoList");
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(createSplitPane());
    }

    private JSplitPane createSplitPane() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel rightPanelInSplit = getRightPanelInSplit();
        JPanel leftPanelInSplit = getLeftPanelInSplit();

        splitPane.add(leftPanelInSplit);
        splitPane.add(rightPanelInSplit);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(100, 50);
        rightPanelInSplit.setMinimumSize(minimumSize);
        leftPanelInSplit.setMinimumSize(minimumSize);
        return splitPane;
    }

    private JPanel getLeftPanelInSplit() {
        // leftPanel
        JPanel leftPanelInSplit = new JPanel(new BorderLayout());
        JPanel welcomeLabelPanel = getWelcomeLabelPanel();
        JPanel subListScrollPanel = getSubListScrollPanel();

        // menuCmboBox with combo box and lists
        JPanel menuCmboBox = getMenuCmboBox();

        leftPanelInSplit.add(welcomeLabelPanel, BorderLayout.PAGE_START);
        leftPanelInSplit.add(subListScrollPanel, BorderLayout.CENTER);
        leftPanelInSplit.add(menuCmboBox, BorderLayout.SOUTH);
        return leftPanelInSplit;
    }

    private JPanel getWelcomeLabelPanel() {
        // Welcome user label
        JPanel taskLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 5));

//        JLabel helloUser = new JLabel("<html>Hello<br><b>User<b></html>");
//        helloUser.setFont(new Font(Font.SERIF, Font.PLAIN, 40));
//        helloUser.setForeground(new Color(68100173));

        JTextArea helloUser = new JTextArea("");
        helloUser.append("User's \nTodoList");
        helloUser.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        Color jpanelColor = frame.getBackground();
        helloUser.setBackground(jpanelColor);
        helloUser.setForeground(new Color(68100173));

        JLabel imageLogo = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(
                "/Users/peterlin/Sheena/CPSC_210/project_u9u2b/data/TodoList Icon.png")
                .getImage().getScaledInstance(85, 85, Image.SCALE_DEFAULT));
        imageLogo.setIcon(imageIcon);
        taskLabelPanel.add(imageLogo);
        taskLabelPanel.add(helloUser);

        return taskLabelPanel;
    }

    private JPanel getMenuCmboBox() {
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(30, 70));
        JButton menuBtn = new JButton("Menu");
        menuBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        String[] menuSelection = {"Add Todo Item", "Add Todo Sub-list", "Edit Todo Item",
                "Remove Todo Item", "Clear List"};
        JComboBox menuComboBox = new JComboBox(menuSelection);
        menuComboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        //JButton deleteBtn = new JButton("Delete");

        menuCmboBoxActionPerformed(menuComboBox);

        menuPanel.add(menuBtn);
        menuPanel.add(menuComboBox);
        return menuPanel;
    }

    private void menuCmboBoxActionPerformed(JComboBox menuComboBox) {
        menuComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String menuSelection = (String) menuComboBox.getSelectedItem();
                if (menuSelection.equals("Add Todo Item")) {
                    addItemPopUpAction();
                } else if (menuSelection.equals("Add Todo Sub-list")) {
                    addSubListPopUpAction();
                } else if (menuSelection.equals("Remove Todo Item")) {
                    selectJtableToRemoveActPerformed(mainListTable);
                } else if (menuSelection.equals("Edit Todo Item")) {
                    selectJtableToEditActPerformed(mainListTable);
                } else if (menuSelection.equals("Clear List")) {
                    selectJtableToClearListActPerformed(mainListTable);
                }
            }
        });
    }

    private void addItemPopUpAction() {
        addItemFrame = new JFrame();
        JPanel addItemPopUp = new JPanel(new GridLayout(0, 2));
//
        JPanel addItemEnterRtrBtn = getAddItemEnterRtrBtn();

        addItemTextFields(addItemPopUp);
        addItemPopUp.add(addItemEnterRtrBtn, BorderLayout.PAGE_END);

        addItemFrame.setSize(400, 200);
        //addItemFrame.setResizable(true);
        addItemFrame.setVisible(true);
        addItemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addItemFrame.getContentPane().add(addItemPopUp);
    }

    private void addItemTextFields(JPanel addItemPopUp) {
//        addItemPopUp.add(new JLabel(" Your preferred todo list: "));
//        listField = new JTextField();
//        addItemPopUp.add(listField);
        addItemPopUp.add(new JLabel(" Is this an urgent item? (Y/N)"));
        priorityField = new JTextField();
        addItemPopUp.add(priorityField);
        addItemPopUp.add(new JLabel(" Task Name: "));
        taskNameField = new JTextField();
        addItemPopUp.add(taskNameField);
        addItemPopUp.add(new JLabel(" Due Date: "));
        dueDateField = new JTextField();
        addItemPopUp.add(dueDateField);
        addItemPopUp.add(new JLabel(" Task Description: "));
        taskDescriptionField = new JTextField();
        addItemPopUp.add(taskDescriptionField);
    }

    private JPanel getAddItemEnterRtrBtn() {
        JPanel addItemPopUpMenu = new JPanel(new FlowLayout());
        final JButton addItemEnterBtn = new JButton("Enter");
        addItemPopUpMenu.add(addItemEnterBtn);

        addItemEnterBtnActPerformed(addItemEnterBtn);

//        final JButton addItemReturnBtn = new JButton("Return to Menu");
//        addItemPopUpMenu.add(addItemReturnBtn);
//        returnBtnActPerformed(addItemReturnBtn);
        return addItemPopUpMenu;
    }

    private void addItemEnterBtnActPerformed(JButton addItemEnterBtn) {
        addItemEnterBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String action = (String) addItemEnterBtn.getActionCommand();
                ImageIcon greenCheckerIcon = getGreenCheckerIcon();
                if (action.equals("Enter")) {
//                    System.out.println("Yes Button pressed");
                    //String listInput = listField.getText();
                    String taskNameInput = taskNameField.getText();
                    String priorityInput = priorityField.getText();
                    String dueDateInput = dueDateField.getText();
                    String taskDescriptionInput = taskDescriptionField.getText();
                    processUserInputsToAddTodoItemFields(currentList, taskNameInput, priorityInput,
                            dueDateInput, taskDescriptionInput);
                    getAddItemMessageDialog(greenCheckerIcon);
                    String[][] twoDdata = generate();
                    refresh(twoDdata);
                    addItemFrame.dispose();
                }
            }
        });
    }


    private void getAddItemMessageDialog(ImageIcon icon) {
        JOptionPane.showMessageDialog(frame, "Your todo item has been added.",
                null, JOptionPane.PLAIN_MESSAGE, icon);
    }

    private ImageIcon getGreenCheckerIcon() {
        ImageIcon greenCheckerIcon = new ImageIcon(new ImageIcon(
                "/Users/peterlin/Sheena/CPSC_210/project_u9u2b/data/greenChecker.jpg")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        ;
        return greenCheckerIcon;
    }


    private void addSubListPopUpAction() {
        addSubListFrame = new JFrame();
        JPanel addSubListPopUp = new JPanel(new GridLayout(0, 2));

        JPanel subListEnterRtrBtn = getSubListEnterRtrBtn();

        addSubListPopUp.add(new JLabel(" Create a new sub-list name: "));
        sublistField = new JTextField();
        addSubListPopUp.add(sublistField);

        addSubListPopUp.add(new JLabel(" Which list does this sub-list belong to?"));
        parentListField = new JTextField();
        addSubListPopUp.add(parentListField);


        addSubListPopUp.add(subListEnterRtrBtn, BorderLayout.PAGE_END);

        addSubListFrame.setSize(300, 100);
        addSubListFrame.setVisible(true);
        addSubListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addSubListFrame.getContentPane().add(addSubListPopUp);
    }

    private JPanel getSubListEnterRtrBtn() {
        JPanel subListPopUpMenu = new JPanel(new FlowLayout());
        final JButton addSubListEnterBtn = new JButton("Enter");
        subListPopUpMenu.add(addSubListEnterBtn);
        addSubListEnterBtnActPerformed(addSubListEnterBtn);
        final JButton addItemReturnBtn = new JButton("Return to Menu");
        subListPopUpMenu.add(addItemReturnBtn);
        returnBtnActPerformed(addItemReturnBtn);
        return subListPopUpMenu;
    }


    private void addSubListEnterBtnActPerformed(JButton addSubListEnterBtn) {
        addSubListEnterBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String action = (String) addSubListEnterBtn.getActionCommand();
                if (action.equals("Enter")) {
                    //System.out.println("Yes Button pressed");
                    String sublistNameInput = sublistField.getText();
                    String parentlistNameInput = parentListField.getText();
                    //sublist.add(sublistNameInput);
                    try {
                        processUserInputsToAddSubList(sublistNameInput, parentlistNameInput);
                        subJlistModel.addElement(sublistNameInput);
                        getAddSubListMessageDialog();
                        addSubListFrame.dispose();
                    } catch (ListAlreadyExistException ex) {
                        JOptionPane.showMessageDialog(frame,
                                "This sub-list has been created. Pick a different name.");
                    }
                }
            }
        });
    }

    private void returnBtnActPerformed(JButton returnBtn) {
        returnBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String action = (String) returnBtn.getActionCommand();
                if (action.equals("Return to Menu")) {
                    addSubListFrame.dispose();
                }
            }
        });
    }

    private void getAddSubListMessageDialog() {
        JOptionPane.showMessageDialog(frame,
                "Your sub-list has been created.", null,
                JOptionPane.PLAIN_MESSAGE, getGreenCheckerIcon());
    }

    private JPanel getSubListScrollPanel() {
        // List scroll pane that lists all the todosub-lists
        subJlistModel = new DefaultListModel();
        subJlistModel.addElement("Default list");
        JList subJlist = new JList(subJlistModel);
        subJlist.setPreferredSize(new Dimension(50, 100));
        subJlist.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        subJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subJlist.setSelectedIndex(0);
        selectJlistActPerformed(subJlist);

        JScrollPane subListScrollPane = new JScrollPane(subJlist);
        JPanel subListPanel = new JPanel(new GridLayout(1, 1));
        //subListPanel.setPreferredSize(new Dimension(50,100));
        TitledBorder subListTitle = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Sub-lists",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(Font.SERIF,
                        Font.PLAIN, 18), new Color(68100173));
        subListTitle.setTitleJustification(TitledBorder.CENTER);
        subListPanel.setBorder(subListTitle);
        subListPanel.add(subListScrollPane);
        return subListPanel;
    }

    private void selectJlistActPerformed(JList subJlist) {
        subJlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                currentList = list.getSelectedValue().toString();
                if (currentList.equalsIgnoreCase("Default list")) {
                    //generalTodoList = parentTodoList;
                    currentTodoList = generalTodoList;
                } else {
                    TodoList selectedList = generalTodoList.searchList(currentList);
                    //generalTodoList = selectedList;
                    currentTodoList = selectedList;
                }
                String[][] twoDdata = generate();
                refresh(twoDdata);
            }
        });
    }

    private JPanel getRightPanelInSplit() {
        // rightPanel
        JPanel rightPanelInSplit = new JPanel(new BorderLayout());
        JPanel mainListScrollPanel = getMainListScrollPanel();

        JList<TodoSystemLevel> list = new JList<>();
        JComponent comp = new JScrollPane();
        list.add(comp);

        rightPanelInSplit.add(mainListScrollPanel, BorderLayout.CENTER);
        return rightPanelInSplit;
    }

    private JPanel getMainListScrollPanel() {
        mainListTable = getjTable();

        JScrollPane mainListScrollPane = new JScrollPane(mainListTable);
        JPanel mainListPanel = new JPanel(new GridLayout(1, 1));
        TitledBorder mainListTitle = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "My Todo Tasks",
                TitledBorder.CENTER, TitledBorder.TOP, new Font(Font.SERIF,
                        Font.PLAIN, 18), new Color(68100173));
        mainListTitle.setTitleJustification(TitledBorder.CENTER);
        mainListPanel.setBorder(mainListTitle);
        mainListPanel.add(mainListScrollPane);
        return mainListPanel;
    }

    private JTable getjTable() {
        mainListTable = new JTable(mainListData, mainListColumns);
        mainListTable.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        mainListTable.setRowHeight(20);
        Font f = new Font(Font.SERIF, Font.PLAIN, 15);
        JTableHeader header = mainListTable.getTableHeader();
        header.setFont(f);
        return mainListTable;
    }

    // EFFECTS: convert the string array data to 2D array and return the todo item fields in 2D array
    private String[][] generate() {
        // generate the data
        List<String[]> data = currentTodoList.generateData();
        // convert the formatting from  List<String[]> to String[][]
        String[][] dataArray2D = new String[data.size()][];
        for (int i = 0; i < dataArray2D.length; i++) {
            String[] row = data.get(i);
            dataArray2D[i] = row;
        }
        return dataArray2D;
    }

    // MODIFIES: this
    // EFFECTS: update the mainListData using the 2-D array data
    // and add the new row of todo item to the Jtable
    private void refresh(String[][] dataArray2D) {
        mainListData = dataArray2D;
        DefaultTableModel tableModel = new DefaultTableModel(mainListData, mainListColumns);
        tableModel.addRow(mainListData);
        //tableModel.addRow(mainListData);
        //mainListTable.setModel(tableModel);
        mainListTable.setModel(tableModel);
        getCustomRenderedTable(mainListTable);
        mainListTable.getSelectionBackground();
    }

    // Stackoverflow - https://stackoverflow.com/questions/5673430/java-jtable-change-cell-color
    private static JTable getCustomRenderedTable(final JTable table) {
        //int statusCol = 2;
        getCustomRenderedConditions(table);
        return table;
    }

    private static void getCustomRenderedConditions(JTable table) {
        customRenderedConditions(table);
    }

    private static void customRenderedConditions(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            //@Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if ("Urgent".equals(getStatus(table, row))) {
                    c.setBackground(new Color(252, 121, 120));
                    //c.setForeground(Color.BLACK);
                    if (isSelected) {
                        setBackground(Color.GRAY);
                    }
                } else {
                    c.setBackground(table.getBackground());
                    //c.setForeground(table.getForeground());
                    if (isSelected) {
                        setBackground(Color.GRAY);
                    }
                }
                return this;
            }
        });
    }

    private static Object getStatus(JTable table, int row) {
        return table.getModel().getValueAt(row, 2);
    }


    private void selectJtableToEditActPerformed(JTable mainListTable) {
        mainListTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                String updatedData = (String) model.getValueAt(row, column);
                processUserInputsToEditSpecificTodoItem(currentList, row, column, updatedData);
                //processPrintSpecificList(currentList);
            }
        });
    }

    private void selectJtableToRemoveActPerformed(JTable mainListTable) {
        //DefaultTableModel tableModel = new DefaultTableModel(mainListData, mainListColumns);
        //int row = mainListTable.getSelectedRow();
        //System.out.println(row);
        //int modelRow = mainListTable.convertRowIndexToModel(row);
        DefaultTableModel tableModel = (DefaultTableModel) mainListTable.getModel();
        ImageIcon trashBinIcon = new ImageIcon(new ImageIcon(
                "/Users/peterlin/Sheena/CPSC_210/project_u9u2b/data/trashBin.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        int selectedRow = mainListTable.getSelectedRow();
        System.out.println(selectedRow);
        if (selectedRow != -1) {
            // remove selected row from the model
            //int selectedRow = mainListTable.getSelectedRow();
            processRemoveSpecificTodoItem(currentList, selectedRow);
            int modelRow = mainListTable.convertRowIndexToModel(selectedRow);
            tableModel.removeRow(modelRow);
            JOptionPane.showMessageDialog(frame,
                    "Your todo item has been removed.",
                    null, JOptionPane.PLAIN_MESSAGE, trashBinIcon);
        }
    }

    private void selectJtableToClearListActPerformed(JTable mainListTable) {
        ImageIcon clearListIcon = new ImageIcon(new ImageIcon(
                "/Users/peterlin/Sheena/CPSC_210/project_u9u2b/data/clearList.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        DefaultTableModel tableModel = (DefaultTableModel) mainListTable.getModel();
        processClearSpecificList(currentList);
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
        mainListTable.setModel(tableModel);
        JOptionPane.showMessageDialog(frame,
                "Your " + currentList + " has been cleared.",
                null, JOptionPane.PLAIN_MESSAGE, clearListIcon);
    }

    //
    // EFFECTS: pass user inputs and call add new todos method in TodoList class
    public void processUserInputsToAddTodoItemFields(String currentList, String taskNameInput,
                                                     String priorityInput, String dueDateInput,
                                                     String taskDescriptionInput) {
        TodoSystemLevel todoItem = generalTodoList.createTodoItem(priorityInput,
                taskNameInput, dueDateInput, taskDescriptionInput);
        generalTodoList.addNewTodos(todoItem, currentList);
    }

    // EFFECTS: pass user inputs and call add new todos method in TodoList class
    public void processUserInputsToAddSubList(String sublistName,
                                              String currentList) throws ListAlreadyExistException {
        TodoSystemLevel todoSubList = generalTodoList.createTodoSubList(sublistName);
        generalTodoList.addNewTodos(todoSubList, currentList);
    }

    // EFFECTS: pass user inputs and call edit todo item method in TodoList class
    public void processUserInputsToEditSpecificTodoItem(String currentList,
                                                        int itemNum, int column, String updatedValue) {
        generalTodoList.editTodoItem(currentList, itemNum, column, updatedValue);
    }

    // EFFECTS: pass user inputs and call remove todo item method in TodoList class
    public void processRemoveSpecificTodoItem(String currentList, int itemNum) {
        generalTodoList.removeSpecificTodoItem(currentList, itemNum);
    }

    // EFFECTS: pass user inputs and call clear todo list method in TodoList class
    public void processClearSpecificList(String currentList) {
        generalTodoList.clearTodoList(currentList);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TodoListMenu todoMenu = new TodoListMenu();
    }


    // EFFECTS: get user input for todo list options
//    public void processUserMenu() throws IOException {
//        int userChoice = 0;
//        ReadWebPageEx.main(null);
//        while (userChoice != 8) {
//            System.out.println("What would you like to do today? \n 1 - Add todo item \n 2 - Add todo sub-list "
//                    + "\n 3 - Edit specific todo item" + "\n 4 - Remove specific todo item"
//                    + "\n 5 - Print out specific list" + "\n 6 - Print all lists and items"
//                    + "\n 7 - Clear all todo items" + "\n 8 - Quit todo list");
//            userChoice = scanner.nextInt();
//
//            processUserChoiceforMenu(userChoice);
//        }
//    }

//    // EFFECTS: process user menu based on user input for todo list option
//    private void processUserChoiceforMenu(int userChoice) throws IOException {
////        try {
////       } catch (TooManyThingsUndoneException e) {
////            System.out.println("You have many incomplete items. Please complete them before adding new ones.");
////            if (userChoice == 1) {
////                processUserInputsToAddTodoItemFields();
////        if (userChoice == 2) {
////            processUserInputsToAddSubList();
////        if (userChoice == 3) {
////            processUserChoiceForEditSpecificTodoItem();
////        if (userChoice == 4) {
////            processRemoveSpecificTodoItem();
////        } else if (userChoice == 5) {
////            processPrintSpecificList();
//        if (userChoice == 6) {
//            processPrintAllList();
//        } else if (userChoice == 7) {
//            processClearSpecificList();
//        } else if (userChoice == 8) {
//            generalTodoList.save("generalTodoListData.ser");
//            System.out.println("Quitting todo list, bye!");
//        }
//    }


//    // EFFECTS: get user input for todo item fields
//    public void processUserInputsToAddTodoItemFields(String listInput, String taskNameInput,
//                                                     String priorityInput, String dueDateInput,
//                                                     String taskDescriptionInput) {
//        String nextItem = "Y";
//        while (nextItem.equalsIgnoreCase("Y")) {
//            scanner.nextLine();
//            String userChoice = processUserSelectedList();
//            System.out.println("Is this an urgent item? (Y or N)");
//            String isUrgent = scanner.nextLine();
//            System.out.println("Enter your todo item:");
//            String itemName = scanner.nextLine();
//            System.out.println("Enter todo item due date: (dd/mm/yyyy)");
//            String itemDueDate = scanner.nextLine();
//            System.out.println("Enter todo item description (leave blank if none):");
//            String itemDescription = scanner.nextLine();
//            System.out.println("Do you want to add next todo item? (Y or N)");
//            nextItem = scanner.nextLine();
//            TodoSystemLevel todoItem = generalTodoList.createTodoItem(isUrgent, itemName,
//            itemDueDate, itemDescription);
//            generalTodoList.addNewTodos(todoItem, userChoice);
//        TodoSystemLevel todoItem = generalTodoList.createTodoItem(priorityInput,
//                taskNameInput, dueDateInput, taskDescriptionInput);
//        generalTodoList.addNewTodos(todoItem, listInput);
//    }

//    public void processUserInputsToAddSubList(String sublistName, String parentlistName)
//    throws ListAlreadyExistException {
//        scanner.nextLine();
//        System.out.println("Please assign a name for your new sub-list");
//        String sublistName = scanner.nextLine();
//        String userChoice = processUserSelectedList();
//        TodoSystemLevel todoSubList = generalTodoList.createTodoSubList(sublistName);
//        generalTodoList.addNewTodos(todoSubList, parentlistName);
//    }

//    public String processUserSelectedList() {
//        scanner.nextLine();
//        System.out.println("Do you want to add/edit on the default todo list or a sub-list?"
//                + "\n (Enter 'default' for default list, else enter the name of sub-list)");
//        String userChoice = scanner.nextLine();
//        return userChoice;
//    }


//    public void processUserChoiceForEditSpecificTodoItem() {
////        String updateNextField = "Y";
////        while (updateNextField.equalsIgnoreCase("Y")) {
////            String listChoice = processUserSelectedList();
////            System.out.println("Please select the number of the item you wish to update:");
////            int itemNum = scanner.nextInt();
////            System.out.println("Please select the field of the item you wish to update: \n 1 - Item name"
////                    + "\n 2 - Item status  \n 3 - Item due date \n 4 - Item details");
////            int editChoice = scanner.nextInt();
//            updateNextField = processUserInputsToEditSpecificTodoItem(listChoice, itemNum, editChoice);
//        }
//    }


//    public void processUserInputsToEditSpecificTodoItem(String currentList,
//                                                        int itemNum, int column, String updatedValue) {
//        scanner.nextLine();
//        if (editChoice == 1) {
//            System.out.println("Please update item name:");
//            newItemName = scanner.nextLine();
//        } else if (editChoice == 2) {
//            System.out.println("Please update item due date (dd/mm/yyyy):");
//            newItemDueDate = scanner.nextLine();
//        } else if (editChoice == 3) {
//            System.out.println("Please update item detail description:");
//            newItemDescription = scanner.nextLine();
//        } else if (editChoice == 4) {
//            System.out.println("Please update item status: (Complete)");
//            newItemStatus = scanner.nextLine();
//        }
//        generalTodoList.editTodoItem(currentList, itemNum, column, updatedValue);
//        //String updateNextField = userContinueEditOrNot();
//        //return updateNextField;
//    }

//    public String userContinueEditOrNot() {
//        System.out.println("Do you want to update other fields? (Y or N)");
//        String updateNextField = scanner.nextLine();
//        return updateNextField;
//    }


//    //    // EFFECTS: get specific todo item to be removed from the user
//    public void processRemoveSpecificTodoItem(String currentList, int itemNum) {
//        //boolean validItemNum = false;
//        //while (!validItemNum) {
//        String listChoice = processUserSelectedList();
//        System.out.println("Please select the number of item you wish to remove:");
//        itemNum = scanner.nextInt();
//        //try {
//        generalTodoList.removeSpecificTodoItem(currentList, itemNum);
//        //validItemNum = true;
//        //} catch (ItemNotOnListException i) {
//        //System.out.println("The item you wish to removed is invalid...");
//        //validItemNum = false;
//        //} finally {
//        //System.out.println("Your item will be removed when the input is valid.");
//    }
//
//
    // EFFECTS: call print todo list method in TodoList class

//    public void processPrintSpecificList(String currentList) {
//        //String listChoice = processUserSelectedList();
//        generalTodoList.printTodoList(currentList);
//    }

//    public void processPrintAllList() {
//        generalTodoList.display("  ");
//    }

//    // EFFECTS: call clear todo list method in TodoList class
//    public void processClearSpecificList(String currentList) {
//        //String listChoice = processUserSelectedList();
//        generalTodoList.clearTodoList(currentList);
//    }


}







