package io.swagger.api.providers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import org.threeten.bp.OffsetDateTime;

import io.swagger.model.SpecName;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;

import static io.swagger.api.providers.TransformerBattle.validTeamName;

/**
 * 
 * @author Kamran A.A. Syed
 *
 */
public class ExampleData {

	public static Map<String, Integer> spec(int strength, int intelligence, int speed, int endurance, int rank,
			int courage, int firepower, int skill) {
		final Map<String, Integer> s = new HashMap<String, Integer>();
		s.put(SpecName.STRENGTH.toString(), Math.min(10, Math.max(1, strength)));
		s.put(SpecName.INTELLIGENCE.toString(), Math.min(10, Math.max(1, intelligence)));
		s.put(SpecName.SPEED.toString(), Math.min(10, Math.max(1, speed)));
		s.put(SpecName.ENDURANCE.toString(), Math.min(10, Math.max(1, endurance)));
		s.put(SpecName.RANK.toString(), Math.min(10, Math.max(1, rank)));
		s.put(SpecName.COURAGE.toString(), Math.min(10, Math.max(1, courage)));
		s.put(SpecName.FIREPOWER.toString(), Math.min(10, Math.max(1, firepower)));
		s.put(SpecName.SKILL.toString(), Math.min(10, Math.max(1, skill)));
		return s;
	}

	public static Transformer transformer(String name, String team, int strength, int intelligence, int speed,
			int endurance, int rank, int courage, int firepower, int skill) {

		final Transformer t = new Transformer().name(name) //
				.team(TeamName.fromValue(validTeamName(team))) //
				.creationDate(OffsetDateTime.now()) //
				.specs(spec(strength, intelligence, speed, endurance, rank, courage, firepower, skill));

		final CRC32 crc = new CRC32();
		crc.update(team.getBytes());
		crc.update(":".getBytes());
		crc.update(name.getBytes());
		t.setId(String.valueOf(crc.getValue()));

		return t;
	}

	public static Transformers transformers(Transformer... list) {
		final Transformers t = new Transformers();
		Arrays.asList(list).stream().forEach(x -> t.add(x));
		return t;
	}

	public Transformer t1 = transformer("Soundwave", "D", 8, 9, 2, 6, 7, 5, 6, 10); // 7 rank
	public Transformer t2 = transformer("Bluestreak", "A", 6, 6, 7, 9, 5, 2, 9, 7); // 5 rank
	public Transformer t3 = transformer("Hubcap", "A", 4, 4, 4, 4, 4, 4, 4, 4);
	public Transformer t4 = transformer("Optimus Prime", "A", 5, 5, 5, 5, 8, 5, 5, 5); // 8 rank
	public Transformer t5 = transformer("Predaking", "D", 1, 1, 1, 1, 9, 1, 1, 1); // 9 rank

	public Transformer[] list1 = { t1, t2, t3 };
	public Transformer[] list2 = { t1, t2, t3, t4, t5 };
	public Transformer[] list3 = { t1, t2, t3, t4 };
	public Transformer[] list4 = { t1, t2, t3, t5 };

	public static String toString(Transformer t) {
		StringBuilder sb = new StringBuilder();
		sb.append(t.getName()).append("(").append(t.getId()).append(")").append(":").append(t.getTeam()).append("->");
		Map<String, Integer> m = t.getSpecs();
		Arrays.asList(SpecName.values()).stream().forEach(key -> sb.append(m.get(key.toString())).append(", "));
		sb.setLength(sb.length() - 2);
		return sb.toString();

	}
}
