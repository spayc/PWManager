package main;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import comp.ComparatorCategory;
import except.NoPasswordEnteredException;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import entry.Category;
import entry.Entry;
import utils.Crypto;

import javax.crypto.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class Main extends Application {
    //CONST
    private static final Crypto crypto = new Crypto();
    public static final String UNICODE_FORMAT = "UTF-8";
    public static final String KEY_TYPE = "PBKDF2WithHmacSHA512";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/";


    //LISTS
    private ArrayList<Entry> entries = new ArrayList<>();
    private ObservableList<Entry> observableList;


    /////////////TMP//////////////
    private byte[] currentSalt;
    private char[] currentPassword;
    private File currentFile;


    ////////LOGIN SCREEN//////////
    private Scene sceneLogin;
    //TXT
    private PasswordField txtPassword;
    private TextField txtSelectedFile;
    //LBL
    private Label lblPassword;
    //BOX
    private HBox hBoxLogin;
    private HBox hBoxButtons;
    private VBox vBoxLblLogin;
    private VBox vBoxTxtLogin;
    //BTN
    private Button btnLogin;
    private Button btnFileChoose;
    //FC
    private FileChooser fileChooser = new FileChooser();
    private GridPane loginPane;
    //ALERT
    private Alert alertWrongPassword;
    private Alert alertNoPassword;
    //IMG VIEW
    private ImageView imageViewLogin;
    private Image imageLogin;



    ////////DASHBOARD//////////
    private Scene sceneDashboard;
    //LV
    private ListView<Entry> listView;
    //BTN
    private Button btnAddEntry;
    private Button btnUpdateEntry;
    private Button btnRemoveEntry;
    private Button btnSaveEntries;
    private Button btnCopyTitle;
    private Button btnCopyUsername;
    private Button btnCopyPassword;
    private Button btnOpenURL;
    private ToggleButton btnHidePassword;
    private ToggleGroup tgPassword;
    //BOX
    private HBox hBoxMain;
    private VBox vBoxMain;
    private HBox hBoxDetails;
    private HBox hBoxButtonsDashboard;
    private VBox vBoxLabels;
    private VBox vBoxTxtFields;
    private VBox vBoxCopyControls;
    private HBox hBoxPasswordControls;
    //LBL
    private Label lblName;
    private Label lblUsername;
    private Label lblPasswordDash;
    private Label lblWebsite;
    private Label lblNotes;
    private Label lblCategory;
    //TXT
    private TextField txtName;
    private TextField txtUsername;
    private TextField txtPasswordDash;
    private TextField txtWebsite;
    private TextArea txtNotes;
    private ComboBox comboBoxCategory;
    //IMAGE VIEW
    private ImageView imageViewCategory;
    private Image imageCategory;
    //COLORS
    private Color colorWhite = Color.WHITE;
    private Color colorGrey = Color.rgb(53, 49, 49);
    private BackgroundFill backgroundFillWhite;
    private BackgroundFill backgroundFillGrey;
    private Background backgroundWhite;
    private Background backgroundGrey;




    ///////MENU BAR///////////
    private MenuBar menuBar;

    private Menu menuSave;
    private MenuItem menuItemSave;
    private MenuItem menuItemSaveAs;
    private Menu menuControls;
    private MenuItem menuItemAddEntry;
    private MenuItem menuItemRemoveEntry;
    private MenuItem menuItemUpdateEntry;
    private MenuItem menuItemClearFields;
    private Menu menuUser;
    private MenuItem menuItemLogOut;
    private MenuItem menuItemExit;
    private Menu menuTools;
    private MenuItem menuItemGenerator;
    private Menu menuSort;
    private MenuItem menuItemSortByEntertainment;
    private MenuItem menuItemSortByFinance;
    private MenuItem menuItemSortByWork;
    private MenuItem menuItemSortBySchool;
    private MenuItem menuItemSortByPrivate;
    private MenuItem menuItemSortByUnassigned;


    @Override
    public void start(Stage primaryStage) throws Exception{

        //////////////////MENU///////////////////
        menuBar = new MenuBar();

        menuControls = new Menu("Entries");
        menuSave = new Menu("Save");
        menuUser = new Menu("User");
        menuTools = new Menu("Tools");
        menuSort = new Menu("Sort");
        menuItemSortByEntertainment = new MenuItem("Sort By Entertainment");
        menuItemSortByFinance = new MenuItem("Sort By Finance");
        menuItemSortByPrivate = new MenuItem("Sort By Private");
        menuItemSortBySchool = new MenuItem("Sort By School");
        menuItemSortByWork = new MenuItem("Sort By Work");
        menuItemSortByUnassigned = new MenuItem("Sort By Unassigned");
        menuItemSave = new MenuItem("Save");
        menuItemSaveAs = new MenuItem("Save As");
        menuItemAddEntry = new MenuItem("Add");
        menuItemRemoveEntry = new MenuItem("Remove");
        menuItemUpdateEntry = new MenuItem("Edit");
        menuItemClearFields = new MenuItem("Clear");
        menuItemLogOut = new MenuItem("Log Out");
        menuItemExit = new MenuItem("Exit");
        menuItemGenerator = new MenuItem("Generate Random Password");

        menuSort.getItems().add(menuItemSortByEntertainment);
        menuSort.getItems().add(menuItemSortByFinance);
        menuSort.getItems().add(menuItemSortByPrivate);
        menuSort.getItems().add(menuItemSortBySchool);
        menuSort.getItems().add(menuItemSortByWork);
        menuSort.getItems().add(menuItemSortByUnassigned);
        menuControls.getItems().add(menuItemAddEntry);
        menuControls.getItems().add(menuItemRemoveEntry);
        menuControls.getItems().add(menuItemUpdateEntry);
        menuControls.getItems().add(menuItemClearFields);
        menuSave.getItems().add(menuItemSave);
        menuSave.getItems().add(menuItemSaveAs);
        menuUser.getItems().add(menuItemLogOut);
        menuUser.getItems().add(menuItemExit);
        menuTools.getItems().add(menuItemGenerator);
        menuBar.getMenus().add(menuUser);
        menuBar.getMenus().add(menuSave);
        menuBar.getMenus().add(menuControls);
        menuBar.getMenus().add(menuSort);
        menuBar.getMenus().add(menuTools);



        ///////////LOGIN SCREEN//////////
        fileChooser = new FileChooser();
        //BTN
        btnLogin = new Button("Login");
        btnFileChoose = new Button("Choose File");
        //TXT
        txtPassword = new PasswordField();
        txtSelectedFile = new TextField("");
        //LBL
        lblPassword = new Label("Password: ");
        //IMG VIEW
        imageLogin = new Image(new FileInputStream("src/imgs/BankofOrgrimmar.png"));
        imageViewLogin = new ImageView(imageLogin);
        //BOXES & SCENE
        hBoxButtons = new HBox(5, btnLogin, btnFileChoose);
        vBoxLblLogin = new VBox(10, lblPassword, hBoxButtons);
        vBoxTxtLogin = new VBox(5, txtPassword, txtSelectedFile);
        hBoxLogin = new HBox(10, vBoxLblLogin, vBoxTxtLogin);
        loginPane = new GridPane();
        loginPane.add(hBoxLogin, 0, 1);
        loginPane.add(imageViewLogin, 0,0);
        loginPane.setAlignment(Pos.CENTER);
        //ALERTS
        alertWrongPassword = new Alert(Alert.AlertType.ERROR);
        alertWrongPassword.setTitle("fuck off :)");
        alertWrongPassword.setHeaderText("WRONG PASSWORD");
        alertWrongPassword.setContentText("EY, Stop stealing");
        alertNoPassword = new Alert(Alert.AlertType.ERROR);
        alertNoPassword.setTitle("Hello there");
        alertNoPassword.setHeaderText("NO PASSWORD ENTERED");
        alertNoPassword.setContentText("Need to set password or otherwise your bank will be raided");
        //SCENE
        sceneLogin = new Scene(loginPane);



        ////////////////DASHBOARD/////////////////
        //BTN
        btnAddEntry = new Button("Add Entry");
        btnRemoveEntry = new Button("Remove Entry");
        btnUpdateEntry = new Button("Update Entry");
        btnSaveEntries = new Button("Save");
        btnCopyTitle = new Button("Copy");
        btnCopyUsername = new Button("Copy");
        btnCopyPassword = new Button("Copy");
        btnOpenURL = new Button("Open");
        btnHidePassword = new ToggleButton("Hide/Reveal");
        tgPassword = new ToggleGroup();
        btnHidePassword.setToggleGroup(tgPassword);
        //COLORS
        backgroundFillWhite = new BackgroundFill(colorWhite, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundFillGrey = new BackgroundFill(colorGrey, CornerRadii.EMPTY, Insets.EMPTY);
        backgroundWhite = new Background(backgroundFillWhite);
        backgroundGrey = new Background(backgroundFillGrey);
        //LV
        observableList = FXCollections.observableArrayList();
        listView = new ListView<Entry>(observableList);
        //LBL
        lblName = new Label("Title: ");
        lblUsername = new Label("Username: ");
        lblPasswordDash = new Label("Password: ");
        lblWebsite = new Label("URL: ");
        lblNotes = new Label("Notes: ");
        lblCategory = new Label("Category: ");
        //TXT
        txtName = new TextField("");
        txtUsername = new TextField("");
        txtPasswordDash = new TextField();
        txtWebsite = new TextField("");
        txtNotes = new TextArea("");
        comboBoxCategory = new ComboBox();
        //COMBO BOX ITEMS
        comboBoxCategory.getItems().add(Category.ENTERTAINMENT);
        comboBoxCategory.getItems().add(Category.FINANCE);
        comboBoxCategory.getItems().add(Category.PRIVATE);
        comboBoxCategory.getItems().add(Category.SCHOOL);
        comboBoxCategory.getItems().add(Category.WORK);
        comboBoxCategory.getItems().add(Category.UNASSIGNED);
        //IMGVIEW
        imageCategory = new Image(new FileInputStream("src/imgs/questionMark.png"));
        imageViewCategory = new ImageView(imageCategory);
        //BOXES AND SCENES
        hBoxPasswordControls = new HBox(btnCopyPassword, btnHidePassword);
        vBoxCopyControls = new VBox(btnCopyTitle, btnCopyUsername, hBoxPasswordControls, btnOpenURL);
        vBoxLabels = new VBox(10, imageViewCategory, lblName, lblUsername, lblPasswordDash, lblWebsite, lblCategory, lblNotes);
        vBoxTxtFields = new VBox(1, txtName, txtUsername, txtPasswordDash, txtWebsite, comboBoxCategory, txtNotes);
        hBoxDetails = new HBox(10, vBoxLabels);
        hBoxMain = new HBox(20, listView, hBoxDetails, vBoxTxtFields, vBoxCopyControls);
        vBoxMain = new VBox(10, menuBar, hBoxMain);
        //SCENE
        sceneDashboard = new Scene(vBoxMain);



        /////////TWEAKS///////////
        txtSelectedFile.setEditable(false);
        txtPasswordDash.setBackground(backgroundGrey);
        //MARGIN
        vBoxTxtFields.setMargin(txtName, new Insets(66,0,0,0));
        hBoxMain.setMargin(listView, new Insets(0,0,0,10));
        hBoxMain.setMargin(vBoxCopyControls, new Insets(66, 0, 0, 0));
        loginPane.setMargin(hBoxLogin, new Insets(25, 0,0,15));
        //WIDTH & HEIGHT
        imageViewCategory.setFitHeight(56);
        imageViewCategory.setFitWidth(56);
        imageViewLogin.setFitHeight(180);
        imageViewLogin.setFitWidth(320);
        //WINDOW
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(500);
        //VARS
        comboBoxCategory.setValue(Category.UNASSIGNED);


//        sceneLogin.getStylesheets().add("main/style.css");
        primaryStage.setTitle("PWManager");
        primaryStage.setScene(sceneLogin);
        primaryStage.getIcons().add(new Image(new FileInputStream("src/imgs/monkaTOS.png")));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.show();



        //////////////////////////////
        ///////////EVENTS////////////
        ////////////////////////////

        listView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Entry> ov, Entry old_val, Entry new_val) -> {
                    if (new_val != null){
                        txtName.setText(new_val.getName());
                        txtUsername.setText(new_val.getUsername());
                        txtPasswordDash.setText(new_val.getPassword());
                        txtWebsite.setText(new_val.getWebsite());
                        txtNotes.setText(new_val.getNote());
                        comboBoxCategory.setValue(new_val.getCategory());


                        try {
                            if(comboBoxCategory.getValue().equals(Category.ENTERTAINMENT)){
                                imageCategory = new Image(new FileInputStream("src/imgs/ratGamble.png"));
                                imageViewCategory.setImage(imageCategory);
                            }else if(comboBoxCategory.getValue().equals(Category.FINANCE)){
                                imageCategory = new Image(new FileInputStream("src/imgs/gold.png"));
                                imageViewCategory.setImage(imageCategory);
                            }else if(comboBoxCategory.getValue().equals(Category.PRIVATE)){
                                imageCategory = new Image(new FileInputStream("src/imgs/safeHead.png"));
                                imageViewCategory.setImage(imageCategory);
                            }else if(comboBoxCategory.getValue().equals(Category.SCHOOL)){
                                imageCategory = new Image(new FileInputStream("src/imgs/peepoNerd.png"));
                                imageViewCategory.setImage(imageCategory);
                            }else if(comboBoxCategory.getValue().equals(Category.WORK)){
                                imageCategory = new Image(new FileInputStream("src/imgs/ratBusiness.png"));
                                imageViewCategory.setImage(imageCategory);
                            }else if(comboBoxCategory.getValue().equals(Category.UNASSIGNED)){
                                imageCategory = new Image(new FileInputStream("src/imgs/questionMark.png"));
                                imageViewCategory.setImage(imageCategory);
                            }
                        } catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }

        });

        primaryStage.setOnCloseRequest(event -> {
            try {
                if (currentFile != null){
                    saveToFile(currentFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //LOGIN
        btnLogin.setOnAction(event -> {
            try {
                checkForPassword();
            } catch (NoPasswordEnteredException e) {
                e.printStackTrace();
            }

            File file = new File(txtSelectedFile.getText());
            char[] password = txtPassword.getText().toCharArray();
            System.out.println(password);

            if (file != null){
                try {
                    if (!checkIfEmpty(file)){
                            Cipher cipher = Cipher.getInstance("AES");
                            System.out.println("FILE READY");
                            String[] saltAndData = getSaltAndData(file);
                            String salt = saltAndData[0];
                            String data = saltAndData[1];

                            SecretKey key = crypto.generateKey(KEY_TYPE, password, Base64.getDecoder().decode(salt));

                            currentPassword = password;
                            currentSalt = salt.getBytes(UNICODE_FORMAT);
                            currentFile = file;

                            try {
                                String decryptedString = crypto.decryptString(Base64.getDecoder().decode(data), key, cipher);
                                System.out.println(decryptedString);

                                entries = objectMapper.readValue(decryptedString, new TypeReference<ArrayList<Entry>>() {});
                                System.out.println(entries);

                                for (Entry e: entries){
                                    observableList.add(e);
                                }

                                listView.refresh();

                                primaryStage.setScene(sceneDashboard);

                            }catch (BadPaddingException | InvalidKeyException | IllegalBlockSizeException e){
                                e.printStackTrace();
                                System.out.println("WRONG PASSWORD ENTERED");
                                alertWrongPassword.show();
                            }
                    }
                    else{
                        if (checkForPassword()){
                            System.out.println("FILE EMPTY");
                            clearFile(file);
                            currentPassword = password;
                            currentSalt = crypto.generateSalt(currentPassword.toString());
                            currentFile = file;
                            listView.refresh();
                            primaryStage.setScene(sceneDashboard);
                        }
                    }
                } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException e){
                    e.printStackTrace();
                }catch (NoPasswordEnteredException e){
                    alertNoPassword.show();
                    e.printStackTrace();
                }
            }
        });

        btnFileChoose.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null){
                txtSelectedFile.setText(file.getAbsolutePath());
            }
        });


        //DASHBOARD

//        btnSaveEntries.setOnAction(event -> {
//            try {
//                saveToFile(currentFile);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });
//        btnAddEntry.setOnAction(event -> addEntry());
//        btnRemoveEntry.setOnAction(event -> removeEntry());
//        btnUpdateEntry.setOnAction(event -> updateEntry());

        menuItemExit.setOnAction(event -> exit(primaryStage));
        menuItemLogOut.setOnAction(event -> {
            try {
                logOut(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        menuItemRemoveEntry.setOnAction(event -> removeEntry());
        menuItemUpdateEntry.setOnAction(event -> updateEntry());
        menuItemAddEntry.setOnAction(event -> addEntry());
        menuItemClearFields.setOnAction(event -> clearFields());
        menuSave.setOnAction(event -> {
            try {
                saveToFile(currentFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        menuItemSaveAs.setOnAction(event -> {
            File chosenFile = fileChooser.showOpenDialog(primaryStage);

            if (chosenFile != null){
                try {
                    clearFile(chosenFile);
                    saveToFile(chosenFile);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
        menuItemGenerator.setOnAction(event -> {
            try {
                generatePassword();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        menuItemSortByFinance.setOnAction(event -> sortBy(Category.FINANCE));
        menuItemSortByEntertainment.setOnAction(event -> sortBy(Category.ENTERTAINMENT));
        menuItemSortByPrivate.setOnAction(event -> sortBy(Category.PRIVATE));
        menuItemSortBySchool.setOnAction(event -> sortBy(Category.SCHOOL));
        menuItemSortByWork.setOnAction(event -> sortBy(Category.WORK));
        menuItemSortByUnassigned.setOnAction(event -> sortBy(Category.UNASSIGNED));

        btnCopyTitle.setOnAction(event -> copyField(txtName));
        btnCopyUsername.setOnAction(event -> copyField(txtUsername));
        btnCopyPassword.setOnAction(event -> copyField(txtPasswordDash));
        btnHidePassword.setOnAction(event -> {
            System.out.println(tgPassword.getSelectedToggle());
            if (tgPassword.getSelectedToggle() != null){
                txtPasswordDash.setBackground(backgroundWhite);
            }else
                txtPasswordDash.setBackground(backgroundGrey);
        });

        btnOpenURL.setOnAction(event -> openWebpage(txtWebsite.getText()));
    }

    //CONTROLS
    public void updateEntry(){
        Entry entry = listView.getSelectionModel().getSelectedItem();

        entry.setName(txtName.getText());
        entry.setUsername(txtUsername.getText());
        entry.setPassword(txtPasswordDash.getText());
        entry.setWebsite(txtWebsite.getText());
        entry.setCategory((Category) comboBoxCategory.getValue());
        entry.setNote(txtNotes.getText());

    }

    public void addEntry(){
        String name = txtName.getText();
        String username = txtUsername.getText();
        String password = txtPasswordDash.getText();
        String website = txtWebsite.getText();
        Object category = comboBoxCategory.getValue();
        String notes = txtNotes.getText();

        if (category == null){
            category = Category.UNASSIGNED;
        }

        Entry entry = new Entry(name, username, password, notes, (Category) category, website);

        observableList.add(entry);
    }

    public void removeEntry(){
        observableList.remove(listView.getSelectionModel().getSelectedItem());
    }

    public void clearFields(){
        txtName.setText("");
        txtUsername.setText("");
        txtPasswordDash.setText("");
        txtWebsite.setText("");
        comboBoxCategory.setValue(Category.UNASSIGNED);
        txtNotes.setText("");
    }


    //OTHER
    /**
     * clears all vars that hold important information
     */
    public void clearCurrent(){
        currentFile = null;
        currentPassword = null;
        currentSalt = null;
        txtPassword.clear();
        txtSelectedFile.clear();
        observableList.clear();
        clearFields();
    }

    /**
     * check in-case the user forgets to set password for empty file
     * @return TRUE if password isn't null
     * @throws NoPasswordEnteredException when user selects an empty file but no password
     */
    public boolean checkForPassword() throws NoPasswordEnteredException {
        if(txtPassword.getText().trim().isEmpty()){
            throw new NoPasswordEnteredException("No password entered");
        }else
            return true;
    }

    /**
     * sorts list by category (asc)
     * @param category determines category
     */
    public void sortBy(Category category){
        Collections.sort(observableList, new ComparatorCategory(category));
    }


    //TWEAK FUNCTIONS
    /**
     * saves/encrypts list content into file
     * @param file determines what file is used
     */
    public void saveToFile(File file) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String jsonString = objectMapper.writeValueAsString(observableList);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        SecretKey key = crypto.generateKey(KEY_TYPE, currentPassword, currentSalt);
        Cipher cipher = Cipher.getInstance("AES");
        String encryptedString = Base64.getEncoder().encodeToString(crypto.encryptString(jsonString, key, cipher));

        bw.write(Base64.getEncoder().encodeToString(currentSalt));
        bw.newLine();
        bw.write(encryptedString);

        bw.close();
        fw.close();
    }

    /**
     * generates random password
     */
    public void generatePassword() throws UnsupportedEncodingException {
        SecureRandom RAND = new SecureRandom();
        String result = "";
        int index;
        for (int i = 0; i < 20; i++) {
            index = RAND.nextInt(alphabet.length());
            result += alphabet.charAt(index);
        }

        StringSelection selection = new StringSelection(result);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * content of a textField is copied into your clipboard
     * @param textField determines what textField is used
     */
    public void copyField(TextField textField){
        StringSelection selection = new StringSelection(textField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * exits the window
     * @param stage for closing the window
     */
    public void exit(Stage stage){
        try {
            if (entries != null && currentFile != null){
                saveToFile(currentFile);
            }
            stage.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * changes to login scene
     * @param stage for changing scene
     */
    public void logOut(Stage stage) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        saveToFile(currentFile);
        clearCurrent();
        stage.setScene(sceneLogin);
    }


    //FILE READING
    /**
     * checks if file is empty
     * @param file determines file
     * @return TRUE if file empty/ FALSE if file not empty
     */
    public boolean checkIfEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        if (br.readLine() == null) {
            br.close();
            return true;
        }
        else
            return false;
    }

    /**
     * flushes file
     * @param file determines file
     */
    public void clearFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.flush();
        writer.close();
    }

    /**
     * gets the salt and data from a file
     * @param file determines file
     * @return array[0] salt, array[1] content (list)
     */
    public String[] getSaltAndData(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String[] result = new String[2];

        for (int i = 0; i < 2; i++){
            result[i] = br.readLine();
        }

        return result;
    }

    /**
     * opens param in browser
     * @param url determines website
     */
    public void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL("https://" + url).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}