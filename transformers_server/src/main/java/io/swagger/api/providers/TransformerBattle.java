package io.swagger.api.providers;

import java.util.Map;

import io.swagger.api.ApiException;
import io.swagger.model.SpecName;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;

/**
 * @author Kamran A.A. Syed
 *
 */
public class TransformerBattle {

	private static final String[] rating_spec = { //
			SpecName.STRENGTH.toString(), //
			SpecName.INTELLIGENCE.toString(), //
			SpecName.SPEED.toString(), //
			SpecName.ENDURANCE.toString(), //
			SpecName.FIREPOWER.toString(), };

	/**
	 * The “overall rating” of a Transformer is the following formula: (Strength +
	 * Intelligence + Speed + Endurance + Firepower)
	 * 
	 * @param t
	 * @return rating of the transformer
	 */
	public static int rating(Transformer t) {
		int r = 0;
		final Map<String, Integer> m = t.getSpecs();
		for (String s : rating_spec) {
			final Integer i = m.get(s);
			r = i == null ? r : r + i;
		}
		return r;
	}

	/**
	 * The basic rules of the battle are:
	 * <li/>The transformers are split into two teams based on if they are Autobots
	 * or Decepticons.
	 * <li/>The teams should be sorted by rank and faced off one on one against each
	 * other in order to determine a victor, the loser is eliminated.
	 * <p/>
	 * <b/> A battle between opponents uses the following rules: </b>
	 * <li/>If any fighter is down 4 or more points of courage and 3 or more points
	 * of strength compared to their opponent, the opponent automatically wins the
	 * face-off regardless of overall rating (opponent has ran away)
	 * <li/>Otherwise, if one of the fighters is 3 or more points of skill above
	 * their opponent, they win the fight regardless of overall rating
	 * <li/>The winner is the Transformer with the highest overall rating
	 * <li/>In the event of a tie, both Transformers are considered destroyed
	 * <li/>Any Transformers who don’t have a fight are skipped (i.e. if it’s a team
	 * of 2 vs. a team of 1, there’s only going to be one battle)
	 * <li/>The team who eliminated the largest number of the opposing team is the
	 * winner
	 * <p/>
	 * <b> Special rules: </b>
	 * <li/>Any Transformer named Optimus Prime or Predaking wins his fight
	 * automatically regardless of any other criteria
	 * <li/>In the event either of the above face each other (or a duplicate of each
	 * other), the game immediately ends with all competitors destroyed
	 * 
	 * 
	 * @param a
	 * @param b
	 * @return -1 if a win +1 if b win, other wise zero for tie(i.e., both
	 *         destroyed)
	 * @throws ApiException code 200 if "Optimus Prime" and "Predaking" are facing
	 *                      each other. Game End Signal with all competitors
	 *                      destroyed <br/>
	 *                      code 400 showing system error - face off are with same
	 *                      name.
	 */
	public static int battle(Transformer a, Transformer b) throws ApiException {
		if (a.getTeam() == b.getTeam()) {
			throw new ApiException(409, "Battle Error! Same team " + a.getTeam() + "( " + a.getName() + ", " + b.getName() + ")");
		}
		int r = 0; // tie
		// Checking special rules does not apply
		int na = match(a.getName(), "Optimus Prime", "Predaking");
		int nb = match(b.getName(), "Optimus Prime", "Predaking");
		if (0 == na + nb) { // clear no one with special rule
			final Map<String, Integer> ma = a.getSpecs();
			final Map<String, Integer> mb = b.getSpecs();
			// check 4 down
			na = ma.getOrDefault(SpecName.COURAGE.toString(), 1); // minimum courage is 1
			nb = mb.getOrDefault(SpecName.COURAGE.toString(), 1);
			if (Math.abs(na - nb) > 3) {
				r = na > nb ? -1 : +1;
				// check 3 down
				na = ma.getOrDefault(SpecName.STRENGTH.toString(), 1);// minimum strength is 1
				nb = mb.getOrDefault(SpecName.STRENGTH.toString(), 1);
				if (Math.abs(na - nb) > 2) {
					if ((r < 0 && na > nb) || (r > 0 && na < nb))
						return r;
				}
				r = 0;// courage & strength test fail. move on for skill and rating test
			}
			// check 3 up skill
			na = ma.getOrDefault(SpecName.SKILL.toString(), 1);// minimum skill is 1
			nb = mb.getOrDefault(SpecName.SKILL.toString(), 1);
			if (Math.abs(na - nb) > 2) {
				r = na > nb ? -1 : +1;
				return r;
			}
			// final rating test
			na = rating(a);
			nb = rating(b);
			if (Math.abs(na - nb) > 0) {
				r = na > nb ? -1 : +1;
				return r;
			}
			assert r == 0;
		} else if (na > 0 && nb > 0) {
			// checking facing each other
			if (na == nb) {
				String msg = "Bad Input! Both battling transformers are \"" + a.getName() + "\"";
				ApiException e = new ApiException(400, msg);
				throw e;
			}
			String msg = "Game End! \"" + a.getName() + "\" is facing \"" + b.getName() + "\"";
			ApiException e = new ApiException(200, msg);
			throw e;

		} else {
			r = na > nb ? -1 : +1;
			return r;
		}
		return r;
	}

	/**
	 * 
	 * @param key
	 * @param keys
	 * @return zero if no match else matching index in keys plus one
	 */
	public static int match(String key, String... keys) {
		if (key != null) {
			key = key.trim();
			if (!key.isEmpty()) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i] != null) {
						if (key.equalsIgnoreCase(keys[i].trim())) {
							return i + 1;
						}
					}
				}
			}
		}
		return 0;
	}

	public static String validTeamName(String teamName) {
		teamName = teamName == null || teamName.trim().isEmpty() || teamName.trim().toUpperCase().startsWith("A")
				? TeamName.AUTOBOT.toString()
				: TeamName.DECEPTICON.toString();
		return teamName;
	}

	public static String validOppositeTeamName(String teamName) {
		teamName = teamName == null || teamName.trim().isEmpty() || teamName.trim().toUpperCase().startsWith("A")
				? TeamName.DECEPTICON.toString()
				: TeamName.AUTOBOT.toString();
		return teamName;
	}

	public static Transformers getTransformersByNames(final Transformers masterList, final String... names) {
		Transformers tlist = new Transformers();
		masterList.stream().filter(x -> match(x.getName(), names) > 0).forEach(x -> tlist.add(x));
		return tlist;
	}

	public static Transformers getTransformersById(final Transformers masterList, final String... ids) {
		Transformers tlist = new Transformers();
		masterList.stream().filter(x -> match(x.getId(), ids) > 0).forEach(x -> tlist.add(x));
		return tlist;
	}

	public static Transformers getTransformersByTeam(final Transformers masterList, final String teamName) {
		final String team = validTeamName(teamName);
		Transformers tlist = new Transformers();
		masterList.stream().filter(x -> x.getTeam().toString().equalsIgnoreCase(team)).forEach(x -> tlist.add(x));
		return tlist;
	}

}