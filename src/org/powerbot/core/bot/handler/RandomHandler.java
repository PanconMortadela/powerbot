package org.powerbot.core.bot.handler;

import org.powerbot.core.script.AntiRandom;
import org.powerbot.core.script.random.BankPin;
import org.powerbot.core.script.random.Beekeeper;
import org.powerbot.core.script.random.Certer;
import org.powerbot.core.script.random.Chest;
import org.powerbot.core.script.random.DrillDemon;
import org.powerbot.core.script.random.EvilBob;
import org.powerbot.core.script.random.EvilTwin;
import org.powerbot.core.script.random.Exam;
import org.powerbot.core.script.random.FirstTimeDeath;
import org.powerbot.core.script.random.FreakyForester;
import org.powerbot.core.script.random.Frog;
import org.powerbot.core.script.random.GraveDigger;
import org.powerbot.core.script.random.Login;
import org.powerbot.core.script.random.LostAndFound;
import org.powerbot.core.script.random.Maze;
import org.powerbot.core.script.random.Mime;
import org.powerbot.core.script.random.Pillory;
import org.powerbot.core.script.random.Pinball;
import org.powerbot.core.script.random.Quiz;
import org.powerbot.core.script.random.SandwichLady;
import org.powerbot.core.script.random.ScapeRune;
import org.powerbot.core.script.random.SpinTickets;
import org.powerbot.core.script.random.WidgetCloser;
import org.powerbot.core.script.job.LoopTask;
import org.powerbot.game.bot.Bot;

/**
 * @author Timer
 */
public class RandomHandler extends LoopTask {
	private final AntiRandom[] antiRandoms;
	private final ScriptHandler handler;
	private AntiRandom running;

	public RandomHandler(final Bot bot, final ScriptHandler scriptHandler) {
		antiRandoms = new AntiRandom[]{
				new Login(),
				new WidgetCloser(),
				new DrillDemon(),
				new Mime(),
				new Pinball(),
				new SandwichLady(),
				new Beekeeper(),
				new LostAndFound(),
				new FirstTimeDeath(),
				new Quiz(),
				new Frog(),
				new Chest(),
				new GraveDigger(),
				new Pillory(),
				new Certer(),
				new FreakyForester(),
				new EvilTwin(),
				new EvilBob(),
				new Maze(),
				new Exam(),
				new ScapeRune(),
				new SpinTickets(),
				new BankPin()
		};
		for (final AntiRandom antiRandom : antiRandoms) {
			antiRandom.bot = bot;
		}
		handler = scriptHandler;
		running = null;
	}

	@Override
	public int loop() {
		if (running != null && !running.activate()) {
			running = null;
			handler.resume();
		}
		if (running == null) {
			for (final AntiRandom random : antiRandoms) {
				if (random.activate()) {
					running = random;
					handler.pause();
					break;
				}
			}
		}

		if (running != null) {
			running.execute();
			return 0;
		}

		return handler.isActive() ? 2000 : -1;
	}
}
