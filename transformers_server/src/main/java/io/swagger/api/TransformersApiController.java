package io.swagger.api;

import io.swagger.model.Result;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.api.providers.ApiServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-11T12:42:06.333Z[GMT]")
@Controller
public class TransformersApiController implements TransformersApi {

	private static final Logger log = LoggerFactory.getLogger(TransformersApiController.class);

	@SuppressWarnings("unused")
	private final ObjectMapper objectMapper;

	@SuppressWarnings("unused")
	// commented out all checks for "Accept" request header
	// and "application/json" in the request header
	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	private ApiServiceProvider service;

	@org.springframework.beans.factory.annotation.Autowired
	public TransformersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<Void> createTransformer(@ApiParam(value = "") @Valid @RequestBody Transformer body) {
//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {
		try {
			HttpStatus v = service.createTransformer(body);

			return new ResponseEntity<Void>(v);
		} catch (ApiException e) {
			System.err.println(e);
			log.error("createTransformer!", e);
			return new ResponseEntity<Void>(HttpStatus.valueOf(e.getCode()));

		}
//		}
//		return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Transformer> updateTransformer(@ApiParam(value = "") @Valid @RequestBody Transformer body) {

//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {
		try {
			return new ResponseEntity<Transformer>(service.updateTransformer(body), HttpStatus.OK);
		} catch (ApiException e) {
			log.error("updateTransformer!", e);
			if (e.getCode() == 409) {
				// get conflicting
				Transformer conflicting = service.conflictingTransformer(body);
				return new ResponseEntity<Transformer>(conflicting, HttpStatus.CONFLICT);

			}
			return new ResponseEntity<Transformer>(HttpStatus.valueOf(e.getCode()));
		}
//		}
//
//		return new ResponseEntity<Transformer>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Transformer> deleteTransformerByName(
			@ApiParam(value = "transformer's unique name", required = true) @PathVariable("name") String name) {
//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {
		try {
			return new ResponseEntity<Transformer>(service.deleteTransformerByName(name), HttpStatus.OK);
		} catch (ApiException e) {
			log.error("deleteTransformerByName!", e);
			return new ResponseEntity<Transformer>(HttpStatus.valueOf(e.getCode()));
		}
//		}
//		return new ResponseEntity<Transformer>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Transformers> listAllTransformers() {
//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {
		try {
			final ResponseEntity<Transformers> r = new ResponseEntity<Transformers>(service.listTransformers(null),
					HttpStatus.OK);
			if (r.getBody().isEmpty()) {
				return new ResponseEntity<Transformers>(HttpStatus.NO_CONTENT);
			}
			return r;
		} catch (ApiException e) {
			log.error("listAllTransformers!", e);
			return new ResponseEntity<Transformers>(HttpStatus.valueOf(e.getCode()));
		}
//		}
//
//		return new ResponseEntity<Transformers>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Transformers> listTransformers(
			@ApiParam(value = "transformers team name", required = true) @PathVariable("team") String team) {
//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {

		if (team != null && !(team.trim().equalsIgnoreCase(TeamName.AUTOBOT.toString())
				|| team.trim().equalsIgnoreCase(TeamName.DECEPTICON.toString()) 
				|| team.trim().equalsIgnoreCase("A")
				|| team.trim().equalsIgnoreCase("D")

		)) {
			log.error("Bad input Error! listAllTransformers for team \"" + team + "\"!");
			return new ResponseEntity<Transformers>(HttpStatus.BAD_REQUEST);
		}
		try {
			final ResponseEntity<Transformers> r = new ResponseEntity<Transformers>(service.listTransformers(team),
					HttpStatus.OK);
			if (r.getBody().isEmpty()) {
				return new ResponseEntity<Transformers>(HttpStatus.NO_CONTENT);
			}
			return r;
		} catch (ApiException e) {
			log.error("listAllTransformers for team \"" + team + "\"!", e);
			return new ResponseEntity<Transformers>(HttpStatus.valueOf(e.getCode()));
		}
//		}
//		return new ResponseEntity<Transformers>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Result> initiateBattle(
			@ApiParam(value = "") @Valid @RequestParam(value = "names", required = false) List<String> names,
			@ApiParam(value = "") @Valid @RequestParam(value = "ids", required = false) List<String> ids) {
//		String accept = request.getHeader("Accept");
//		if (accept != null && accept.contains("application/json")) {
		try {
			return new ResponseEntity<Result>(service.initiateBattle(names, ids), HttpStatus.OK);
		} catch (ApiException e) {
			log.error("Error! initiateBattle: ", e);
			return new ResponseEntity<Result>(HttpStatus.valueOf(e.getCode()));
		}
//		}
//
//		return new ResponseEntity<Result>(HttpStatus.NOT_IMPLEMENTED);
	}
}
