package com;

import com.data.ApiService;
import com.data.ApiServiceProvider;
import com.entities.*;
import io.reactivex.Completable;
import io.reactivex.Single;

import javax.swing.*;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BankClientForm extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel GUIForm;
    private JPanel Clients;
    private JPanel Accounts;
    private JTextArea textArea;

    private JButton getAllAccountsButton;
    private JButton getAllClientsButton;
    private JButton getAccountsByClientIdButton;
    private JButton getAllOperationsButton;
    private JButton getAllContactsButton;
    private JButton getContactsByClientIdButton;
    private JButton createClientButton;
    private JButton getOperationsByAccountIdButton;

    private JTextField contactsByClientId;
    private JTextField operationsByAccountId;

    private JTextField clientsDepartment;
    private JTextField clientsName;
    private JTextField accountsByClientId;
    private JButton getAccountsByDepartmentId;
    private JTextField accountsByDepartmentId;
    private JButton getClientsByDepartmentIdButton;
    private JTextField clientsByDepartmentId;
    private JButton getAllDepartmentsButton;
    private JButton createAccount;
    private JTextField accountsDepartment;
    private JTextField accountsClient;
    private JTextField accountsBalance;
    private JTextField accountsTypeTitle;
    private JTextField accountsTypeInformation;
    private JButton createDepartment;
    private JTextField departmentsTitle;
    private JTextField departmentsAddress;
    private JButton createContact;
    private JLabel labellll;
    private JTextField contactsAddress;
    private JButton createOperation;
    private JLabel labelwer;
    private JTextField operationsAmount;
    private JComboBox<Operation.Type> operationsType;
    private JLabel labelef;
    private JTextField operationsAccount;
    private JTextField contactsClientId;
    private JButton updateContact;
    private JTextField contactsId;
    private JButton updateOperation;
    private JTextField operationsId;
    private JButton updateDepartment;
    private JTextField departmentsId;
    private JButton updateAccount;
    private JTextField accountsId;
    private JButton updateClient;
    private JTextField clientsId;
    private JButton deleteClient;
    private JButton deleteAccount;
    private JButton deleteDepartment;
    private JButton deleteOperation;
    private JButton deleteContact;

    private ApiService apiService;

    BankClientForm() {
        setContentPane(GUIForm);
        setVisible(true);
        setSize(636, 535);

        operationsType.addItem(Operation.Type.CHARGE);
        operationsType.addItem(Operation.Type.WITHDRAW);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        apiService = ApiServiceProvider.getInstance();

        ///AccountController
        getListRequest(getAllAccountsButton, () -> apiService.getAllAccounts());
        getListRequest(getAccountsByClientIdButton, () -> apiService.getAllAccountsByClientId(accountsByClientId.getText()));
        getListRequest(getAccountsByDepartmentId, () -> apiService.getAllAccountsByDepartmentId(accountsByDepartmentId.getText()));

        modifyRequest(createAccount, () -> apiService.createAccount(accountsDepartment.getText(), accountsClient.getText(),
                new Account(Long.valueOf(accountsBalance.getText()), new AccountType(
                        accountsTypeTitle.getText(),
                        accountsTypeInformation.getText(),
                        "qwerty", AccountType.Type.CURRENT_ACCOUNT, "less 440"))));

        modifyRequest(updateAccount, () -> apiService.updateAccount(accountsId.getText(),
                new Account(Long.valueOf(accountsBalance.getText()), new AccountType(
                        accountsTypeTitle.getText(),
                        accountsTypeInformation.getText(),
                        "ytrewq", AccountType.Type.ANOTHER_ACCOUNT, "more 440"))));

        deleteRequest(deleteAccount, () -> apiService.deleteAccount(accountsId.getText()));

        //ClientController
        getListRequest(getAllClientsButton, () -> apiService.getAllClients());
        getListRequest(getClientsByDepartmentIdButton, () -> apiService.getAllClientsByDepartmentId(clientsByDepartmentId.getText()));

        modifyRequest(createClientButton, () -> apiService.createClient(clientsDepartment.getText(), new Client(clientsName.getText())));

        modifyRequest(updateClient, () -> apiService.updateClient(clientsId.getText(), new Client(clientsName.getText())));

        deleteRequest(deleteClient, () -> apiService.deleteClient(clientsId.getText()));

        //ContactController
        getListRequest(getAllContactsButton, () -> apiService.getAllContacts());
        getListRequest(getContactsByClientIdButton, () -> apiService.getAllContactsByClientId(contactsByClientId.getText()));

        modifyRequest(createContact, () -> apiService.createContact(contactsClientId.getText(),
                new Contact(contactsAddress.getText())));

        modifyRequest(updateContact, () -> apiService.updateContact(contactsId.getText(),
                new Contact(contactsAddress.getText())));

        deleteRequest(deleteContact, () -> apiService.deleteContact(contactsId.getText()));

        //DepartmentController
        getListRequest(getAllDepartmentsButton, () -> apiService.getAllDepartments());

        modifyRequest(createDepartment, () -> apiService.createDepartment(
                new Department(departmentsTitle.getText(), departmentsAddress.getText())));

        modifyRequest(updateDepartment, () -> apiService.updateDepartment(departmentsId.getText(),
                new Department(departmentsTitle.getText(), departmentsAddress.getText())));

        deleteRequest(deleteDepartment, () -> apiService.deleteDepartment(departmentsId.getText()));

        //OperationController
        getListRequest(getAllOperationsButton, () -> apiService.getAllOperations());
        getListRequest(getOperationsByAccountIdButton, () -> apiService.getAllOperationsByAccountId(operationsByAccountId.getText()));

        modifyRequest(createOperation, () -> apiService.createOperation(operationsAccount.getText(),
                new Operation((Operation.Type) operationsType.getSelectedItem(), Long.valueOf(operationsAmount.getText()))));

        modifyRequest(updateOperation, () -> apiService.updateOperation(operationsId.getText(),
                new Operation((Operation.Type) operationsType.getSelectedItem(), Long.valueOf(operationsAmount.getText()))));

        deleteRequest(deleteOperation, () -> apiService.deleteOperation(operationsId.getText()));

    }

    interface Action<T extends Single<?>> {
        T perform();
    }

    interface CompletableAction<T extends Completable> {
        T perform();
    }

    private <T extends Completable> void deleteRequest(JButton button, CompletableAction<T> action) {
        button.addActionListener(__ -> action.perform()
                .subscribe(() -> showResult("Удалено"), throwable -> {
                    throwable.printStackTrace();
                    showResult(throwable.getMessage());
                }));
    }

    private <V, T extends Single<V>> void modifyRequest(JButton button, Action<T> action) {
        button.addActionListener(__ -> action.perform()
                .subscribe(values -> showResult(values.toString()), throwable -> {
                    throwable.printStackTrace();
                    showResult(throwable.getMessage());
                }));
    }

    private <V, T extends Single<List<V>>> void getListRequest(JButton button, Action<T> action) {
        button.addActionListener(__ -> action.perform()
                .subscribe(values -> showResult(printList(values)), throwable -> {
                    throwable.printStackTrace();
                    showResult(throwable.getMessage());
                }));
    }

    private void showResult(String str) {
        textArea.setText(str);
    }

    private <T> String printList(List<T> list) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Список {").append("\n");
        for (T value : list) {
            stringBuilder.append("   ").append(value.toString()).append("\n");
        }
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

}
