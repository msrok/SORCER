package sorcer.requestor.hasherMD5;

import sorcer.core.requestor.ServiceRequestor;
import sorcer.provider.hasherMD5.HasherMD5;
import sorcer.service.*;

import java.io.File;
import java.io.IOException;

import static sorcer.co.operator.inEnt;
import static sorcer.eo.operator.*;

public class HasherMD5Requestor extends ServiceRequestor {

    public Exertion getExertion(String... args)
            throws ExertionException, ContextException, SignatureException, IOException {


        if (args[1].equals("netlet")) {
            return (Exertion) evaluate(new File("src/main/netlets/hasherMD5-netlet.groovy"));
        } else if (args[1].equals("dynamic")) {
            return (Exertion) evaluate(new File("src/main/netlets/dynamic-hasherMD5-netlet.groovy"));
        }
        Class serviceType =  HasherMD5.class;

        Double v1 = new Double(getProperty("arg/x1"));
        Double v2 = new Double(getProperty("arg/x2"));
        
        return task("hello hasherMD5", sig("add", serviceType),
                    context("hasherMD5", inEnt("arg/x1", v1), inEnt("arg/x2", v2),
                            result("out/y")));
    }

    @Override
    public void postprocess(String... args) throws ExertionException, ContextException {
        super.postprocess();
        logger.info("<<<<<<<<<< add task: \n" + exertion);
    }
}
