package project.discspring.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.discspring.model.Disc;
import project.discspring.model.form.DiscForm;
import project.discspring.model.form.DiscFormUpdate;
import project.discspring.service.Impl.DiscServiceImpl;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainDiscController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeStateButton(true);
        state = "neutral";
        loadTableDiscs();


        adjustingData();
    }

// -----------------------------------------Table Elements-------------------------------------------------------------

    private List<Disc> discsList;

    private ObservableList<Disc> observableList;
    @FXML
    private TableView<Disc> tableDiscs;

    @FXML
    private TableColumn<Disc, String> columnName;

    @FXML
    private TableColumn<Disc, Integer> columnNum;

    @FXML
    private TableColumn<Disc, String> columnSinger;

    private Disc selectedDisc;

// --------------------------------------Generics Elements-----------------------------------------------------------

    private String state;


    private final DiscServiceImpl service;

    int index;

    Long id = 3L;

    @FXML
    private Label bestSinger;

    @FXML
    private Label maxDiscs;

    @FXML
    private List<TextField> texFieldList ;

    @FXML
    private List<Button> buttonDisc;

    @FXML
    private List<Button> buttonControl;
    @FXML
    private Button bAdd;

    @FXML
    private Button bData;

    @FXML
    private Button bDelete;

    @FXML
    private Button bEdit;

    @FXML
    private Button bConfirm;

    @FXML
    private Button bCancel;

    @FXML
    private Button bClean;

    @FXML
    private TextField name_area;

    @FXML
    private TextField num_area;

    @FXML
    private TextField singer_area;

