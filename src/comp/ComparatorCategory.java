package comp;

import entry.Category;
import entry.Entry;

import java.util.Comparator;

public class ComparatorCategory implements Comparator<Entry> {
    private Category category;

    public ComparatorCategory(Category category) {
        this.category = category;
    }

    @Override
    public int compare(Entry o1, Entry o2) {
       if (o1.getCategory() == category){
           return -1;
       }else if (o2.getCategory() == category){
           return 1;
       }else
           return 0;
    }
}
