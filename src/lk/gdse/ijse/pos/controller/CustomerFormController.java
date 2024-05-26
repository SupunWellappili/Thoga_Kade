package lk.gdse.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.gdse.ijse.pos.db.Database;
import lk.gdse.ijse.pos.modal.Customer;
import lk.gdse.ijse.pos.view.tm.CustomerTM;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public void initialize(){
        searchCustomer();
    }

    private void searchCustomer(){
        ObservableList<CustomerTM>  tmList =FXCollections.observableArrayList();
        for (Customer c :Database.customerTable) {
            Button btn = new Button("Delete");
            CustomerTM tm = new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn);
            tmList.add();
        }

    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer c1 = new Customer(txtID.getText(), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        boolean isSaved = Database.customerTable.add(c1);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Customer saved!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
        }
    }
}
