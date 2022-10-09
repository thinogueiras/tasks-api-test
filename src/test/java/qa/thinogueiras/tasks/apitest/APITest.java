package qa.thinogueiras.tasks.apitest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;


public class APITest {
	
	@BeforeAll
	public static void setup() {
		baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTask() {
		given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTaskComSucesso() {
		given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2030-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()			
			.statusCode(201);
	}
	
	@Test
	public void nãoDeveAdicionarTaskComDataInválida() {
		given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()			
			.statusCode(400)
			.body("message", is("Due date must not be in past"));
	}
	
	@Test
	public void nãoDeveAdicionarTaskComDescriçãoNula() {
		given()
			.body("{ \"task\": \"\", \"dueDate\": \"2030-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()			
			.statusCode(400)
			.body("message", is("Fill the task description"));
	}
	
	@Test
	public void nãoDeveAdicionarTaskComDataNula() {
		given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()			
			.statusCode(400)
			.body("message", is("Fill the due date"));
	}	
}