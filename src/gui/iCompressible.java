package gui;

import javax.swing.*;

public interface iCompressible {

    public void shrink(int numPixels, int stopPosition);
    public void expand(int numPixels, int stopPosition);

}
