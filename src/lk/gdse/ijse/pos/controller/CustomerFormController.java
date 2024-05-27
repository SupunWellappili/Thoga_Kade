package lk.gdse.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.gdse.ijse.pos.db.Database;
import lk.gdse.ijse.pos.modal.Customer;
import lk.gdse.ijse.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public Button btnSaveCustomer;
    public AnchorPane customerFormContext;

    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchCustomer();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue){ //newValue != null -> Late (null akak new object aka nullda kiynawa wada late new object akak null da kiyla blnna)
            setData(newValue);
            }
        });
    }

    private void setData(CustomerTM tm) {
        txtID.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtSalary.setText(String.valueOf(tm.getSalary()));//get  Double .Return String type
        btnSaveCustomer.setText("Update Customer");

    }


    private void searchCustomer() {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        for (Customer c : Database.customerTable) {
            Button btn = new Button("Delete");
            CustomerTM tm = new CustomerTM(c.getId(), c.getName(), c.getAddress(), c.getSalary(), btn);
            tmList.add(tm);


            btn.setOnAction(event -> {
                // System.out.println(c.getId());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You sure whether do you want to delete this Customer?", ButtonType.YES, ButtonType.NO);
                //alert.show();
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {

                    boolean isDeleted = Database.customerTable.remove(c);
                    if (isDeleted) {
                        searchCustomer();
                        clearFields();
                        new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                    }
                }
            });
        }

        tblCustomer.setItems(tmList);


    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(txtID.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));

        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")) {
            //save
            boolean isSaved = Database.customerTable.add(c1);
            if (isSaved) {
                searchCustomer();
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved!").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } else {
            //update
            for (int i = 0; i < Database.customerTable.size(); i++) {
                if (txtID.getText().equalsIgnoreCase(Database.customerTable.get(i).getId())) {
                    Database.customerTable.get(i).setName(txtName.getText());
                    Database.customerTable.get(i).setAddress(txtAddress.getText());
                    Database.customerTable.get(i).setSalary(Double.parseDouble(txtSalary.getText()));
                    searchCustomer();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                    clearFields();
                }
            }
        }
    }

    private void clearFields() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();

    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../pos/view/DashBoardForm.fxml"))));
        stage.show();
    }
}
