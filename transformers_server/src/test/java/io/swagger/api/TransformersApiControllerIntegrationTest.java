package io.swagger.api;

import io.swagger.model.Result;
import io.swagger.model.SpecName;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import io.swagger.api.TransformersApi;
import io.swagger.api.providers.ExampleData;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransformersApiControllerIntegrationTest {

	@Autowired
	private TransformersApi api;

	private ExampleData data;

	public TransformersApiControllerIntegrationTest() {
		this.data = new ExampleData();
	}
	//

	public void createTransformerTest(Transformer t) throws Exception {
		try {
			@SuppressWarnings("unused")
			ResponseEntity<Void> responseEntity = api.createTransformer(t);
			//code = responseEntity.getStatusCode();
		} catch (Exception e) {
			if (e instanceof ApiException) {

				final int h = ((ApiException) e).getCode();
				assertEquals(h, HttpStatus.CONFLICT);
				// assertEquals(true, codet == HttpStatus.CREATED.value() || code ==
				// HttpStatus.CONFLICT || code == HttpStatus.BAD_REQUEST);

			}
		}

	}

	// @Test
	public void createTransformerTest() throws Exception {
		createTransformerTest(data.t1);
		createTransformerTest(data.t2);
		createTransformerTest(data.t3);
		createTransformerTest(data.t4);
		createTransformerTest(data.t5);
	}

	// @Test
	public void deleteTransformerByNameTest() throws Exception {
		String name = data.t1.getName();
		ResponseEntity<Transformer> responseEntity = api.deleteTransformerByName(name);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().getName(), name);
		Transformer x = responseEntity.getBody();
		System.err.println("\n\tRestoring the deleted transformer = " + x.getName());//

		ResponseEntity<Void> responseEntity1 = api.createTransformer(responseEntity.getBody());
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
	}

	// @Test
	public void updateTransformerTest() throws Exception {
		final Transformer t = this.data.t1;
		final String key = SpecName.COURAGE.toString();
		final int value = t.getSpecs().get(key);
		t.getSpecs().put(key, value + 1);

		System.err.println("\n\tUpdating courage for transformer = " + t.getName());//

		ResponseEntity<Transformer> responseEntity = api.updateTransformer(t);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		final int k = responseEntity.getBody().getSpecs().get(key);
		assertEquals(value, k);
	}

	public void listAllTransformersTest() throws Exception {
		ResponseEntity<Transformers> responseEntity = api.listAllTransformers();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		printList(responseEntity.getBody());
	}

	void printList(Transformers list) {

		System.err.print("\n#########\tList.size = " + list.size());
		list.stream().forEach(x -> //
		System.err.print("\n\ttransformer = " + ExampleData.toString(x)) //
		);
		System.err.println();
	}

	public void listTransformersTest() throws Exception {

		String team = TeamName.AUTOBOT.toString();
		ResponseEntity<Transformers> responseEntity = api.listTransformers(team);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		printList(responseEntity.getBody());

		team = TeamName.DECEPTICON.toString();
		responseEntity = api.listTransformers(team);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		printList(responseEntity.getBody());

	}

	// @Test
	public void initiateBattleTest() throws Exception {
		List<String> names = Arrays.asList(data.t1.getName(), data.t3.getName());
		List<String> ids = Arrays.asList(data.t2.getId());
		System.err.println(names);
		System.err.println(ids);
		ResponseEntity<Result> responseEntity = api.initiateBattle(names, ids);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		final Result r = responseEntity.getBody();
		assertEquals(1, r.getBattle().intValue());
		assertEquals(TeamName.DECEPTICON, r.getWinner().getName());
		assertEquals(TeamName.AUTOBOT, r.getSurvivors().getName());

		assertEquals(1, r.getWinner().getMembers().size());
		assertEquals(1, r.getSurvivors().getMembers().size());

		final String rw = ExampleData.toString(r.getWinner().getMembers().get(0));
		final String rs = ExampleData.toString(r.getSurvivors().getMembers().get(0));

		final String t1 = ExampleData.toString(data.t1);
		final String t3 = ExampleData.toString(data.t3);

		assertEquals(t1, rw); // Soundwave
		assertEquals(t3, rs); // Hubcap

	}

	@Test
	public void mainTest() throws Exception {
		createTransformerTest();
		listAllTransformersTest();
		deleteTransformerByNameTest();
		updateTransformerTest();
		listAllTransformersTest();
		listTransformersTest();
		initiateBattleTest();
	}
}
