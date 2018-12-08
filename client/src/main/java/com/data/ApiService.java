package com.data;

import com.entities.*;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {
    //AccountController
    @GET("/accounts/")
    Single<List<Account>> getAllAccounts();

    @GET("/clients/{clientId}/accounts")
    Single<List<Account>> getAllAccountsByClientId(@Path("clientId") String id);

    @GET("/departments/{departmentId}/accounts/")
    Single<List<Account>> getAllAccountsByDepartmentId(@Path("departmentId") String id);

    @POST("/departments/{departmentId}.{clientId}/accounts/")
    Single<Account> createAccount(@Path("departmentId") String departmentId, @Path("clientId") String clientId,
                                  @Body Account account);

    @PUT("/accounts/{accountId}")
    Single<Account> updateAccount(@Path("accountId") String id, @Body Account account);

    @DELETE("/accounts/{accountId}")
    Completable deleteAccount(@Path("accountId") String id);

    //ClientController
    @GET("/clients/")
    Single<List<Client>> getAllClients();

    @GET("/departments/{departmentId}/clients/")
    Single<List<Client>> getAllClientsByDepartmentId(@Path("departmentId") String id);

    @POST("/departments/{departmentId}/clients/")
    Single<Client> createClient(@Path("departmentId") String id, @Body Client client);

    @PUT("/clients/{clientId}")
    Single<Client> updateClient(@Path("clientId") String id, @Body Client client);

    @DELETE("/clients/{clientId}")
    Completable deleteClient(@Path("clientId") String id);

    //ContactController
    @GET("/contacts/")
    Single<List<Contact>> getAllContacts();

    @GET("/clients/{clientId}/contacts/")
    Single<List<Contact>> getAllContactsByClientId(@Path("clientId") String id);

    @POST("/clients/{clientId}/contacts/")
    Single<Contact> createContact(@Path("clientId") String id, @Body Contact contact);

    @PUT("/contacts/{contactId}")
    Single<Contact> updateContact(@Path("contactId") String id, @Body Contact contact);

    @DELETE("/contacts/{contactId}")
    Completable deleteContact(@Path("contactId") String id);

    //DepartmentController
    @GET("/departments/")
    Single<List<Department>> getAllDepartments();

    @POST("/departments/")
    Single<Department> createDepartment(@Body Department department);

    @PUT("/departments/{departmentId}")
    Single<Department> updateDepartment(@Path("departmentId") String id, @Body Department department);

    @DELETE("/departments/{departmentId}")
    Completable deleteDepartment(@Path("departmentId") String id);

    //OperationController
    @GET("/operations/")
    Single<List<Operation>> getAllOperations();

    @GET("/accounts/{accountId}/operations/")
    Single<List<Operation>> getAllOperationsByAccountId(@Path("accountId") String id);

    @POST("/accounts/{accountId}/operations/")
    Single<Operation> createOperation(@Path("accountId") String id, @Body Operation operation);

    @PUT("/operations/{operationId}")
    Single<Operation> updateOperation(@Path("operationId") String id, @Body Operation operation);

    @DELETE("/operations/{operationId}")
    Completable deleteOperation(@Path("operationId") String id);
}
