package com.LRH;

import java.io.File;

/**
 * Created by tlindblom on 5/11/2017.
 */
public class Pair {
    public final File repo_path;
    public final File project_path;

    public Pair (File rp, File pp) {
        this.repo_path = rp;
        this.project_path = pp;
    }
}
