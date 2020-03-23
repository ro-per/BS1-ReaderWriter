package buttonviewers;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import domain.Process;
import domain.ProcessState;

public class ButtonViewer extends java.awt.Button implements Observer {

    private static final long serialVersionUID = 8389983849367015129L;

    public ButtonViewer(String name, Process p) {
        super(name + "                       ");
        setBackground(Color.green);
        p.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        ProcessState state = ((Process) o).getState();
        setLabel((Process) o + ": " + state + " ...");

        switch (state) {
            case IDLE:
                setBackground(Color.green);
                break;
            case READING:
                setBackground(Color.blue);
                break;
            case WRITING:
                setBackground(Color.cyan);
                break;
            case WANTREAD:
                setBackground(Color.yellow);
                break;
            case WANTWRITE:
                setBackground(Color.orange);
                break;

            default:
                // If black, then something is wrong
                setBackground(Color.black);
                ;
        }

    }
}
