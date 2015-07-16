package pl.droidsonroids.bootcamp.yo.model;

import android.support.annotation.NonNull;

public class User implements Comparable<User> {
    int id;
    String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(@NonNull User another) {
        int last = this.name.compareToIgnoreCase(another.name);
        return last == 0 ? this.name.compareToIgnoreCase(another.name) : last;
    }
}
