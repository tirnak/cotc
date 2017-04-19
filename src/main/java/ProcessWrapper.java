import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessWrapper {
    Process process;
    BufferedReader stdOut;
    OutputStream stdIn;
    String name;
    boolean debug;

    public ProcessWrapper(Process process, String name, boolean debug) {
        this(process); this.debug = debug; this.name = name;
    }
    public ProcessWrapper(Process process) {
        this.process = process;
        stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
        stdIn = process.getOutputStream();
    }

    public void toStdIn(String s) {
        try {
            if (!s.endsWith("\n")) s += "\n";
            stdIn.write(s.getBytes());
            stdIn.flush();
            if (debug) System.out.print(name + " gets: " + s);
        } catch (Exception e) {throw  new RuntimeException(e);}
    }

    public List<String> fromStdOut(int lines) {
        try {
            List<String> result = new ArrayList<>(); String tmp;
            System.out.println("fromStdOut "+name);
            while (lines-- > 0 && (tmp = stdOut.readLine()) != null) {
                if (debug) System.out.println(name + " writes: " + tmp);
                result.add(tmp);
            }
            return result;
        } catch (Exception e) {throw  new RuntimeException(e);}
    }

//    public List<String> fromStdOut() {
//        try {
//            List<String> result = new ArrayList<>(); String tmp;
//            while (stdOut.ready() && (tmp = stdOut.readLine()) != null) {
//                if (debug) System.out.println(name + " writes: " + tmp);
//                result.add(tmp);
//            }
//            return result;
//        } catch (Exception e) {throw  new RuntimeException(e);}
//    }


}
