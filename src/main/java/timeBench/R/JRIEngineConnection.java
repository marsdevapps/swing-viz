package timeBench.R;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.rosuda.REngine.*;

import java.lang.reflect.InvocationTargetException;

public class JRIEngineConnection implements REngineConnection {//org.rosuda.REngine.Rserve.RConnection
    public static final String RENGINE_IMPL = "org.rosuda.REngine.JRI.JRIEngine";
    
    private static Logger logger = LogManager.getLogger(JRIEngineConnection.class);

    private static JRIEngineConnection instance;

    // could be replaced by REngine.getLastEngine()
    private REngine engine;

    private JRIEngineConnection() {
        try {
            if (logger.isInfoEnabled())
                // show R console output in System.out
                engine = REngine.engineForClass(RENGINE_IMPL, new String[] {
                        "--no-save", "--no-restore" }, new REngineStdOutput(),
                        false);
            else
                engine = REngine.engineForClass(RENGINE_IMPL);
        } catch (ClassNotFoundException e) {
            logger.error("unexpected exception", e);
        } catch (NoSuchMethodException e) {
            logger.error("unexpected exception", e);
        } catch (IllegalAccessException e) {
            logger.error("unexpected exception", e);
        } catch (InvocationTargetException e) {
            logger.error("unexpected exception", e);
        }
    }

    public static synchronized JRIEngineConnection getInstance() {
        if (null == instance) {

        	System.out.println("NEW JRIEngineConnection");
            instance = new JRIEngineConnection();
        }
        return instance;
    }
    
    public REngine getREngine() {
    	return engine;
    }
    
    public double getDouble(String name) throws REXPMismatchException,
    REngineException {
    	REXP x = engine.parseAndEval(name, null, true);
    	return x.asDouble();
    }
}
