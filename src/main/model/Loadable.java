package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Loadable {
    void load(String file) throws IOException, ClassNotFoundException;
}
