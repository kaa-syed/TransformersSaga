/**
 * 
 */
package io.swagger.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.swagger.api.providers.ExampleData;
import io.swagger.api.providers.TransformerBattle;
import io.swagger.model.TeamName;
import io.swagger.model.Transformer;
import io.swagger.model.Transformers;

/**
 * @author Kamran A.A. Syed
 *
 */
@RunWith(SpringRunner.class)
public class TransformerBattleTest {

	private ExampleData data;

	public TransformerBattleTest() {
		this.data = new ExampleData();
	}

	@Test
	public void matchTest() throws Exception {
		String[] keys = { data.t1.getName(), data.t2.getName(), data.t3.getName(), data.t4.getName(),
				data.t5.getName() };
		assertEquals(1, TransformerBattle.match(data.t1.getName(), keys));
		assertEquals(2, TransformerBattle.match(data.t2.getName(), keys));
	}

	@Test
	public void teamNameTest() throws Exception {
		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validTeamName(null));
		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validTeamName(""));
		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validTeamName("ahgft"));
		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validTeamName("Avcds"));

		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validTeamName("hgft"));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validTeamName("vcds"));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validTeamName("d"));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validTeamName("Dgt"));

		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validOppositeTeamName(null));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validOppositeTeamName(""));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validOppositeTeamName("ahgft"));
		assertEquals(TeamName.DECEPTICON.toString(), TransformerBattle.validOppositeTeamName("Avcds"));

		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validOppositeTeamName("hgft"));
		assertEquals(TeamName.AUTOBOT.toString(), TransformerBattle.validOppositeTeamName("vcds"));

	}

	@Test
	public void searchTest() throws Exception {

		final Transformer[] list = data.list2;
		final Transformers masterList = ExampleData.transformers(list);
		Transformers tlist = TransformerBattle.getTransformersByNames(masterList, data.t2.getName(), data.t4.getName());
		assertEquals(2, tlist.size());
		assertEquals(data.t2.getName(), tlist.get(0).getName());
		assertEquals(data.t4.getName(), tlist.get(1).getName());

		tlist = TransformerBattle.getTransformersById(masterList, data.t2.getId(), data.t4.getId());
		assertEquals(2, tlist.size());
		assertEquals(data.t2.getName(), tlist.get(0).getName());
		assertEquals(data.t4.getName(), tlist.get(1).getName());

		tlist = TransformerBattle.getTransformersByTeam(masterList, TeamName.AUTOBOT.toString());
		int k1 = tlist.size();
		tlist = TransformerBattle.getTransformersByTeam(masterList, TeamName.DECEPTICON.toString());
		int k2 = tlist.size();
		assertEquals(k1 + k2, masterList.size());

	}

	@Test
	public void simpleBattleTest() throws Exception {
		int k = TransformerBattle.battle(data.t1, data.t2);
		// "Soundwave" vs "Bluestreak"
		assertEquals(-1, k);// winner "Soundwave"

	}

	@Test
	public void wrongBattleTest() throws Exception {
		try {
			int k = TransformerBattle.battle(data.t2, data.t3);
			throw new Exception("Same team result! " + k);
		} catch (ApiException e) {
			assertEquals(409, e.getCode());// bad input error
		}

	}

	@Test
	public void spacialRulesBattleTest() throws Exception {

		int k = TransformerBattle.battle(data.t1, data.t4);
		// "Soundwave" vs "Optimus Prime"
		assertEquals(1, k);// winner "Optimus Prime"

		try {
			k = TransformerBattle.battle(data.t5, data.t4);
			// "Predaking" vs "Optimus Prime"
			throw new Exception("Predaking vs Optimus Prime");
		} catch (ApiException e) {
			assertEquals(200, e.getCode());// game over signal
		}

	}

}
