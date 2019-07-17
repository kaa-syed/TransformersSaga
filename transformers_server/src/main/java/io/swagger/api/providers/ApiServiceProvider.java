/**
 * 
 */
package io.swagger.api.providers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import io.swagger.api.ApiException;
import io.swagger.model.Result;
import io.swagger.model.SpecName;
import io.swagger.model.Team;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;
import static io.swagger.api.providers.TransformerBattle.getTransformersByNames;
import static io.swagger.api.providers.TransformerBattle.getTransformersByTeam;
import static io.swagger.api.providers.TransformerBattle.getTransformersById;
import static io.swagger.api.providers.TransformerBattle.rating;
import static io.swagger.api.providers.TransformerBattle.battle;
import static io.swagger.api.providers.TransformerBattle.validTeamName;

/**
 * @author Kamran A.A. Syed
 *
 */
@Service
public class ApiServiceProvider {
	private static final Logger log = LoggerFactory.getLogger(ApiServiceProvider.class);
	private final Transformers masterList;

	void addingSampleData() {
		ExampleData data = new ExampleData();
		try {
			createTransformer(data.t1);
			createTransformer(data.t2);
			createTransformer(data.t3);

		} catch (ApiException e) {
			// e.printStackTrace();
		}

	}

	public ApiServiceProvider() {
		this.masterList = new Transformers();
		addingSampleData();
	}

	public final Transformers getMasterList() {
		return this.masterList;
	}

	public Transformer deleteTransformerByName(final String name) throws ApiException {
		if (name == null || name.trim().isEmpty()) {
			ApiException e = new ApiException(400,
					"Bad request for deleting transformer! \"" + name + "\" must be a valid transformer name");
			throw e;
		}
		final Transformers tlist = getTransformersByNames(this.masterList, name);
		if (tlist.isEmpty()) {
			ApiException e = new ApiException(204, "Nothing to delete! No transformer exist with name = " + name);
			throw e;
		}
		final Transformer t = tlist.get(0);
		if (this.masterList.remove(t)) {
			return t;
		}
		ApiException e = new ApiException(500, "Internal error! Unable to delete transformer \"" + name + "\".");
		throw e;
	}

	public Result initiateBattle(final List<String> names, final List<String> ids) throws ApiException {
		final String[] a = names == null || names.isEmpty() ? null : names.toArray(new String[0]);
		final String[] b = ids == null || ids.isEmpty() ? null : ids.toArray(new String[0]);

		final Transformers battleList = new Transformers();
		if (a == null && b == null) {
			battleList.addAll(this.masterList);
		} else {
			if (a != null) {
				final Transformers t = getTransformersByNames(this.masterList, a);
				if (!t.isEmpty())
					battleList.addAll(t);
			}
			if (b != null) {
				final Transformers t = getTransformersById(this.masterList, b);
				if (!t.isEmpty())
					battleList.addAll(t);
			}
		}
		if (battleList.isEmpty()) {
			String msg = "Bad Inputs! no transformers for the names and ids";
			final ApiException e = new ApiException(400, msg);
			throw e;
		}
		final Transformers aList = getTransformersByTeam(battleList, TeamName.AUTOBOT.toString());
		final Transformers dList = getTransformersByTeam(battleList, TeamName.DECEPTICON.toString());
		if (aList.isEmpty() || dList.isEmpty()) {
			String msg = "Bad Inputs! no face offs for battling transformers";
			final ApiException e = new ApiException(400, msg);
			throw e;
		}
		// ready to rumble

		Comparator<? super Transformer> c = new Comparator<Transformer>() {

			@Override
			public int compare(Transformer o1, Transformer o2) {
				if (o1 == o2)
					return 0;
				final Map<String, Integer> s1 = o1.getSpecs();
				final Map<String, Integer> s2 = o2.getSpecs();
				Integer i1 = s1.getOrDefault(SpecName.RANK, 10); // 10 being lowest rank
				Integer i2 = s2.getOrDefault(SpecName.RANK, 10);
				int k = i1.compareTo(i2);
				if (k == 0) {
					i1 = rating(o1);
					i2 = rating(o2);
					k = i1.compareTo(i2);
					k = -k;// one with higher rating first
				}
				return k;
			}
		};
		aList.sort(c);
		dList.sort(c);
		final Transformers destroyed = new Transformers();
		final int n = Math.min(aList.size(), dList.size()); // number of battle > 0
		final Result result = new Result();
		result.setBattle(0);
		int awin = 0;
		int dwin = 0;
		for (int k = 0; k < n; k++) {
			final Transformer t1 = aList.get(k);
			final Transformer t2 = dList.get(k);
			try {
				final int x = battle(t1, t2);
				if (x < 0) {
					destroyed.add(t2);
					awin = awin + 1;

				} else if (x > 0) {
					destroyed.add(t1);
					dwin = dwin + 1;
				} else {
					destroyed.add(t1);
					destroyed.add(t2);
				}
				result.setBattle(result.getBattle() + 1);
			} catch (ApiException e) {
				if (e.getCode() == 200) {
					result.setBattle(result.getBattle() + 1);
					while (k < n) {
						destroyed.add(aList.get(k));
						destroyed.add(dList.get(k));
						k++;
					}
				} else {// code 400
					String msg = "Battle Error!";
					log.error(msg, e);
					throw e;
				}
			}
		}
		// assume no tie. i.e. d is the challengers so, in case of tie, d is winner
		final Team winner = new Team();
		final Team survivors = new Team();
		final Transformers twinner = new Transformers();
		final Transformers tsurvivors = new Transformers();
		winner.setMembers(twinner);
		survivors.setMembers(tsurvivors);
		if (awin > dwin) {
			winner.setName(TeamName.AUTOBOT);
			survivors.setName(TeamName.DECEPTICON);
			aList.stream().filter(x -> !destroyed.contains(x)).forEach(x -> twinner.add(x));
			dList.stream().filter(x -> !destroyed.contains(x)).forEach(x -> tsurvivors.add(x));
		} else {
			winner.setName(TeamName.DECEPTICON);
			survivors.setName(TeamName.AUTOBOT);
			dList.stream().filter(x -> !destroyed.contains(x)).forEach(x -> twinner.add(x));
			aList.stream().filter(x -> !destroyed.contains(x)).forEach(x -> tsurvivors.add(x));
		}
		result.setWinner(winner);
		result.setSurvivors(survivors);
		return result;
	}

