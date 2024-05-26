package lk.gdse.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.gdse.ijse.pos.db.Database;
import lk.gdse.ijse.pos.modal.Customer;
import lk.gdse.ijse.pos.view.tm.CustomerTM;

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

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchCustomer();
    }

    private void searchCustomer(){
        ObservableList<CustomerTM>  tmList =FXCollections.observableArrayList();
        for (Customer c :Database.customerTable) {
            Button btn =new Button("Delete");
            CustomerTM tm = new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn);
            tmList.add(tm);
        }

        tblCustomer.setItems(tmList);
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(txtID.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        boolean isSaved = Database.customerTable.add(c1);
        if (isSaved) {
            searchCustomer();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Customer saved!").show();

        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
        }
    }

    private void clearFields(){
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();

    }
}
