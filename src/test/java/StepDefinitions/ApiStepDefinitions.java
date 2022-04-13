package StepDefinitions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class ApiStepDefinitions {

    Response response;
    String url = "https://reqres.in";

    @Given("existing server application https:\\/\\/reqres.in\\/api")
    public void existing_server_application_https_reqres_in_api() {
        response = RestAssured.get(url);
    }

    @Then("on GET request to \\/users it returns expected users list")
    public void on_GET_request_to_users_it_returns_expected_users_list() {


        given().accept(ContentType.JSON)
                .when().get("https://reqres.in/api/users")
                .then().assertThat().body(
                "data.id",hasItems(1,2,3,4,5,6),
                "data.first_name",hasItems("George","Janet","Emma","Eve","Charles","Tracey"),
                "data.last_name",hasItems("Bluth","Weaver","Wong","Holt","Morris","Ramos"));
    }





    @Then("on GET request to \\/users\\/{int} it returns expected welcome message")
    public void on_GET_request_to_users_it_returns_expected_welcome_message(Integer user_id) {
        Map<String, Object> userInfo = given().accept(ContentType.JSON)
                .and().pathParam("id",user_id)
                .when().get(url+user_id)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().extract().body().as(Map.class);

        assertThat(userInfo.get("id"),equalTo(2));
        assertThat(userInfo.get("email"),equalTo("janet.weaver@reqres.in"));
        assertThat(userInfo.get("first_name"),equalTo("Janet"));
        assertThat(userInfo.get("last_name"),equalTo("Weaver"));
        assertThat(userInfo.get("avatar"),equalTo("https://reqres.in/img/faces/2-image.jpg"));

    }


    @Then("on GET request to \\/users\\/{int} it returns {int} status code")
    public void on_GET_request_to_users_it_returns_status_code(Integer int1, Integer int2) {

    }





}