	public Transformers listTransformers(final String team) throws ApiException {
		if (team == null) {
			Transformers list = new Transformers();
			list.addAll(this.masterList);
			return list;
		}
		return getTransformersByTeam(this.masterList, team);

	}

	private TeamName validTeam(final Transformer body) {
		return validTeamName(body.getTeam().toString()).equals(TeamName.AUTOBOT.toString()) ? TeamName.AUTOBOT
				: TeamName.DECEPTICON;
	}

	public HttpStatus createTransformer(final Transformer body) throws ApiException {
		final String name = body.getName();
		final String id = body.getId();
		final OffsetDateTime creationDate = body.getCreationDate();
		final TeamName team = validTeam(body);
		if (name == null || id == null || creationDate == null || team == null || name.trim().isEmpty()
				|| id.trim().isEmpty()) {
//			String msg = "Bad inputs. Invalid transformer inputs with name = " + body.getName() + ", or id = "
//					+ body.getId();
			// final ApiException e = new ApiException(400, msg);
			// log.error("Create Transformer Error!", e);
			return HttpStatus.BAD_REQUEST;
		}
		final Transformer t = conflictingTransformer(body);
		if (t == null) {
			Transformer c = new Transformer();
			c.name(name).id(id)//
					.team(team)//
					.creationDate(creationDate)//
					.specs(new HashMap<String, Integer>());
			final Map<String, Integer> bspec = body.getSpecs();
			final Map<String, Integer> cspec = c.getSpecs();
			if (bspec != null)
				bspec.entrySet().stream().forEach(x -> cspec.put(x.getKey(), x.getValue()));
			masterList.add(c);
			return HttpStatus.CREATED;
		}
//		String msg = "Bad inputs. input transformer name = " + body.getName() + ", or id = " + body.getId()
//				+ " is in conflict with existing transformer name = " + t.getName() + ", or id = " + t.getId();
		// final ApiException e = new ApiException(409, msg);
		// log.error("Create Transformer Error!", e);
		return HttpStatus.CONFLICT;
	}

	public Transformer updateTransformer(final Transformer body) throws ApiException {
		final Transformer t = conflictingTransformer(body);
		if (t == null) {
			String msg = "Bad inputs. No Transformer exist with name = " + body.getName() + ", or id = " + body.getId();
			final ApiException e = new ApiException(400, msg);
			log.error("Update Transformer Error!", e);
			throw e;
		}
		if (t.getName().equalsIgnoreCase(body.getName()) && t.getId().equalsIgnoreCase(body.getId())) {
			final Transformer r = new Transformer();
			r.name(t.getName()).id(t.getId()).team(t.getTeam()).creationDate(t.getCreationDate())
					.specs(new HashMap<String, Integer>(t.getSpecs()));
			// now updating
			t.setTeam(validTeam(body));
			t.setCreationDate(body.getCreationDate());
			final Map<String, Integer> tspec = t.getSpecs() == null ? new HashMap<String, Integer>() : t.getSpecs();
			final Map<String, Integer> bspec = body.getSpecs();
			if (bspec != null)
				bspec.entrySet().stream().forEach(x -> tspec.put(x.getKey(), x.getValue()));
			return r;

		} else {
			String msg = "Bad inputs. input transformer name = " + body.getName() + ", or id = " + body.getId()
					+ " is in conflict with existing transformer name = " + t.getName() + ", or id = " + t.getId();
			final ApiException e = new ApiException(409, msg);
			log.error("Update Transformer Error!", e);
			throw e;
		}
	}

	public Transformer conflictingTransformer(final Transformer body) {
		Transformers tlist = getTransformersByNames(this.masterList, body.getName());
		if (!tlist.isEmpty()) {
			return tlist.get(0);
		}
		tlist = getTransformersById(this.masterList, body.getId());
		if (!tlist.isEmpty()) {
			return tlist.get(0);
		}
		return null;
	}

}