// -----------------------------------------Generic Methods---------------------------------------------------------

    /**
     *
     * @param event
     * Method of the button Confirm
     * Add a new disc to the table or confirm the edit of an element
     */
    @FXML
    void addDisc(MouseEvent event) {

        boolean flag = true;
        if(!name_area.getText().equals("") && !singer_area.getText().equals("") && !num_area.getText().equals("")){

            String name = name_area.getText();
            String singer = singer_area.getText();
            int num= 0;

            try {
                num = Integer.parseInt(num_area.getText());
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro de digitação, o campo número precisa ser um número");
                alert.showAndWait();
                flag = false;
            }

            if(flag){
                if(state.equals("edit")){
                    Disc disc = service.get(selectedDisc.getId());
                    DiscFormUpdate discUpdate = new DiscFormUpdate();

                    disc.setName(name_area.getText());
                    disc.setSinger(singer_area.getText());
                    disc.setNum(Integer.valueOf(num_area.getText()));

                    discUpdate.setName(disc.getName());
                    discUpdate.setSinger(disc.getSinger());
                    discUpdate.setNum(disc.getNum());
                    discUpdate.setId(disc.getId());

                    service.update(discUpdate);
                    tableDiscs.getItems().set(index,disc);

                }else {
                    DiscForm discForm = new DiscForm();
                    discForm.setName(name);
                    discForm.setSinger(singer);
                    discForm.setNum(num);
                    service.create(discForm);

                    Disc newDisc = new Disc(name, singer, num);
                    discsList.add(newDisc);
                }
                addTableElement();
                changeStateButton(true);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro de digitação, os campos precisam ser preenchidos");
            alert.showAndWait();
            flag = false;
        }

        if(flag){
            cleanTextAreas();
        }

        state = "neutral";
        adjustingData();
    }

    /**
     *
     * @param b boolean param that determine if a specific group of buttons is enabled or disable
     */
    private void changeStateButton(boolean b){
        for(Button button: buttonDisc){
            button.setDisable(b);
        }

        for(TextField textField: texFieldList){
            textField.setDisable(b);
        }

        for (Button button : buttonControl) {
            button.setDisable(!b);
        }


        tableDiscs.setDisable(!b);
    }

    /**
     *
     * @param event
     * Prepares the data entry area, clearing the text boxes
     */
    @FXML
    void startProcess(MouseEvent event) {
        cleanTextAreas();
        changeStateButton(false);
    }

    /**
     *
     * @param event
     * Prepares the data entry area, clearing the text boxes
     * The difference between this method and the startProcess method is that the latter
     * enables the main buttons and the startProcess disables them.
     */
    @FXML
    void cancel(MouseEvent event) {
        cleanTextAreas();
        changeStateButton(true);
        state = "neutral";
    }

    /**
     *
     * @param event this param call the method cleanTextAreas
     */
    @FXML
    void cleanText(MouseEvent event) {
        cleanTextAreas();
    }

    /**
     * Set the input data text area text to an empty String
     */
    private void cleanTextAreas(){
        name_area.setText("");
        singer_area.setText("");
        num_area.setText("");
    }

    /**
     *
     * @param event
     * Get the id of the selected item and delete this item of the table
     */
    @FXML
    void deleteDisc(MouseEvent event) {
        if(selectedDisc ==  null){
            Alert alertSelection = new Alert(Alert.AlertType.ERROR, "É necessário escolher um item para deletar");
            alertSelection.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você quer mesmo apagar o elemento: "+selectedDisc.getName()+"?"
                    ,ButtonType.YES, ButtonType.NO);
            alert.setHeaderText("Deletar item");
            Optional<ButtonType> choice = alert.showAndWait();

            if(choice.get() == ButtonType.YES){
                service.delete(selectedDisc.getId());
                discsList.remove(selectedDisc);
                tableDiscs.getItems().remove(selectedDisc);
                loadTableDiscs();
            }
        }
        adjustingData();
        cleanTextAreas();
    }

    /**
     *
     * @param event
     * Adjusts the state of the main buttons and changing the main state to edit
     */
    @FXML
    void editDisc(MouseEvent event) {
        if(selectedDisc ==  null){
            Alert alertSelection = new Alert(Alert.AlertType.ERROR, "É necessário escolher um item para deletar");
            alertSelection.showAndWait();
        }else{
            state = "edit";
            changeStateButton(false);
        }
        adjustingData();
    }

    /**
     *
     * @param event the disc that you selected in the table
     */
    @FXML
    void selectCellTable(MouseEvent event) {
        index = tableDiscs.getSelectionModel().getSelectedIndex();
        if(index >= 0 && index < discsList.size()){
            selectedDisc = discsList.get(index);
            name_area.setText(selectedDisc.getName());
            singer_area.setText(selectedDisc.getSinger());
            num_area.setText(String.valueOf(selectedDisc.getNum()));
        }
    }

    /**
     * Adjusts the text of the data area, updating after a new insert of a disc
     */
    private void adjustingData(){
        String[] singer = new String[1];
        maxDiscs.setText(String.valueOf(tableDiscs.getItems().size()));

        tableDiscs.getItems().stream()
                .map(Disc::getSinger)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(s -> singer[0] = String.valueOf(s));

        if(singer[0] != null){
            bestSinger.setText(singer[0].replaceAll("[^A-Za-z]+", ""));
        }else {
            bestSinger.setText("Sem cantor favorito");
        }

        loadTableDiscs();
    }


//  -------------------------------------------Table Methods--------------------------------------------------------

    /**
     * Update the observableList and put the new information in the table
     */
    private void addTableElement(){
        observableList = FXCollections.observableArrayList(discsList);
        tableDiscs.setItems(observableList);
    }

    private void stanceList(){
        discsList = new ArrayList<>();
    }

    private void loadTableDiscs(){
        discsList = service.getAll();
        if(discsList == null){
            stanceList();
            discsList.add(new Disc("O disco","Lucas",123));
        }
        observableList = FXCollections.observableArrayList(discsList);

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnSinger.setCellValueFactory(new PropertyValueFactory<>("singer"));
        columnNum.setCellValueFactory(new PropertyValueFactory<>("num"));

        tableDiscs.setItems(observableList);
    }
}