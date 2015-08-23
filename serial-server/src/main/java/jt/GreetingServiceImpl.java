package jt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends ContextRemoteServiceServlet implements
        GreetingService {

    static int version = 0;

    public GreetingServiceImpl() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("vers" + version);
                    version++;
                }
            }
        }).start();
    }

    public GreetingResponse greetServer(String input) throws IllegalArgumentException {
        // Verify that the input is valid.
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException(
                    "Name must be at least 4 characters long");
        }

        GreetingResponse response = new GreetingResponse();

        response.setServerInfo(getServletContext().getServerInfo());
        response.setUserAgent(getThreadLocalRequest().getHeader("User-Agent"));

        response.setGreeting("Hello, " + input + "!");

        return response;
    }

    public String version() {
        return version + "";
    }
}
