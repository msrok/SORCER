package sorcer.provider.hasherMD5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.service.*;
import sorcer.service.Strategy.Access;
import sorcer.service.Strategy.Wait;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.*;
import static sorcer.eo.operator.*;

/**
 * @author Mike Sobolewski
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/hasher")
public class NetTasks {
	private final static Logger logger = Logger.getLogger(NetTasks.class.getName());
	
	@Test
	public void exertTask() throws Exception  {

		Task t5 = srv("t5", sig("hash", HasherMD5.class),
				cxt("hash", inEnt("arg/x1", "test"), result("result/y")));

		Exertion out = exert(t5);
		Context cxt = context(out);
		logger.info("out context: " + cxt);
		logger.info("context @ arg/x1: " + value(cxt, "arg/x1"));
		logger.info("context @ result/y: " + value(cxt, "result/y"));

		// get a single context argument
		assertEquals("098f6bcd4621d373cade4e832627b4f6", value(cxt, "result/y"));

		// get the subcontext output from the context
		assertTrue(context(ent("arg/x1", "test"), ent("result/y", "098f6bcd4621d373cade4e832627b4f6")).equals(
				value(cxt, result("result/context", from("arg/x1", "result/y")))));
	}
/*
	@Test
	public void valueTask() throws SignatureException, ExertionException, ContextException  {

		Task t5 = srv("t5", sig("add", HasherMD5.class),
				cxt("add", inEnt("arg/x1", 20.0), inEnt("arg/x2", 80.0), result("result/y")));

		// get the result value
		assertEquals(100.0, value(t5));

		// get the subcontext output from the exertion
		assertTrue(context(ent("arg/x1", 20.0), ent("result/z", 100.0)).equals(
				value(t5, result("result/z", from("arg/x1", "result/z")))));

	}

	@Test
	public void spaceTask() throws Exception {

		Task t5 = task(
				"t5",
				sig("add", HasherMD5.class),
				context("add", inEnt("arg/x1", 20.0),
						inEnt("arg/x2", 80.0), outEnt("result/y")),
				strategy(Access.PULL, Wait.YES));

		t5 = exert(t5);
		logger.info("t5 context: " + context(t5));
		logger.info("t5 value: " + get(t5, "result/y"));
		assertEquals(get(t5, "result/y"), 100.0);
	}


	@Test
	public void spaceTask2() throws Exception {
		task("hello hasherMD5", sig("add",
						HasherMD5.class,
						deploy(configuration("org.sorcer:hasherMD5:config:5.0"))),
//				strategy(Strategy.Provision.YES),
				context("hasherMD5", inEnt("arg/x1", 10.0), inEnt("arg/x2", 100.0), result("out/y")));
	}
	*/
}
	
	
